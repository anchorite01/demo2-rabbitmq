package com.example.demo2.conf;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 延时消息的实现
 * @Author zhenghao
 * @Date 2019/9/12 17:41
 */

@Configuration
public class Delay2Config {
    /**
     * 短信发送队列
     */
    public static final String REAL_MSG_QUEUE = "real:msg:query";

    /**
     * 短信发送队列 DLX
     */
    public static final String DLX_MSG_EXCHANGE = "dlx:msg:exchange";

    /**
     * 短信发送队列 延迟缓冲（按消息）
     */
    public static final String DELAY_MSG_QUEUE = "delay:msg:query";

    /**
     * 延时队列
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_MSG_QUEUE)
                // 消息变成死信后转发到的交换机
                .withArgument("x-dead-letter-exchange", DLX_MSG_EXCHANGE)
                // 私信后重新按照指定的routing_key发送
                .withArgument("x-dead-letter-routing-key", REAL_MSG_QUEUE)
                .build();
    }

    /**
     * 真正发消息的队列（自定义dlxQueue）
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue(REAL_MSG_QUEUE, true);
    }

    /**
     * 自定义dlx交换机
     **/
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_MSG_EXCHANGE);
    }

    /**
     * 绑定
     */
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(REAL_MSG_QUEUE);
    }

}
