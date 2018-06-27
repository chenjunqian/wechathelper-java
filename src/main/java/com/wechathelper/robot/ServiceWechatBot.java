package com.wechathelper.robot;

import com.wechathelper.model.User;
import com.wechathelper.model.WeatherData;
import com.wechathelper.model.WeatherTask;
import com.wechathelper.repository.UserRepository;
import com.wechathelper.repository.WeatherDataRepository;
import com.wechathelper.repository.WeatherTaskRepository;
import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.constant.Config;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceWechatBot extends WeChatBot {

    @Autowired
    private WeatherTaskRepository weatherTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    public ServiceWechatBot(Config config) {
        super(config);
        init();
    }

    private void init(){
        WeatherTaskThread weatherTaskThread = new WeatherTaskThread();
        weatherTaskThread.run();
    }


    private class WeatherTaskThread extends Thread{

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(5000);
                    int minute = LocalDateTime.now().getMinute();
                    int hour = LocalDateTime.now().getHour();
                    List<WeatherTask> weatherTaskList = weatherTaskRepository.findByTaskTimeHourAndTaskTimeHour(minute,hour);
                    for (WeatherTask weatherTask : weatherTaskList){
                        String wechatId = weatherTask.getWechatId();
                        User user = userRepository.findByWechatId(wechatId);
                        WeatherData weatherData = weatherDataRepository.findByCity(user.getCity());
                        ServiceWechatBot.this.sendMsg(user.getWechatId(),formatWeatherMessage(weatherData));
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        private String formatWeatherMessage(WeatherData weatherData){
            String weatherMessage =
                    "今日天气情况：\n"
                            +"温度 : "+weatherData.getWendu()+"\n"
                            +"湿度 : "+weatherData.getShidu()+"\n"
                            +"PM 2.5 值 : "+weatherData.getPm25()+"\n"
                            +"空气质量 : "+weatherData.getQuality()+"\n"
                            +"温馨提示 : "+weatherData.getNotice();
            return weatherMessage;
        }
    }
}
