package com.example.demo2.mq;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo2.conf.Delay2Config;
import com.example.demo2.model.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 发送消息
 * @Author: zhenghao
 * @Date: 2019/11/14 14:57
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    // 日期格式化使用
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @Description: 发送消息
     * @Author: zhenghao
     * @Date: 2019/11/14 14:57
     */
    public void send(Integer task) {
        String context = "hello " + df.format(new Date());
        List<Object> list = new ArrayList<>();
        list.add(context);
        list.add(new User(10L, 20, "张三", "123321", new Date()));
//        System.out.println("Sender : " + list);
        System.out.println("Sender:" + task);
        this.rabbitTemplate.convertAndSend("direct_queue", list);
    }

    /**
     * @Description: 延时消息
     * @Author: zhenghao
     * @Date: 2019/11/14 14:57
     */
    public void sendDelayMessage(String queueName, String msg) {
        String nowDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        msg = "发送时间:" + nowDateStr + "\n" + " msg:" + msg;
        System.out.println(msg);
        rabbitTemplate.convertAndSend("delayExchange", queueName, msg, message -> {
            // 设置延时时间
            message.getMessageProperties().setHeader("x-delay", 5000);
            return message;
        });
    }
    public void sendDelayMessage2(String msg) {
        System.out.println("发送时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        rabbitTemplate.convertAndSend(Delay2Config.DELAY_MSG_QUEUE, msg, message -> {
            message.getMessageProperties().setExpiration("3000");
            return message;
        });
    }

}