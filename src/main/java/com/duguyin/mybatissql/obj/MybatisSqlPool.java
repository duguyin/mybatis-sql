package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.enums.LogicOperator;

import java.util.*;

/**
 * @ClassName MybatisSqlPool
 * @Description sql 语句池，专门用来放sql
 * @Author LiuYin
 * @Date 2018/9/27 18:37
 */
public class MybatisSqlPool<T> {

    public  MybatisMapping<T> mapping;


    private MybatisSqlPool(){}

    public MybatisSqlPool(MybatisMapping<T> mybatisMapping){
        Objects.requireNonNull(mybatisMapping);
        this.mapping = mybatisMapping;
    }


    private enum OP {
        INSERT("INSERT INTO"),
        DELETE("DELETE"),
        SELECT("SELECT"),
        UPDATE("UPDATE"),
        ;

        String op;

        OP(String op){
            this.op = op;
        }

        String getOp(){
            return this.op;
        }


    }

    public String sql(){
        final MybatisSQL mybatisSQL = new MybatisSQL();
        mybatisSQL.setLogicFragment(new LogicFragment(mapping.getDefaultMappingMap()));

        mybatisSQL.WHERE(new LogicFragment("id").and("age")).OR(new LogicFragment("id").and("age"));
        return mybatisSQL.logicFragment.toSqlFragment();

    }


    public class MybatisSQL{

        String tableName;
        OP op;

        List<String> columns = new ArrayList<>();

        List<String> values;
        List<String> countColumns = new ArrayList<>();

        LogicFragment logicFragment;

        int[] limit = new int[2];

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public void setLogicFragment(LogicFragment logicFragment) {
            this.logicFragment = logicFragment;
        }

        public MybatisSQL WHERE(LogicFragment logicFragment){
            logicFragment.setMappingMap(mapping.getDefaultMappingMap());
            this.logicFragment.addChild(logicFragment);
            return this;
        }

        public MybatisSQL AND(LogicFragment logicFragment){
            this.logicFragment.addChild(logicFragment, LogicOperator.AND);
            return this;
        }

        public MybatisSQL OR(LogicFragment logicFragment){
            this.logicFragment.addChild(logicFragment, LogicOperator.OR);
            return this;
        }

        public MybatisSQL tableName(String tableName){
            this.tableName = tableName;
            return this;
        }

        public MybatisSQL OP(OP op){
            this.op = op;
            return this;
        }

        public MybatisSQL colums(String... columns){
            Objects.requireNonNull(columns, "columns is null");
            this.columns.addAll(Arrays.asList(columns));
            return this;
        }

        public MybatisSQL limit(int begin, int offset){
            this.limit[0] = begin;
            this.limit[1] = offset;
            return this;
        }

        public MybatisSQL count(String property){

        }

    }




}
