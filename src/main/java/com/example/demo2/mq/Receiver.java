package com.example.demo2.mq;


import com.example.demo2.conf.Delay2Config;
import com.example.demo2.conf.DirectConfig;
import com.example.demo2.conf.FanoutConf;
import com.example.demo2.utils.DateUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * @Description: 接收消息
 * @Author: zhenghao
 * @Date: 2019/11/14 14:56
 */
@Component
public class Receiver {

    // 完全匹配消息
    @RabbitListener(queues= DirectConfig.DIRECT_QUEUE)    //监听器监听指定的Queue
    public void processC(List list) {
        System.out.println("Receive1:"+list);
    }
    // 完全匹配消息
    @RabbitListener(queues=DirectConfig.DIRECT_QUEUE)    //监听器监听指定的Queue
    public void processD(List list) {
        System.out.println("Receive2:"+list);
    }

    // 通配符匹配消息
    @RabbitListener(queues="topic.message")    //监听器监听指定的Queue
    public void process1(String str) {
        System.out.println("message:"+str);
    }
    @RabbitListener(queues="topic.messageX")    //监听器监听指定的Queue
    public void processX(String str) {
        System.out.println("messageX:"+str);
    }
    @RabbitListener(queues="topic.messages1")    //监听器监听指定的Queue
    public void process2(String str) {
        System.out.println("messages1:"+str);
    }
    @RabbitListener(queues="topic.messages2")    //监听器监听指定的Queue
    public void process3(String str) {
        System.out.println("messages2:"+str);
    }

    // 广播消息
    @RabbitListener(queues= FanoutConf.MESSAGE_A)
    public void processA(String str) {
        System.out.println("ReceiveA:"+str);
    }
    @RabbitListener(queues= FanoutConf.MESSAGE_B)
    public void processB(String str) {
        System.out.println("ReceiveB:"+str);
    }
    @RabbitListener(queues= FanoutConf.MESSAGE_C)
    public void processC(String str) {
        System.out.println("ReceiveC:"+str);
    }

    // 延时消息
    @RabbitListener(queues="delay_queue_1")
    public void delayQueue1(String msg) {
        System.out.println(msg + " 接收时间：" + DateUtil.nowDateFormat());
    }
    @RabbitListener(queues= Delay2Config.REAL_MSG_QUEUE)
    public void query2(String msg) {
        System.out.println(msg + "\n接收时间：" + DateUtil.nowDateFormat());
    }
}