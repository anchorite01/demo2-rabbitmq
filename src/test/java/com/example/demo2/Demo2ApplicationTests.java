package com.example.demo2;

import com.example.demo2.conf.Delay2Config;
import com.example.demo2.conf.DelayConfig;
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
        // 模糊匹配发送, 发送所有
//        rabbitTemplate.convertAndSend("topicExchange", "topic.message", "controller："
//                + DateUtil.nowDateFormat());
        // 一个都没匹配
//        rabbitTemplate.convertAndSend("topicExchange", "xxxxxx", "controller："
////                + DateUtil.nowDateFormat());
        // 模糊匹配
//        rabbitTemplate.convertAndSend("topicExchange", "topic.xx", "controller："
//                + DateUtil.nowDateFormat());
        // 模糊匹配
//        rabbitTemplate.convertAndSend("topicExchange", "topic.#", "controller："
//                + DateUtil.nowDateFormat());
        // 模糊匹配
        rabbitTemplate.convertAndSend("topicExchange", "topic.message.hello.aofjdsof", "controller："
                + DateUtil.nowDateFormat());

        /*rabbitTemplate.convertAndSend(FanoutConf.FANOUT_EXCHANGE, null, "hello, rabbitmq"
                + DateUtil.nowDateFormat());*/
    }


    @Test
    public void delayQueue1Test() {
        sender.sendDelayMessage(DelayConfig.DELAY_QUEUE1, "这是一条延时消息", 10000);
    }


    @Test
    public void queryTest2() {
        sender.sendDelayMessage2(Delay2Config.DELAY_MSG_QUEUE, "延时消息2," + "发送时间:" + DateUtil.nowDateFormat(), 5000);
    }




}
