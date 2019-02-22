package com.duguyin.mybatissql.enums;

/**
 * 基础sql枚举类
 */
public enum BaseSqlEnum {

    INSERT("insert"),
    UPDATE_BY_PRIMARYKEY("update_by_primaryKey"),
    DELETE_BY_PRIMARYKEY("delete_by_primaryKey"),
    SELECT_BY_PRIMARYKEY("select_by_primaryKey"),

    ;



    private String sugar;

    BaseSqlEnum(String sugar){
        this.sugar = sugar;
    }

    public String sugar(){
        return this.sugar;
    }




}
