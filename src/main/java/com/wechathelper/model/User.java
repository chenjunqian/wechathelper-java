package com.wechathelper.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class User extends BaseEntity{

    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "*Please provide your password")
    @Transient
    private String password;

    @Column(name = "wechat_id")
    private String wechatId;

    private String city;

    @Column(name = "is_get_weather")
    private boolean isGetWeather;

    @Column(name = "is_need_service")
    private boolean isNeedService;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Role> roles;

    protected User(){}

    public User(java.lang.String username, java.lang.String wechatId, java.lang.String city, boolean isGetWeather, boolean isNeedService){
        this.username = username;
        this.wechatId = wechatId;
        this.city = city;
        this.isGetWeather = isGetWeather;
        this.isNeedService = isNeedService;
    }

    public java.lang.String getUsername() {
        return username;
    }

    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    public java.lang.String getWechatId() {
        return wechatId;
    }

    public void setWechatId(java.lang.String wechatId) {
        this.wechatId = wechatId;
    }

    public java.lang.String getCity() {
        return city;
    }

    public void setCity(java.lang.String city) {
        this.city = city;
    }

    public boolean isGetWeather() {
        return isGetWeather;
    }

    public void setGetWeather(boolean getWeather) {
        isGetWeather = getWeather;
    }

    public boolean isNeedService() {
        return isNeedService;
    }

    public void setNeedService(boolean needService) {
        isNeedService = needService;
    }

    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
