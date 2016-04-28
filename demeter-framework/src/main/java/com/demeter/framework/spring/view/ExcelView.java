package com.demeter.framework.spring.view;

import com.google.common.collect.Lists;
import com.outsource.utils.NameValuePair;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by eric on 2/13/16.
 */
public class ExcelView extends AbstractXlsView {

    private static final String PAIR_SPLIT = "~";

    private static final String PAIR_KEY_VALUE = "#";

    private static final String MERGED_KEY_SPLIT = ":";

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String columnStr = (String)model.get("columns");
        List<?> records = (List<?>)model.get("records");

        int sheetNum = 0;
        if(records.size()%65535!=0){//Excel 2007以前版本一个工作表最多65535行
            sheetNum = records.size()/65535+1;
        } else {
            sheetNum = records.size()/65535;
        }

        List<NameValuePair<String, String>> columns = genColumns(columnStr);
        Font font = workbook.createFont();
        font.setColor(HSSFFont.COLOR_RED);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        CellStyle cellStyle= workbook.createCellStyle();
        cellStyle.setFont(font);

        for(int sheetStart=0; sheetStart < sheetNum; sheetStart++){
            Sheet sheet				= workbook.createSheet("导出信息"+(sheetStart+1));
            Row titleRow = sheet.createRow(0);
            for(int i=0;i < columns.size(); i++){//head
                Cell cell = titleRow.createCell(i);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(columns.get(i).value);
            }
            for(int i = sheetStart*65535, j=0, size = Math.min((sheetStart + 1)*65535, records.size());
                i < size;
                i++, j++) {
                Row dataRow = sheet.createRow(j+1);
                Object record = records.get(i);
                for (int c = 0; c < columns.size(); c++) {
                    NameValuePair<String, String> column = columns.get(c);
                    Cell cell = dataRow.createCell(c);
                    if(column.name.contains(":")){
                        String[] mergedColumns = column.name.split(":");
                        StringBuilder cellValue = new StringBuilder();
                        for(String subCol : mergedColumns){
                            cellValue.append(BeanUtils.getProperty(record, subCol)).append("/");
                        }
                        cellValue.deleteCharAt(cellValue.length() - 1);
                        cell.setCellValue(cellValue.toString());
                    } else {
                        cell.setCellValue(BeanUtils.getProperty(record, column.name));
                    }
                }

            }
        }

        String fname = (String)model.get("fileName");
        response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
        fname = java.net.URLEncoder.encode(fname,"UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+new String(fname.getBytes("UTF-8"),"GBK")+".xls");

    }

    private static List<NameValuePair<String, String>> genColumns(String columnStr){
        List<NameValuePair<String, String>> result = Lists.newArrayList();
        String[] pairStrs = columnStr.split(PAIR_SPLIT);
        for(String pairStr : pairStrs){
            if(pairStr != null && pairStr.contains(PAIR_KEY_VALUE)){
                String[] pairArr = pairStr.split(PAIR_KEY_VALUE);
                result.add(new NameValuePair<>(pairArr[0], pairArr[1]));
            }
        }
        return result;
    }
}
