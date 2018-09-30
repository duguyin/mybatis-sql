package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.enums.LogicOperator;
import com.duguyin.mybatissql.tool.Conditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        mybatisSQL.where(new LogicFragment("id").and("age").and("createTime.<=").or("beforeResult.%%"));
        return mybatisSQL.logicFragment.toSqlFragment();

    }


    public class MybatisSQL{

        String tableName;
        String op;

        List<String> List;
        List<String> valueList;

        LogicFragment logicFragment;

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public void setLogicFragment(LogicFragment logicFragment) {
            this.logicFragment = logicFragment;
        }

        public MybatisSQL where(LogicFragment logicFragment){
            logicFragment.setMappingMap(mapping.getDefaultMappingMap());
            this.logicFragment.addChild(logicFragment);
            return this;
        }

        public MybatisSQL and(LogicFragment logicFragment){
            this.logicFragment.addChild(logicFragment, LogicOperator.AND);
            return this;
        }

        public MybatisSQL or(LogicFragment logicFragment){
            this.logicFragment.addChild(logicFragment, LogicOperator.OR);
            return this;
        }

    }




}
