package com.example.demo2.controller;

import com.example.demo2.conf.Delay2Config;
import com.example.demo2.conf.DelayConfig;
import com.example.demo2.conf.DirectConfig;
import com.example.demo2.conf.FanoutConf;
import com.example.demo2.model.User;
import com.example.demo2.mq.MqAnnotation;
import com.example.demo2.mq.Sender;
import com.example.demo2.utils.DateUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private MqAnnotation mqAnnotation;

    @GetMapping("/send")
    public String send(String exchange, String routingKey, String message) {
        mqAnnotation.send(exchange, routingKey, message);
        return "SUCCESS";
    }


    @GetMapping("hello")
    public String sayHello() {
        // 消息
        System.out.println("--------------------- 完全匹配消息 ------------------------");
        List<Object> list = new ArrayList<>();
        list.add("hello");
        list.add(new User(10L, 20, "张三", "123321", new Date()));
        rabbitTemplate.convertAndSend(DirectConfig.DIRECT_QUEUE, list);

        System.out.println("--------------------- 通配符匹配消息 ------------------------");
        // 匹配路由
        rabbitTemplate.convertAndSend("exchange", "topic.**", "controller："
                + DateUtil.nowDateFormat());
        rabbitTemplate.convertAndSend("exchange", "topic.message", "controller："
                + DateUtil.nowDateFormat());

        System.out.println("--------------------- 广播消息 ------------------------");
        // 广播路由
        rabbitTemplate.convertAndSend(FanoutConf.FANOUT_EXCHANGE, null, "hello, rabbitmq"
                + DateUtil.nowDateFormat());


        System.out.println("--------------------- 延时消息 ------------------------");
        sender.sendDelayMessage(DelayConfig.DELAY_QUEUE1, "这是延时消息！", 3000);
        sender.sendDelayMessage2(Delay2Config.DELAY_MSG_QUEUE, "这是延时消息2！", 5000);
        return "hello";
    }

}
