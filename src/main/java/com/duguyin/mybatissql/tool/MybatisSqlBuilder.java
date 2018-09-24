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

    private enum OP {
        INSERT,
        DELETE,
        SELECT,
        UPDATE,
        ;
    }


    private String getOperationSql(OP op) {
        Objects.requireNonNull(op, "operation is null");
        switch (op) {
            case DELETE:
                return "DELETE ";
            case INSERT:
                return "INSERT INTO ";
            case SELECT:
                return "SELECT ";
            case UPDATE:
                return "UPDATE ";
            default:
                throw new RuntimeException("operation is error: " + op.name());
        }
    }


    private class SQL {
        private OP op;
        private String table;

        private String getBaseFragment(Class<?> clazz){
            Objects.requireNonNull(clazz, "class is null");
            String key = clazz.getClassLoader() + "_" + clazz.getName();
            final ConcurrentHashMap<String, String> thisClazzMap = SQL_MAP.getOrDefault(key, new ConcurrentHashMap<>());
            String baseFragment = thisClazzMap.get(BASE_FRAGMENT);
            return baseFragment;
        }

    }


    private boolean isEmpty(String s){
        return Objects.nonNull(s) && s.length() > 0 && s.trim().length() > 0;
    }

    private String withBrackets(String s) {
        return "(" + s + ")";
    }


}
