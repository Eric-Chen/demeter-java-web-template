package com.demeter.models.oplog;


import com.demeter.models.BasicModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by eric on 4/2/16.
 */
@ToString
@Setter @Getter
public class Oplog extends BasicModel {

    private int id;
    private int uid;
    private String username;
    private String operation;
    private String changeData;

}
