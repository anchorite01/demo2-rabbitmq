package com.example.demo2.task;

import com.example.demo2.mq.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description TODO
 * @Author zhenghao
 * @Date 2019/9/12 11:23
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class TaskDemo {
    @Autowired
    private Sender sender;

    /*//3.添加定时任务
    @Scheduled(cron = "0/2 * * * * ?")
    //或直接指定时间间隔，例如：2秒
    //@Scheduled(fixedRate=2000)
    private void configureTasks() {
        sender.send(1);
    }

    //或直接指定时间间隔，例如：2秒
    @Scheduled(fixedRate = 3000)
    private void task2() {
        sender.send(2);
    }
    @Scheduled(fixedRate = 5000)
    private void task3() {
        sender.send(3);
    }
    @Scheduled(fixedRate = 10000)
    private void task4() {
        for (int i = 0; i < 100; i++) {
            sender.send(i + 4);
        }
    }*/
}
