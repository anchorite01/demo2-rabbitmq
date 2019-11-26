package com.example.demo2.mq;

import com.example.demo2.conf.Delay2Config;
import com.example.demo2.conf.DirectConfig;
import com.example.demo2.model.User;
import com.example.demo2.utils.DateUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 发送消息
 * @Author: zhenghao
 * @Date: 2019/11/14 14:57
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * @Description: 发送消息
     * @Author: zhenghao
     * @Date: 2019/11/14 14:57
     */
    public void send(Integer task) {
        String context = "hello " + DateUtil.nowDateFormat();
        List<Object> list = new ArrayList<>();
        list.add(context);
        list.add(new User(10L, 20, "张三", "123321", new Date()));
//        System.out.println("Sender : " + list);
        System.out.println("Sender:" + task);
        this.rabbitTemplate.convertAndSend(DirectConfig.DIRECT_QUEUE, list);
    }

    /**
     * @Description: 延时消息
     * @Author: zhenghao
     * @Date: 2019/11/14 14:57
     */
    public void sendDelayMessage(String queueName, String msg, long second) {
        msg = "延时消息1,发送时间:" + DateUtil.nowDateFormat() + "\n" + " msg:" + msg;
        System.out.println(msg);
        rabbitTemplate.convertAndSend("delayExchange", queueName, msg, message -> {
            // 设置延时时间
            message.getMessageProperties().setHeader("x-delay", second);
            return message;
        });
    }
    public void sendDelayMessage2(String queueName, String msg, long second) {
        System.out.println("发送时间:" + DateUtil.nowDateFormat());
        rabbitTemplate.convertAndSend(queueName, msg, message -> {
            message.getMessageProperties().setExpiration("" + second);
            return message;
        });
    }

}