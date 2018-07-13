package com.wechathelper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class AutoReplyToUser extends BaseEntity {

    @Column(name = "reply_to_wechat_id")
    private String replyToWechatId;

    @Column(name = "wechat_id")
    private String wechatId;

    @Column(name = "is_auto_reply_by_custom_message")
    private boolean isAutoReplyByCustomMessage;

    @Column(name = "is_auto_reply_by_bot_message")
    private boolean isAutoReplyByBotMessage;

    @Column(name = "custom_message")
    private String customMessage;

    public AutoReplyToUser() {
    }

    public AutoReplyToUser(String replyToWechatId, String wechatId, boolean isAutoReplyByCustomMessage, boolean isAutoReplyByBotMessage, String customMessage) {
        this.replyToWechatId = replyToWechatId;
        this.wechatId = wechatId;
        this.isAutoReplyByCustomMessage = isAutoReplyByCustomMessage;
        this.isAutoReplyByBotMessage = isAutoReplyByBotMessage;
        this.customMessage = customMessage;
    }

    public String getWechatId() {
        return replyToWechatId;
    }

    public void setWechatId(String replyToWechatId) {
        this.replyToWechatId = replyToWechatId;
    }

    public String getUser() {
        return wechatId;
    }

    public void setUser(String wechatId) {
        this.wechatId = wechatId;
    }

    public boolean isAutoReplyByCustomMessage() {
        return isAutoReplyByCustomMessage;
    }

    public void setAutoReplyByCustomMessage(boolean autoReplyByCustomMessage) {
        isAutoReplyByCustomMessage = autoReplyByCustomMessage;
    }

    public boolean isAutoReplyByBotMessage() {
        return isAutoReplyByBotMessage;
    }

    public void setAutoReplyByBotMessage(boolean autoReplyByBotMessage) {
        isAutoReplyByBotMessage = autoReplyByBotMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
}
