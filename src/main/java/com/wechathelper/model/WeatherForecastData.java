package com.wechathelper.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class WeatherForecastData extends BaseEntity{

    @OneToOne
    private WeatherData weatherData;
    private String city;
    private String date;
    private String high;
    private String low;
    private String fengli;
    private String weatherType;
    private String notice;

    public WeatherForecastData( String city, String date, String high, String low, String fengli, String weatherType, String notice) {
        this.city = city;
        this.date = date;
        this.high = high;
        this.low = low;
        this.fengli = fengli;
        this.weatherType = weatherType;
        this.notice = notice;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
