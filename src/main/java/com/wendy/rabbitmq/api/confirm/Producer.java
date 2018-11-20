package com.wendy.rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hwd
 * @date 2018/10/25
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建并设置ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPort(5672);
        connectionFactory.setHost("10.0.3.5");
        connectionFactory.setVirtualHost("/");

        // 获取Connection
        Connection connection = connectionFactory.newConnection();

        // 通过Connection创建一个新的Channel
        Channel channel = connection.createChannel();

        // 指定消息投递模式：消息确认模式
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        // 发送一条消息
        String msg = "Send confirm message";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        // 添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("————Ack!——————");
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("————No Ack!——————");
            }
        });
    }
}
