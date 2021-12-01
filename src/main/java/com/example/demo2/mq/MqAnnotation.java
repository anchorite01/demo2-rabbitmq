package com.example.demo2.mq;

import com.example.demo2.utils.SleepUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * rabbitmq测试
 *
 * @author zhenghao
 * @date 2021/11/30 13:11
 */
@Slf4j
@Component
public class MqAnnotation {
    @Resource
    private AmqpTemplate rabbitTemplate;
    private final static String QUEUE_NAME = "cs_name";
    private final static String TOPIC_KEY = "cs_key";
    private final static String EXCHANGE = "cs_yi_xia";

    /**
     * 发送消息
     *
     * @param json
     */
    public void send(String json) {
        rabbitTemplate.convertAndSend(EXCHANGE, TOPIC_KEY, json);
        log.info("send------1.E XCHANGE:{}-----TOPIC_KEY:{}-------meg:{}", EXCHANGE, TOPIC_KEY, json);
        rabbitTemplate.convertAndSend("cs.name", "laiya");
    }

    /**
     * 下面四个消费者，exchange和RoutingKey都相同，最后两个消费者队列名都相同
     *
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME, durable = "true"),
            exchange = @Exchange(value = EXCHANGE, type = ExchangeTypes.TOPIC),
            key = TOPIC_KEY)
    )
    public void queueName(@Payload String msg) {
        log.info("1.name:{}-----msg:{}", QUEUE_NAME, msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME + ".test", durable = "true"),
            exchange = @Exchange(value = EXCHANGE, type = ExchangeTypes.TOPIC),
            key = TOPIC_KEY)
    )
    public void queueNameTest(@Payload String msg) {
        log.info("2.name:{}-----msg:{}", QUEUE_NAME + ".test", msg);
    }

    /**
     * 这里我的消费者队列名"123445"，是乱写的，也能够接受
     *
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME + ".123445", durable = "true",
                    arguments = @Argument(name = "x-message-ttl", value = "5000", type = "java.lang.Integer")),
            exchange = @Exchange(value = EXCHANGE, type = ExchangeTypes.TOPIC),
            key = TOPIC_KEY
    )
    )
    public void queueNameNumber(@Payload String msg) {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        log.info("3.name:{}-----msg:{}", QUEUE_NAME + ".123445", msg);
    }

    /**
     * 由于这个和上面的Exchange、RoutingKey、queue完全相同，所以这两个消费者，一条消息，只有一个能消费(随机)
     *
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME + "123445", durable = "true"),
            exchange = @Exchange(value = EXCHANGE, type = ExchangeTypes.TOPIC),
            key = TOPIC_KEY)
    )
    public void queueNameNumberSame(@Payload String msg) {
        log.info("4.name:{}-----msg:{}", QUEUE_NAME + "123445", msg);
    }

    /**
     * 队列不存在时，需要创建一个队列，并且与exchange绑定
     * value: @Queue 注解，用于声明队列，value 为 queueName, durable 表示队列是否持久化（RabbitMQ重启后，交换机还存在）, autoDelete 表示没有消费者之后队列是否自动删除
     * exchange: @Exchange 注解，用于声明 exchange，type指定消息投递策略，我们这里用的topic方式 {@link org.springframework.amqp.core.ExchangeTypes}
     * key: 在 topic 方式下，这个就是我们熟知的 routingKey
     */
    @RabbitListener(bindings = @QueueBinding(
            // 声明式队列，队列名，持久化，自动删除
            value = @Queue(value = "topic.n1", durable = "false", autoDelete = "false"),
            // 声明式交换机，交换机名，类型
            exchange = @Exchange(value = "topic.e", type = ExchangeTypes.TOPIC),
            // 路由key
            key = "r"))
    public void consumerNoQueue(String data) {
        System.out.println("consumerNoQueue: " + data);
        SleepUtils.sleep(3);
    }

    /**
     * 需要手动ack，但是不ack时，会发现数据一直在unacked这一栏，当 Unacked 数量超过限制的时候，就不会再消费新的数据了
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "topic.n2", durable = "false", autoDelete = "false"),
            exchange = @Exchange(value = "topic.e", type = ExchangeTypes.TOPIC), key = "r"), ackMode = "MANUAL")
    public void consumerNoAck(String data) {
        // 要求手动ack，这里不ack，会怎样?
        System.out.println("consumerNoAck: " + data);
        SleepUtils.sleep(3);
    }


    private static int i = 0;

    /**
     * 手动ack消息，ackMode：NONE、MANUAL、AUTO
     * deliveryTag: 相当于消息的唯一标识，用于 mq 辨别是哪个消息被 ack/nak 了
     * channel: mq 和 consumer 之间的管道，通过它来 ack/nak
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "topic.n3", durable = "false", autoDelete = "false"),
            exchange = @Exchange(value = "topic.e", type = ExchangeTypes.TOPIC), key = "r"),
            ackMode = "MANUAL")
    public void consumerDoAck(String data, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel)
            throws IOException {
        System.out.println("consumerDoAck: " + data + ", i=" + i);
        SleepUtils.sleep(1);

        if (data.contains("success") || ++i > 3) {
            // RabbitMQ的ack机制中，第二个参数为true，表示需要将这条消息投递给其他的消费者重新消费
            channel.basicAck(deliveryTag, false);
            i = 0;
        } else {
            // 第三个参数true，表示这个消息会重新进入队列
            channel.basicNack(deliveryTag, false, true);
        }
    }

    /**
     * 并发消费
     * concurrency="4" 表示固定 4 个消费者
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "topic.n4", durable = "false", autoDelete = "false"),
            exchange = @Exchange(value = "topic.e", type = ExchangeTypes.TOPIC), key = "r"),
            concurrency = "4")
    public void multiConsumer(String data) {
        SleepUtils.sleep(2);
        System.out.println("multiConsumer: " + data);
    }


}
