package com.example.demo2;

import com.example.demo2.conf.Delay2Config;
import com.example.demo2.mq.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo2ApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Sender sender;

    @Test
    public void hello() throws Exception {
        sender.send(99);
    }


    @Test
    public void contextLoads() throws InterruptedException {
        rabbitTemplate.convertAndSend("exchange", "topic.**", "controller："
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS").format(new Date()));
        rabbitTemplate.convertAndSend("exchange", "topic.message", "controller："
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS").format(new Date()));


        /*rabbitTemplate.convertAndSend("fanoutExchange", null, "hello, rabbitmq"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS").format(new Date()));*/
    }


    @Test
    public void delayQueue1Test() {
        sender.sendDelayMessage("delay_queue_1", "这是一条延时消息");
    }


    @Test
    public void queryTest2() {
        System.out.println("发送时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        rabbitTemplate.convertAndSend(Delay2Config.DELAY_MSG_QUEUE, "延时消息", message -> {
            message.getMessageProperties().setExpiration("3000");
            return message;
        });
    }
}
