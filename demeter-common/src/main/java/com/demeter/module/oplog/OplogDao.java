package com.demeter.module.oplog;

import java.util.List;

/**
 * Created by eric on 4/2/16.
 */
public interface OplogDao {

    int createLog(Oplog oplog);

    List<Oplog> queryLogs(Oplog param);


}
