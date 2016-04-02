package com.demeter.module.oplog.support;

import com.demeter.models.oplog.Oplog;
import com.demeter.module.BasicService;
import com.demeter.module.oplog.OplogDao;
import com.demeter.module.oplog.OplogService;
import com.demeter.tools.CollectionsUtil;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.beanutils.ConvertUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eric on 4/2/16.
 */
public class DefaultOplogService extends BasicService<OplogDao> implements OplogService {

    @Subscribe
    @Override
    public boolean saveOplog(Oplog oplog) {
        return dao.createLog(oplog) == 1;
    }

    @Override
    public List<Oplog> getOplogs(QueryRequest request) {
        Object param = ConvertUtils.convert(request, HashMap.class);
        List<Oplog> oplogs = dao.queryLogs((Map)param);
        return CollectionsUtil.isNullOrEmpty(oplogs) ? Collections.emptyList() : oplogs;
    }
}
