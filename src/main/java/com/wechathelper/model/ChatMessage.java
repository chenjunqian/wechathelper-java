package com.wechathelper.model;

import javax.persistence.Entity;

@Entity
public class ChatMessage extends BaseEntity {

    private String userId;
    private String toUserId;
    private String message;

    public ChatMessage(String userId, String toUserId, String message) {
        this.userId = userId;
        this.toUserId = toUserId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
