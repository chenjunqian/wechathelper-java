package com.wechathelper.repository;

import com.wechathelper.model.WeatherTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeatherTaskRepository extends JpaRepository<WeatherTask,Long> {
    List<WeatherTask> findByWechatId(String wechatId);

    List<WeatherTask> findByTaskTimeHourAndTaskTimeHour(int taskTimeMinute,int taskTimeHour);
}
