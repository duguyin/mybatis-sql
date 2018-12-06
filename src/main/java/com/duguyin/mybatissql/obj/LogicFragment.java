package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.enums.ComparisonOperator;
import com.duguyin.mybatissql.enums.LogicOperator;
import com.duguyin.mybatissql.exceptions.ParseException;
import com.duguyin.mybatissql.tool.StringTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName LogicFragment
 * @Description
 * @Author LiuYin
 * @Date 2018/9/28 14:04
 */
public class LogicFragment {

    private Map<String, PropertyColumnMapping> mappingMap;
    private List<CompareFragment> compareFragments = new ArrayList<>();
    private List<LogicFragment> children = new ArrayList<>();
    private List<LogicOperator> logicOperators = new ArrayList<>();

//    int level = 0;

    public LogicFragment begin(String sugar){
        return add(createCompareFragment(sugar));
    }

    public LogicFragment(Map<String, PropertyColumnMapping> mappingMap) {
        Objects.requireNonNull(mappingMap);
        this.mappingMap = mappingMap;
    }

    public LogicFragment(CompareFragment compareFragment){
        add(compareFragment);
    }



    public LogicFragment(String sugar){
        add(createCompareFragment(sugar));
    }

    public LogicFragment and(String sugar){
        return and(createCompareFragment(sugar));
    }

    public LogicFragment or(String sugar){
        return or(createCompareFragment(sugar));
    }


    private CompareFragment createCompareFragment(String sugar) {
        Objects.requireNonNull(sugar);
        String[] split = sugar.split("\\.");
        if(split.length == 0 || split.length > 2){
            throw new ParseException("sugar format error");
        }
        String property = split[0];
        if(StringTool.isNullOrEmpty(property)){
            throw new ParseException("property is null or empty");
        }

        String operatorSugar = split.length < 2 ? null : split[1];

        return new CompareFragment().column(property).value(property).operator(createBySugar(operatorSugar));
    }

    private ComparisonOperator createBySugar(String operatorSugar){
        if(StringTool.isNullOrEmpty(operatorSugar)){
            return ComparisonOperator.EQ;
        }
        switch (operatorSugar){
            case "eq":
            case "=":
                return ComparisonOperator.EQ;
            case "<":
            case "lt":
                return ComparisonOperator.LT;
            case ">":
            case "gt":
                return ComparisonOperator.GT;
            case "lte":
            case "<=":
                return ComparisonOperator.LTE;
            case "gte":
            case ">=":
                return ComparisonOperator.GTE;
            case "like":
            case "_":
                return ComparisonOperator.LIKE;
            case "%like":
            case "%_" :
                return ComparisonOperator.LEFT_LIKE;
            case "like%":
            case "_%":
                return ComparisonOperator.RIGTH_LIKE;
            case "%like%":
            case "%%":
                return ComparisonOperator.BOTH_LIKE;
            case "neq":
            case "<>":
            case "x":
                return ComparisonOperator.NEQ;
            case "n":
                return ComparisonOperator.IS_NULL;
            case "nn":
                return ComparisonOperator.IS_NOT_NULL;
            default:
                throw new ParseException("operator is error:" + operatorSugar);
        }
    }


    public String toSqlFragment(){
        return toSqlFragment(new StringBuilder(), mappingMap);
    }


    public String toSqlFragment(StringBuilder fragment, Map<String, PropertyColumnMapping> mappingMap){

        final int size = compareFragments.size();
        final int childrenSize = children.size();
        if(size == 0 && childrenSize == 0){
            return "";
        }

        // 逻辑比较符索引
        int index = 0;
        // 先解析本身的条件表达式
        if(size > 0){
            for(int i = 0 ; i < size ; i ++){
                final CompareFragment compareFragment = compareFragments.get(i);
                compareFragment.check();
                if(Objects.nonNull(mappingMap) && !mappingMap.isEmpty()){
                    compareFragment.reBuild(mappingMap);
                }
                fragment.append(compareFragment.getFragment());
                if(i < size - 1){
                    fragment.append(logicOperators.get(i).getOperator());
                    index ++;
                }
            }
        }
        // 再解析子条件片段
        if(childrenSize > 0){
            for(int i = 0; i < childrenSize; i ++){
                if(size > 0){
                    fragment.append(logicOperators.get(i + index).getOperator());
                }
                final LogicFragment logicFragment = children.get(i);
                boolean hasMany = logicFragment.compareFragments.size() > 1;
                if(hasMany){
                    fragment.append(" ( ");
                }
                logicFragment.toSqlFragment(fragment, mappingMap);
                if(hasMany){
                    fragment.append(" ) ");
                }
                if(size == 0 && i < childrenSize - 1){
                    fragment.append(logicOperators.get(i + index).getOperator());
                }
            }
        }
        return fragment.toString();
    }

    public LogicFragment add(CompareFragment compareFragment){
        return add(compareFragment, null);
    }

    public LogicFragment and(CompareFragment compareFragment){
        return add(compareFragment,LogicOperator.AND);
    }

    public LogicFragment or(CompareFragment compareFragment){
        return add(compareFragment,LogicOperator.OR);
    }

    public LogicFragment and(LogicFragment logicFragment){
        return addChild(logicFragment, LogicOperator.AND);
    }

    public LogicFragment or(LogicFragment logicFragment){
        return addChild(logicFragment, LogicOperator.OR);
    }

    public LogicFragment addChild(LogicFragment logicFragment){
        return addChild(logicFragment, null);
    }


    private LogicFragment add(CompareFragment compareFragment, LogicOperator logicOperator){
        Objects.requireNonNull(compareFragment);
        compareFragment.check();
        compareFragments.add(compareFragment);

        if(Objects.nonNull(logicOperator)){
            logicOperators.add(logicOperator);
        }
//        compareFragment.check();
//        final PropertyColumnMapping propertyColumnMapping = mappingMap.get(compareFragment.getColumn());
//        Objects.requireNonNull(propertyColumnMapping);
//
//        String newColumn = propertyColumnMapping.getColumn();
//        String newValue = "#{"+propertyColumnMapping.getProperty()+"}";
//        compareFragments.add(compareFragment.column(newColumn).value(newValue));
        return this;
    }

    public LogicFragment addChild(LogicFragment logicFragment, LogicOperator logicOperator){
        Objects.requireNonNull(logicFragment);
        logicFragment.setMappingMap(this.mappingMap);

        if(Objects.nonNull(logicOperator)){
            logicOperators.add(logicOperator);
        }
        children.add(logicFragment);
        return this;
    }

    public void setMappingMap(Map<String, PropertyColumnMapping> mappingMap) {
        this.mappingMap = mappingMap;
    }
}
