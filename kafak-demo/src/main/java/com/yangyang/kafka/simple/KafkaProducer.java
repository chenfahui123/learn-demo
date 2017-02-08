package com.yangyang.kafka.simple;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class KafkaProducer extends Thread{
	
	private String topic;  
    
    public KafkaProducer(String topic){  
        super();  
        this.topic = topic;  
    }  
      
      
    @Override  
    public void run() {  
        Producer producer = createProducer();  
        int i=0;  
        while(i< 30 ){  
        	String msg = "message: " + i++;
            producer.send(new KeyedMessage<Integer, String>(topic, msg));  
            System.out.println("send message "+msg);
            try {  
                TimeUnit.SECONDS.sleep(1);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    private Producer createProducer() {  
        Properties properties = new Properties();  
        properties.put("zookeeper.connect", "localhost:2181,localhost:2182,localhost:2183");//声明zk  
        properties.put("serializer.class", StringEncoder.class.getName());  
        properties.put("metadata.broker.list", "localhost:9092,localhost:9093,localhost:9094");// 声明kafka broker  
        return new Producer<Integer, String>(new ProducerConfig(properties));  
     }  
      
      
    public static void main(String[] args) {  
        new KafkaProducer("csy").start();// 使用kafka集群中创建好的主题 test
    }  

}
