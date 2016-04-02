package com.demeter.module;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by eric on 4/2/16.
 */
@Setter @Getter
public class BasicModel {

    private int id;

    private Date created;

    private Date updated;

}
