package com.example.demo2.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description Fanout形式又叫广播形式
 *      一个消费者将消息首先发送到交换器，交换器绑定到多个队列，然后被监听该队列的消费者所接收并消费。
 * @Author zhenghao
 * @Date 2019/9/12 14:06
 */
@Configuration
public class FanoutConf {
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String MESSAGE_A = "messageA";
    public static final String MESSAGE_B = "messageB";
    public static final String MESSAGE_C = "messageC";

    @Bean(name="messageA")
    public Queue AMessage() {
        return new Queue(MESSAGE_A);
    }


    @Bean(name="messageB")
    public Queue BMessage() {
        return new Queue(MESSAGE_B);
    }

    @Bean(name= FanoutConf.MESSAGE_C)
    public Queue CMessage() {
        return new Queue(FanoutConf.MESSAGE_C);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FanoutConf.FANOUT_EXCHANGE);//配置广播路由器
    }

    @Bean
    Binding bindingExchangeA(@Qualifier("messageA") Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(@Qualifier("messageB") Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(@Qualifier("messageC") Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

}