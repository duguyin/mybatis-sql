package com.duguyin.mybatissql;

import com.duguyin.mybatissql.annotations.Column;
import com.duguyin.mybatissql.annotations.Table;

import java.sql.Timestamp;

@Table("user_action")
public class UserAction {

    private long id;
    private int age;
    @Column("create_time")
    private Timestamp createTime;
    @Column("before_result")
    private String beforeResult;

}
