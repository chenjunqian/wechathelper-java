package com.wechathelper.repository;

import com.wechathelper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByCity(String city);
    User findByUsername(String username);
    User findByWechatId(String wechatId);
}
