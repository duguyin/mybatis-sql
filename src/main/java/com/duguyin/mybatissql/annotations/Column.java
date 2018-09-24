package com.duguyin.mybatissql.annotations;

import org.apache.ibatis.type.JdbcType;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    String value() default "";

    JdbcType jdbcType() default JdbcType.UNDEFINED;

    boolean primaryKey() default false;
}
