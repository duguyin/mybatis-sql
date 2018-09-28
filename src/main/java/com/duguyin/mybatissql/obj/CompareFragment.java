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




    public CompareFragment eq(String property){
        return eq(property, property);
    }

    public CompareFragment lte(String property){
        return lte(property, property);
    }

    public CompareFragment lt(String property){
        return lt(property, property);
    }

    public CompareFragment gt(String property){
        return gte(property, property);
    }

    public CompareFragment gte(String property){
        return gte(property, property);
    }

    public CompareFragment neq(String property){
        return neq(property, property);
    }



    public CompareFragment eq(String column, String value){
        return create(column, ComparisonOperator.EQ, value);
    }

    public CompareFragment lt(String column, String value){
        return create(column, ComparisonOperator.LT, value);
    }

    public CompareFragment lte(String column, String value){
        return create(column, ComparisonOperator.LTE, value);
    }

    public CompareFragment gt(String column, String value){
        return create(column, ComparisonOperator.GT, value);
    }

    public CompareFragment gte(String column, String value){
        return create(column, ComparisonOperator.GTE, value);
    }

    public CompareFragment neq(String column, String value){
        return create(column, ComparisonOperator.NEQ, value);
    }


    private CompareFragment create(String column, ComparisonOperator comparisonOperator, String value){
        return new CompareFragment().column(column).operator(comparisonOperator).value(value);
    }


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
