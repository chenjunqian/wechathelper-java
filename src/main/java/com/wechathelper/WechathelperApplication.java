package com.wechathelper;

import com.wechathelper.robot.WechatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
public class WechathelperApplication {

    private static WechatService wechatService;

    private static ScheduledExecutorService scheduledExecutorService;

    public static WechatService getWechatService() {
        return wechatService;
    }

    public static ScheduledExecutorService getScheduledExecutorService(){
        return scheduledExecutorService;
    }

    public static void setWechatService(WechatService wechatService) {
        WechathelperApplication.wechatService = wechatService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WechathelperApplication.class, args);
    }

    @Component
    @Order(2)
    public class WechatServiceRunner implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            wechatService = new WechatService();
            wechatService.initServiceWechat();
            wechatService.runWechatServiceServer();

        }
    }

    @Component
    @Order(1)
    public class WechatSchedulerRunner implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            scheduledExecutorService = Executors.newScheduledThreadPool(10);
        }
    }

}
