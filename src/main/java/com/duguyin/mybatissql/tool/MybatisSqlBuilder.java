package com.duguyin.mybatissql.tool;

import com.duguyin.mybatissql.annotations.Column;
import com.duguyin.mybatissql.annotations.Table;
import com.duguyin.mybatissql.obj.MybatisMapping;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MybatisSqlBuilder {

    private static  Configuration CONFIGURATION = null;

    public static Configuration getConfiguration() {
        return CONFIGURATION;
    }

    public static void setConfiguration(Configuration configuration){
        CONFIGURATION = configuration;
    }

    private static final String BASE_FRAGMENT = "_BASE_FRAGMENT";

    private static final Map<String, ConcurrentHashMap<String,String>>  SQL_MAP = new ConcurrentHashMap<>();
    private static final Map<String, MybatisMapping>  MAPPING_MAP = new ConcurrentHashMap<>();










    private boolean isEmpty(String s){
        return Objects.nonNull(s) && s.length() > 0 && s.trim().length() > 0;
    }

    private String withBrackets(String s) {
        return "(" + s + ")";
    }


}
