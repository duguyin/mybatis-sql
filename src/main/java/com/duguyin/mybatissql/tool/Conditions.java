package com.duguyin.mybatissql.tool;

import com.duguyin.mybatissql.enums.ComparisonOperator;
import com.duguyin.mybatissql.obj.CompareFragment;
import com.duguyin.mybatissql.obj.LogicFragment;

/**
 * @ClassName Conditions
 * @Description
 * @Author LiuYin
 * @Date 2018/9/28 17:12
 */
public class Conditions {


    public static LogicFragment newLogic(){
        return new LogicFragment("");
    }


    public static LogicFragment newCondition(CompareFragment compareFragment){
        return newLogic().add(compareFragment);
    }

    public static LogicFragment newCondition(String column, ComparisonOperator operator,String value){
        return newCondition(new CompareFragment().column(column).value(value).operator(operator));
    }

    public static LogicFragment eq(String property){
        return newCondition(property, ComparisonOperator.EQ);
    }

    public static LogicFragment gt(String property){
        return newCondition(property, ComparisonOperator.GT);
    }

    public static LogicFragment lt(String property){
        return newCondition(property, ComparisonOperator.LT);
    }

    public static LogicFragment gte(String property){
        return newCondition(property, ComparisonOperator.GTE);
    }

    public static LogicFragment lte(String property){
        return newCondition(property, ComparisonOperator.LTE);
    }

    public static LogicFragment neq(String property){
        return newCondition(property, ComparisonOperator.NEQ);
    }

    public static LogicFragment like(String property){
        return newCondition(property, ComparisonOperator.LIKE);
    }

    public static LogicFragment newCondition(String property, ComparisonOperator comparisonOperator){
        return newCondition(property, comparisonOperator, property);
    }

}
