package com.wechathelper.model;

import javax.persistence.Entity;

@Entity
public class TextMessageTask extends BaseEntity {

    private String wechatId;
    private String toUsername;
    private Integer toUsernId;
    private String message;
    private int taskTimeMinute;
    private int taskTimeHour;
    private boolean isCancelTask;

    public TextMessageTask(String wechatId, String toUsername, Integer toUsernId, String message, int taskTimeMinute, int taskTimeHour, boolean isCancelTask) {
        this.wechatId = wechatId;
        this.toUsername = toUsername;
        this.toUsernId = toUsernId;
        this.message = message;
        this.taskTimeMinute = taskTimeMinute;
        this.taskTimeHour = taskTimeHour;
        this.isCancelTask = isCancelTask;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public Integer getToUsernId() {
        return toUsernId;
    }

    public void setToUsernId(Integer toUsernId) {
        this.toUsernId = toUsernId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTaskTimeMinute() {
        return taskTimeMinute;
    }

    public void setTaskTimeMinute(int taskTimeMinute) {
        this.taskTimeMinute = taskTimeMinute;
    }

    public int getTaskTimeHour() {
        return taskTimeHour;
    }

    public void setTaskTimeHour(int taskTimeHour) {
        this.taskTimeHour = taskTimeHour;
    }

    public boolean isCancelTask() {
        return isCancelTask;
    }

    public void setCancelTask(boolean cancelTask) {
        isCancelTask = cancelTask;
    }
}
