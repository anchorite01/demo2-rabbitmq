package com.example.demo2.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @description Topic转发模式（模糊匹配）
 *      符号“#”表示匹配一个或多个词，符号“*”表示匹配一个词。
 * @author zhenghao
 * @date 14:36 2019/9/12
 **/
@Configuration
public class TopicConf {

    @Bean(name="message")
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean(name="messageX")
    public Queue queueMessageX() {
        return new Queue("topic.messageX");
    }

    @Bean(name="messages1")
    public Queue queueMessages1() {
        return new Queue("topic.messages1");
    }

    @Bean(name="messages2")
    public Queue queueMessages2() {
        return new Queue("topic.messages2");
    }

    @Bean(name="otherMessage1")
    public Queue otherMessage1() {
        return new Queue("other.message1");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("message") Queue queueMessage) {
        return BindingBuilder.bind(queueMessage).to(topicExchange()).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessageX(@Qualifier("messageX") Queue queueMessageX) {
        return BindingBuilder.bind(queueMessageX).to(topicExchange()).with("topic.*");
    }

    @Bean
    Binding bindingExchangeMessages1(@Qualifier("messages1") Queue queueMessages1) {
        return BindingBuilder.bind(queueMessages1).to(topicExchange()).with("topic.#");//*表示一个词,#表示零个或多个词
    }
    @Bean
    Binding bindingExchangeMessages11(@Qualifier("messages1") Queue queueMessages1) {
        return BindingBuilder.bind(queueMessages1).to(topicExchange()).with("topicMessages1");//*表示一个词,#表示零个或多个词
    }
    @Bean
    Binding bindingExchangeMessages2(@Qualifier("messages2") Queue queueMessages2) {
        return BindingBuilder.bind(queueMessages2).to(topicExchange()).with("topic.#");//*表示一个词,#表示零个或多个词
    }
    @Bean
    Binding bindingOtherExchangeMessages() {
        return BindingBuilder.bind(otherMessage1()).to(topicExchange()).with("other.message.#");//*表示一个词,#表示零个或多个词
    }
}