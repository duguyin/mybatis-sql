package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.enums.ComparisonOperator;
import com.duguyin.mybatissql.exceptions.ParseException;
import com.duguyin.mybatissql.tool.StringTool;

import java.util.Map;
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




    public static CompareFragment eq(String property){
        return eq(property, property);
    }

    public static CompareFragment lte(String property){
        return lte(property, property);
    }

    public static CompareFragment lt(String property){
        return lt(property, property);
    }

    public static CompareFragment gt(String property){
        return gte(property, property);
    }

    public static CompareFragment gte(String property){
        return gte(property, property);
    }

    public static CompareFragment neq(String property){
        return neq(property, property);
    }



    public static CompareFragment eq(String column, String value){
        return create(column, ComparisonOperator.EQ, value);
    }

    public static CompareFragment lt(String column, String value){
        return create(column, ComparisonOperator.LT, value);
    }

    public static CompareFragment lte(String column, String value){
        return create(column, ComparisonOperator.LTE, value);
    }

    public static CompareFragment gt(String column, String value){
        return create(column, ComparisonOperator.GT, value);
    }

    public static CompareFragment gte(String column, String value){
        return create(column, ComparisonOperator.GTE, value);
    }

    public static CompareFragment neq(String column, String value){
        return create(column, ComparisonOperator.NEQ, value);
    }


    private static CompareFragment create(String column, ComparisonOperator comparisonOperator, String value){
        return new CompareFragment().column(column).operator(comparisonOperator).value(value);
    }


    public void check() {
        if (StringTool.hasAnyEmpty(column, value) || Objects.isNull(comparisonOperator)) {
            throw new ParseException("condition is error");
        }
    }


    public String getFragment() {
        check();
        switch (comparisonOperator){
            case EQ:
            case GT:
            case GTE:
            case LT:
            case LTE:
            case NEQ:
            case LIKE:
                return column + comparisonOperator.getOperator() + value;
            case LEFT_LIKE:
                return column + comparisonOperator.getOperator() + " concat( '%', " + value + " ) ";
            case RIGTH_LIKE:
                return column + comparisonOperator.getOperator() + " concat( " + value + ", '%' ) ";
            case BOTH_LIKE:
                return column + comparisonOperator.getOperator() + " concat( '%', " + value + ", '%' ) ";
            case IS_NULL:
            case IS_NOT_NULL:
                return column + comparisonOperator.getOperator();
            default:
                throw new ParseException("this operator is not definition:" + comparisonOperator.getOperator());
        }
    }

    public void reBuild(Map<String, PropertyColumnMapping> mappingMap){
        Objects.requireNonNull(mappingMap);
        final PropertyColumnMapping propertyColumnMapping = mappingMap.get(this.getColumn());
        if(Objects.nonNull(propertyColumnMapping)){
            String newColumn = propertyColumnMapping.getColumn();
            String newValue = "#{"+propertyColumnMapping.getProperty()+"}";
            this.column(newColumn).value(newValue);
        }

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

    public String getColumn() {
        return column;
    }

    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public String getValue() {
        return value;
    }
}
