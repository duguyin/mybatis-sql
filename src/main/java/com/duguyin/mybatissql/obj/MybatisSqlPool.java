package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.tool.Conditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MybatisSqlPool
 * @Description sql 语句池，专门用来放sql
 * @Author LiuYin
 * @Date 2018/9/27 18:37
 */
public class MybatisSqlPool {


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


    public static class MybatisSQL{
        String tableName;
        String op;

        List<String> List;
        List<String> valueList;

        LogicFragment logicFragment = new LogicFragment();


        public MybatisSQL where(LogicFragment logicFragment){
            this.logicFragment.addChild(logicFragment);
            return this;
        }

    }

    public static void main(String[] args) {
        final MybatisSQL where = new MybatisSQL().where(Conditions.eq("dog").and(Conditions.eq("pig")));
        System.out.println(where.logicFragment.toSqlFragment(new StringBuilder()));
    }



}
