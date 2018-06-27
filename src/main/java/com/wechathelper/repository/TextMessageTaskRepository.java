package com.wechathelper.repository;

import com.wechathelper.model.TextMessageTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextMessageTaskRepository extends JpaRepository<TextMessageTask, Long> {

    List<TextMessageTask> findAllByWechatId(String wechatId);
}
