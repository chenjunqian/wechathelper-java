package com.wechathelper.repository;

import com.wechathelper.model.AutoReplyToUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoReplyToUserRepository extends JpaRepository<AutoReplyToUser, Long> {

    AutoReplyToUser findByWechatIdAndAndReplyToWechatId(String wechatId, String replyToWechatId);
}
