package com.recruit.model;

/**
 * @author 郭宏禧
 * @createTime 2017/8/15
 * @context **
 */
public class UserRole {
    private String user_id;
    private int role_id;
    private String role_name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "user_id='" + user_id + '\'' +
                ", role_id=" + role_id +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
