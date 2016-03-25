package com.demeter.tools;

import org.slf4j.helpers.MessageFormatter;

/**
 * Created by eric on 3/15/16.
 */
public final class StringUtils {

    private StringUtils(){}

    public static final String format(String pattern, Object...objects){
        return MessageFormatter.format(pattern, objects).getMessage();
    }


    public static void main(String[] args) {
        format("test {}", "result");
    }
}