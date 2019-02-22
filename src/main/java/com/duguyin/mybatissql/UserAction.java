package com.duguyin.mybatissql;

import com.duguyin.mybatissql.annotations.Column;
import com.duguyin.mybatissql.annotations.Table;

import java.sql.Timestamp;

@Table(value = "user_action", autoScan = true)
public class UserAction {

    @Column(primaryKey = true)
    private long id;
    @Column("age_aaa")
    private int age;
    @Column("create_time")
    private Timestamp createTime;
    @Column("before_result")
    private String beforeResult;

}
