package com.yangyang.corejava.exec;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ArrayBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue=new ArrayBlockingQueue(5);
        for (int i = 0; i < 10; i++) {
            // 将指定元素添加到此队列中，如果没有可用空间，将一直等待（如果有必要）。
            queue.put(i);
            System.out.println("向阻塞队列中添加了元素:" + i);
        }
        Object obj=queue.take();
        System.out.println("从阻塞队列中取出了元素:" + obj);
        System.out.println("程序到此运行结束，即将退出----");
    }
}
