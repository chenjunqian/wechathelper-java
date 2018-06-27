package com.wechathelper.repository;

import com.wechathelper.model.AutoReplyMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoReplyTextRepository extends JpaRepository<AutoReplyMessage, Long> {

    AutoReplyMessage findByWechatId(String wechatId);
}
