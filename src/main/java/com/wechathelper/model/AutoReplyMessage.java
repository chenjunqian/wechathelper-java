package com.wechathelper.model;

import javax.persistence.Entity;

@Entity
public class AutoReplyMessage extends BaseEntity{

    private String wechatId;
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

}
