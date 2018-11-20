package com.wendy.rabbitmq.api.message;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author hwd
 * @date 2018/10/9
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.0.3.5");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        Map<String, Object> headers = new HashMap<>();
        headers.put("my1", "111");
        headers.put("my2", "222");

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)    // 重启服务器消息还在队列中
                .contentEncoding("UTF-8")
                .expiration("10000")    // 消息在队列中的失效时间
                .headers(headers)
                .build();

        // 通过channel发送数据
        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ!";
            // exchange为空时，使用AMQP default，并使用与RoutingKey相同的queueName
            channel.basicPublish("", "test001", properties, msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
