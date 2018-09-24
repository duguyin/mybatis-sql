package com.duguyin.mybatissql;

import java.sql.Timestamp;

public class UserTable {

    private long id;
    private String name;
    private String userAction;
    private String beforeResult;
    private Timestamp createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getBeforeResult() {
        return beforeResult;
    }

    public void setBeforeResult(String beforeResult) {
        this.beforeResult = beforeResult;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        String sb = "UserTable{" + "id=" + id +
                ", name='" + name + '\'' +
                ", userAction='" + userAction + '\'' +
                ", beforeResult='" + beforeResult + '\'' +
                ", createTime=" + createTime +
                '}';
        return sb;
    }
}
