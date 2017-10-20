package com.yangyang.rabbitmq.deadqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DeadQueueConsumer {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Constant.queueName, true, false, false, null);

        //指定消费队列
        //创建队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列
        channel.basicConsume(Constant.queueName, true, consumer);
        System.out.println("消费程序已经启动");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (true) {
            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            try {
                String msg = new String(delivery.getBody());
                System.out.println("设置时间=" + msg + "   执行时间=" + simpleDateFormat.format(new Date()));
            } catch (Exception e) {
                System.out.println("translation" + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
