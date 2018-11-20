package com.wendy.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hwd
 * @date 2018/10/9
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.0.3.5");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        // 声明一个队列
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);

        // 创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        // 设置channel，参数：队列名称，是否autoAck，consumer
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            // 获取消息
            Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端：" + msg);
        }
    }
}
