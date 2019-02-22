package com.duguyin.mybatissql.obj;


import org.apache.ibatis.type.JdbcType;

/**
 * 属性与列的对应关系
 * 如:property: createTime
 *    column: create_time
 */
public class PropertyColumnMapping {

    /** 属性名称*/
    private String property;
    /** java类型*/
    private Class<?> javaType;
    /** 列名称*/
    private String column;
    /** jdbc类型*/
    private JdbcType jdbcType;
    /** 是否是主键*/
    private boolean primaryKey;


    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }



    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
