package com.duguyin.mybatissql.enums;

/**
 * @ClassName ComparisonOperator
 * @Description
 * @Author LiuYin
 * @Date 2018/9/28 11:23
 */
public enum ComparisonOperator {

    EQ(" = "),
    LT(" < "),
    GT(" > "),
    LTE(" <= "),
    GTE(" >= "),
    NEQ(" != "),
    LIKE(" LIKE "),
    LEFT_LIKE(" LIKE "),
    RIGHT_LIKE(" LIKE "),
    BOTH_LIKE(" LIKE "),
    IS_NULL(" IS NULL "),
    IS_NOT_NULL(" IS NOT NULL "),
    IN(" IN"),
    ;

    String operator ;

    ComparisonOperator(String operator){
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
