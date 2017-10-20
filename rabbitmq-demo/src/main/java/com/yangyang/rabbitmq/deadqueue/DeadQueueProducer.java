package com.yangyang.rabbitmq.deadqueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DeadQueueProducer {

    public static void main(String[] args) throws Exception{
        //创建连接连接到MabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        //设置MabbitMQ所在主机ip或者主机名
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        //创建一个连接
        Connection connection =factory.newConnection();
        //创建一个频道
        Channel channel = connection.createChannel();

        //指定一个队列
        channel.exchangeDeclare(Constant.exchangeName,"direct");
        channel.queueDeclare(Constant.queueName, true, false, false, null);
        channel.queueBind(Constant.queueName, Constant.exchangeName, Constant.routingKey);

        //创建delay queue
        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-dead-letter-exchange", Constant.exchangeName);
        arguments.put("x-dead-letter-routing-key", Constant.routingKey);

        channel.exchangeDeclare(Constant.delayExchangeName,"direct");
        channel.queueDeclare(Constant.delayQueueName, true, false, false, arguments);
        channel.queueBind(Constant.delayQueueName,Constant.delayExchangeName,Constant.delayRoutingKey);


        // 设置延时属性
        // 持久性 non-persistent (1) or persistent (2)
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        AMQP.BasicProperties properties = builder.expiration("5000").build();

        //发送的消息
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = "hello world!" + dateFormat.format(new Date());
        //往队列中发出一条消息     这时候要发送的队列不应该是QUEUE_NAME，这样才能进行转发的
        channel.basicPublish(Constant.delayExchangeName, Constant.delayRoutingKey, properties, message.getBytes());
        System.out.println("sent message: " + message + ",date:" + dateFormat.format(new Date()));

        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
