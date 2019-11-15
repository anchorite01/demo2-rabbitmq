package com.example.demo2.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 延时消息的实现 - 插件实现
 * @Author zhenghao
 * @Date 2019/9/12 17:41
 */

@Configuration
public class DelayConfig {
    @Bean
    public CustomExchange delayExchange() { // 延时交换机
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        // 使用的是CustomExchange,不是DirectExchange，x-delayed-message是插件提供的类型，并不是rabbitmq本身的
        return new CustomExchange("delayExchange", "x-delayed-message",true, false,args);
    }

    @Bean
    public Queue delayQueue1() { // 延时队列
        Queue queue = new Queue("delay_queue_1", true);
        return queue;
    }

    @Bean
    public Binding delayBinding1() { // 绑定
        return BindingBuilder.bind(delayQueue1()).to(delayExchange()).with("delay_queue_1").noargs();
    }


}
