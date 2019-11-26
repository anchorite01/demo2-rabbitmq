package com.example.demo2;

import com.example.demo2.conf.Delay2Config;
import com.example.demo2.mq.Sender;
import com.example.demo2.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo2ApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Sender sender;

    @Test
    public void hello() throws Exception {
        for (int i = 0; i < 5; i++) {
            sender.send(i + 1);
        }
    }

    @Test
    public void contextLoads() throws InterruptedException {
        // 模糊匹配发送
        rabbitTemplate.convertAndSend("exchange", "topic.message", "controller："
                + DateUtil.nowDateFormat());
        rabbitTemplate.convertAndSend("exchange", "topic.xx", "controller："
                + DateUtil.nowDateFormat());
        rabbitTemplate.convertAndSend("exchange", "topic.#", "controller："
                + DateUtil.nowDateFormat());
        rabbitTemplate.convertAndSend("exchange", "topic.message.hello", "controller："
                + DateUtil.nowDateFormat());

        /*rabbitTemplate.convertAndSend("fanoutExchange", null, "hello, rabbitmq"
                + DateUtil.nowDateFormat());*/
    }


    @Test
    public void delayQueue1Test() {
        // sender.sendDelayMessage("delay_queue_1", "这是一条延时消息");
        rabbitTemplate.convertAndSend("delayExchange", "delay_queue_1",  "延时消息1,发送时间:" + DateUtil.nowDateFormat() + "\n" , message -> {
            // 设置延时时间
            message.getMessageProperties().setHeader("x-delay", 10000);
            return message;
        });
    }


    @Test
    public void queryTest2() {
        System.out.println("发送时间:" + DateUtil.nowDateFormat());
        rabbitTemplate.convertAndSend(Delay2Config.DELAY_MSG_QUEUE, "延时消息2," + "发送时间:" + DateUtil.nowDateFormat(), message -> {
            message.getMessageProperties().setExpiration("5000");
            return message;
        });
    }




}
