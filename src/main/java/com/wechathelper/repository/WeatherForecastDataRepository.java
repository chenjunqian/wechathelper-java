package com.wechathelper.repository;

import com.wechathelper.model.WeatherData;
import com.wechathelper.model.WeatherForecastData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherForecastDataRepository extends JpaRepository<WeatherForecastData,Long> {

    List<WeatherForecastDataRepository> findAllByWeatherData(WeatherData weatherData);
}
