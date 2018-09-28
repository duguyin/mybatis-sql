package com.duguyin.mybatissql.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WhereFragment
 * @Description
 * @Author LiuYin
 * @Date 2018/9/28 15:36
 */
public class WhereFragment {

    private  List<LogicFragment> logicFragments = new ArrayList<>();

    public String toWhereSqlFragment(){
        final int size = logicFragments.size();
        if(size == 0){
            return "";
        }
        StringBuilder fragment = new StringBuilder();
        for(int i = 0; i < size; i++){
            fragment.append(logicFragments.get(i).toSqlFragment(new StringBuilder()));
        }
        return fragment.toString();
    }



    public List<LogicFragment> getLogicFragments() {
        return logicFragments;
    }

    public void setLogicFragments(List<LogicFragment> logicFragments) {
        this.logicFragments = logicFragments;
    }
}
