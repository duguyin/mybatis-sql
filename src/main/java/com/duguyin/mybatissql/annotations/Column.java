package com.duguyin.mybatissql.annotations;

import org.apache.ibatis.type.JdbcType;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 列注解
 */
public @interface Column {

    /** 列的名称*/
    String value() default "";

    /** 列的jdbcType*/
    JdbcType jdbcType() default JdbcType.UNDEFINED;

    /** 是否是主键*/
    boolean primaryKey() default false;
}
