package com.duguyin.mybatissql.enums;

/**
 * @ClassName LogicOperator
 * @Description
 * @Author LiuYin
 * @Date 2018/9/28 11:31
 */
public enum LogicOperator {

    AND(" AND "),
    OR(" OR "),
    ;

    String operator;

    LogicOperator(String operator){
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }



}
