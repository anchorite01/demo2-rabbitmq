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
 * @Author zhenghao
 * @Date 2019/9/12 14:06
 */
@Configuration
public class FanoutConf {

    @Bean(name="messageA")
    public Queue AMessage() {
        return new Queue("fanout.A");
    }


    @Bean(name="messageB")
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean(name="messageC")
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");//配置广播路由器
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