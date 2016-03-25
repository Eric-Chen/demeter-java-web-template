package com.demeter.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eric on 15/9/21.
 */
public final class TimeUtils {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    private TimeUtils(){}

    public static int getCurrentTimeInSeconds(){
        Calendar calendar = Calendar.getInstance();
        return Long.valueOf(calendar.getTimeInMillis()/1000).intValue();
    }

    public static int getCurrentTimeInMins(){
        Calendar calendar = Calendar.getInstance();
        return Long.valueOf(calendar.getTimeInMillis()/1000/60).intValue();
    }

    public static long convertTimeStampFromFormat(Long formattedTime){
        try {
            Date date = SIMPLE_DATE_FORMAT.parse(formattedTime.toString());
            return date.getTime();
        } catch (ParseException e) {
            throw new RuntimeException("Time format error for "+formattedTime);
        }
    }

    public static long getDefaultCurrentFormatedTime(){
        return Long.parseLong(SIMPLE_DATE_FORMAT.format(new Date()));
    }

    public static long getDefaultFormatedTimeAt(Date date){
        return Long.parseLong(SIMPLE_DATE_FORMAT.format(date));
    }

    public static class StopWatch {

        private long start = System.nanoTime();

        public static StopWatch start(){
            return new StopWatch();
        }


        public long stop(){
            long pass = System.nanoTime() - start;
            return pass / 1000000;
        }

    }

//    public static void main(String[] args) {
//        System.out.println(getTimeStampFromFormat(20151228170301L));
//        Date d = new Date();
//        d.setTime(1451293381000L);
//        System.out.println(d);
//    }
}
