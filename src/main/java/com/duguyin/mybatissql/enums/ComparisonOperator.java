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
    ;

    String operator ;

    ComparisonOperator(String operator){
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
