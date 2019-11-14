package com.example.demo2.conf;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description Direct模式（精准匹配模式）
 * @author zhenghao
 * @date 14:37 2019/9/12
 **/
@Configuration
public class DirectConfig {

    @Bean
    public Queue queue() {
        return new Queue("queue");
    }
}
