package com.yangyang.corejava.exec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


class Product {
    private int id;
    public Product(int id) {
        this.id = id;
    }
    public String toString() {// 重写toString方法
        return "产品：" + this.id;
    }
}

/**
 * 仓库，用来存放产品
 *2015年8月7日 上午11:24:19
 *chenshunyang
 */
  class Storage {
     LinkedBlockingQueue<Product> queues = new LinkedBlockingQueue<Product>(10);
     //生产
     public void put(Product p) throws InterruptedException {
         queues.put(p);
     }
    //消费
     public Product take() throws InterruptedException {
         return queues.take();
     }
 }

/**
* 消费者
*2015年8月7日 上午11:23:55
*chenshunyang
*/
class Consumer implements Runnable {
    private String name;
    private Storage s = null;

    public Consumer(String name,Storage s){
        this.name=name;
        this.s=s;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(name + "准备消费产品.");
                Product product=s.take();
                System.out.println(name + "已消费(" + product.toString() + ").");
                System.out.println("===============");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
* 生产者
*2015年8月7日 上午11:24:08
*chenshunyang
*/
class Producer implements Runnable {
    private String name;
    private Storage s = null;

    public Producer(String name, Storage s) {
        this.name = name;
        this.s = s;
    }

    public void run() {
        try {
            while (true) {
                Product product = new Product((int) (Math.random() * 10000)); // 产生0~9999随机整数
                System.out.println(name + "准备生产(" + product.toString() + ").");
                s.put(product);
                System.out.println(name + "已生产(" + product.toString() + ").");
                System.out.println("===============");
                Thread.sleep(500);
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

    }
}


public class ProducerConsumerTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Storage s=new Storage();
        service.submit(new Producer("张三", s));
        service.submit(new Producer("李四", s));
        service.submit(new Consumer("王五", s));
        service.submit(new Consumer("老刘", s));
        service.submit(new Consumer("老林", s));
        
        service.shutdown();
    }
  
}
