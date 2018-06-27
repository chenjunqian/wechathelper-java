package com.wechathelper.model;

import javax.persistence.Entity;

@Entity
public class Role extends BaseEntity {

    private String role;

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
