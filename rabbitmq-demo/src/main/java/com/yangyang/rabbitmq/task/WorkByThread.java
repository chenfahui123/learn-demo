package com.yangyang.rabbitmq.task;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

class WorkT implements Runnable {

	// 队列名称
	private final static String QUEUE_NAME = "workqueue";

	@Override
	public void run() {
		try {
			// 创建连接和频道
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			// 声明队列
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out.println(Thread.currentThread().getName() + " [*] Waiting for messages. To exit press CTRL+C");

			// 设置最大服务转发消息数量
			int prefetchCount = 1;
			// channel.basicQos(prefetchCount);
			QueueingConsumer consumer = new QueueingConsumer(channel);
			// 指定消费队列
			channel.basicConsume(QUEUE_NAME, false, consumer);
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());

				System.out.println(Thread.currentThread().getName() + " [x] Received '" + message + "'");
				doWork(message);
				System.out.println(Thread.currentThread().getName() + " [x] Done " + message);
			}

		} catch (Exception e) {
		}

	}

	/**
	 * 每个点耗时1s
	 * 
	 * @param task
	 * @throws InterruptedException
	 */
	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.')
				Thread.sleep(1000);
		}
	}

}

public class WorkByThread {

	public static void main(String[] argv) throws Exception {

		Thread t1 = new Thread(new WorkT());
		Thread t2 = new Thread(new WorkT());
		t1.start();
		t2.start();
	}

}
