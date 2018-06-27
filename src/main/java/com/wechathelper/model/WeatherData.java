package com.wechathelper.model;

import javax.persistence.Entity;

@Entity
public class WeatherData extends BaseEntity {

    private String city;
    private String wendu;
    private String shidu;
    private String pm25;
    private String quality;
    private String notice;
    private String date;

    public WeatherData(String city, String shidu, String pm25, String quality, String notice, String date) {
        this.city = city;
        this.shidu = shidu;
        this.pm25 = pm25;
        this.quality = quality;
        this.notice = notice;
        this.date = date;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
