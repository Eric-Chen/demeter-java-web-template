package com.demeter.module.oplog;

import com.demeter.models.oplog.Oplog;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by eric on 4/3/16.
 */
public interface OplogService {

    boolean saveOplog(Oplog oplog);

    List<Oplog> getOplogs(QueryRequest request);

    @Getter @Setter
    class QueryRequest{
        private String username;
        private String uid;
        private Date updatedStart;
        private Date updatedEnd;
    }

}
