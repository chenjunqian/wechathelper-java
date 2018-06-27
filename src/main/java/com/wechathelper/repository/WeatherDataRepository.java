package com.wechathelper.repository;

import com.wechathelper.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData,Long> {

    WeatherData findByDate(String date);
    WeatherData findByCity(String city);
}
