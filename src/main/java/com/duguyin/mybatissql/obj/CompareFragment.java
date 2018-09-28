package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.enums.ComparisonOperator;
import com.duguyin.mybatissql.exceptions.ParseException;
import com.duguyin.mybatissql.tool.StringTool;

import java.util.Objects;

/**
 * @ClassName CompareFragment
 * @Description
 * @Author LiuYin
 * @Date 2018/9/28 11:21
 */
public class CompareFragment {

    private String column;
    private ComparisonOperator comparisonOperator;
    private String value;


    public void check() {
        if (StringTool.hasAnyEmpty(column, value) || Objects.isNull(comparisonOperator)) {
            throw new ParseException("condition is error");
        }
    }


    public String getFragment() {
        check();
        return column + comparisonOperator.getOperator() + value;
    }

    public CompareFragment column(String column){
        this.column = column;
        return this;
    }

    public CompareFragment operator(ComparisonOperator operator){
        this.comparisonOperator = operator;
        return this;
    }

    public CompareFragment value(String value){
        this.value = value;
        return this;
    }




}
