package com.demeter.module.oplog;

import com.demeter.models.oplog.Oplog;

import java.util.List;
import java.util.Map;

/**
 * Created by eric on 4/2/16.
 */
public interface OplogDao {

    int createLog(Oplog oplog);

    List<Oplog> queryLogs(Map<String, Object> params);


}
