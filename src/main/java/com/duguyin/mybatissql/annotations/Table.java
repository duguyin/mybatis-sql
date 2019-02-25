package com.duguyin.mybatissql.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 表的注解
 */
public @interface Table {

    /** 表名称*/
    String value();

    /** 自动搜索，如果autoScan为true，只要类上有这个Table注解，则无论类filed是否有Column注解，都会认为是列*/
    boolean autoScan() default false;

}
