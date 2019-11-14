package com.example.demo2.controller;

import com.example.demo2.model.User;
import com.example.demo2.mq.Sender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description rabbit测试Controller
 * @Author zhenghao
 * @Date 2019/9/12 11:02
 */
@RestController
public class DemoController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Sender sender;

    @GetMapping("hello")
    public String sayHello() {
        // 消息
        System.out.println("--------------------- 完全匹配消息 ------------------------");
        List<Object> list = new ArrayList<>();
        list.add("hello");
        list.add(new User(10L, 20, "张三", "123321", new Date()));
        rabbitTemplate.convertAndSend("queue", list);

        System.out.println("--------------------- 通配符匹配消息 ------------------------");
        // 匹配路由
        rabbitTemplate.convertAndSend("exchange", "topic.**", "controller："
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS").format(new Date()));
        rabbitTemplate.convertAndSend("exchange", "topic.message", "controller："
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS").format(new Date()));

        System.out.println("--------------------- 广播消息 ------------------------");
        // 广播路由
        rabbitTemplate.convertAndSend("fanoutExchange", null, "hello, rabbitmq"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS").format(new Date()));


        System.out.println("--------------------- 延时消息 ------------------------");
        sender.sendDelayMessage("delay_queue_1", "这是延时消息！");
        sender.sendDelayMessage2("这是延时消息2！");
        return "hello";
    }

}
