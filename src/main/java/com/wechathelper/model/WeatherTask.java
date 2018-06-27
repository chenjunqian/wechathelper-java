package com.wechathelper.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class WeatherTask extends BaseEntity {

    @OneToMany
    private List<WeatherForecastData> weatherForecastData;
    private String wechatId;
    private int taskTimeMinute;
    private int taskTimeHour;

    public WeatherTask( String wechatId, int taskTimeMinute, int taskTimeHour) {
        this.wechatId = wechatId;
        this.taskTimeMinute = taskTimeMinute;
        this.taskTimeHour = taskTimeHour;
    }

    public List<WeatherForecastData> getWeatherForecastData() {
        return weatherForecastData;
    }

    public void setWeatherForecastData(List<WeatherForecastData> weatherForecastData) {
        this.weatherForecastData = weatherForecastData;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
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
}
