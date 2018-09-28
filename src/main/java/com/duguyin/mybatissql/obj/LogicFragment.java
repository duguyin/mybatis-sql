package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.enums.LogicOperator;
import com.duguyin.mybatissql.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName LogicFragment
 * @Description
 * @Author LiuYin
 * @Date 2018/9/28 14:04
 */
public class LogicFragment {

    List<CompareFragment> compareFragments = new ArrayList<>();
    List<LogicFragment> children = new ArrayList<>();
    List<LogicOperator> logicOperators = new ArrayList<>();

//    int level = 0;

    public LogicFragment() {}




    public String toSqlFragment(StringBuilder fragment){

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
                logicFragment.toSqlFragment(fragment);
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
        compareFragments.add(compareFragment);
        if(Objects.nonNull(logicOperator)){
            logicOperators.add(logicOperator);
        }
        return this;
    }

    public LogicFragment addChild(LogicFragment logicFragment, LogicOperator logicOperator){
        children.add(logicFragment);
        if(Objects.nonNull(logicOperator)){
            logicOperators.add(logicOperator);
        }
        return this;
    }









//    private String connect(StringBuilder builder, int level){
//        Objects.requireNonNull(builder, "builder is null");
//
//        final int compareFragmentsSize = compareFragments.size();
//        if(compareFragmentsSize < 1){
//            throw new ParseException("compare fragment list size must grater than zero");
//        }
//
//        final int logicOperatorsSize = logicOperators.size();
//        final int childrenSize = children.size();
//        if(childrenSize  == 0 && logicOperatorsSize < compareFragmentsSize - 1){
//            throw new ParseException("logic operator list size must grater than fragment size sub one");
//        }
//        if(childrenSize > 0 && logicOperatorsSize < compareFragmentsSize){
//            throw new ParseException("logic operator list size must equal to fragment size");
//        }
//
//
//        if(level > 0){
//            builder.append(" ( ");
//        }
//
//        for(int i = 0; i< compareFragmentsSize; i ++ ){
//            final CompareFragment compareFragment = compareFragments.get(i);
//
//            builder.append(compareFragment.getFragment());
//            if( i < compareFragmentsSize - 1){
//                builder.append(logicOperators.get(i));
//            }
//        }
//
//        if(childrenSize > 0){
//            builder.append(logicOperators.get(logicOperatorsSize - 1));
//            final int newLevel = level + 1;
//            children.forEach(f -> connect(builder, newLevel));
//        }
//
//        if(level > 0){
//            builder.append(" ) ");
//        }
//        return builder.toString();
//
//    }
//
//    public void add(CompareFragment compareFragment){
//        Objects.requireNonNull(compareFragment, "compare fragment is null");
//        compareFragments.add(compareFragment);
//    }




}
