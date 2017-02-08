package com.yangyang.rabbitmq.log.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;

public class EmitLog {
	private final static String EXCHANGE_NAME = "ex_log";

	public static void main(String[] args) throws Exception {
		// 创建连接和频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		// 声明转发器和类型
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		String message = new Date().toLocaleString() + " : log something";
		// 往转发器上发送消息
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());

		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();

	}

}