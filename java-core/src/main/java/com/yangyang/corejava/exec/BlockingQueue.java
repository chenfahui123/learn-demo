package com.yangyang.corejava.exec;

import java.util.LinkedList;
import java.util.List;

/**
 * 阻塞队列的一个简单实现
 *2015年8月6日 下午8:24:00
 *chenshunyang
 */
public class BlockingQueue {

    private List queue = new LinkedList();
    private int  limit = 10;

    public BlockingQueue(int limit){
        this.limit = limit;
   }
    /**
     * 入队
     * @param item
     * @throws InterruptedException
     * 2015年8月6日 下午8:24:12
     * chenshunyang
     */
    public synchronized void enqueue(Object item)  throws InterruptedException{
        while(this.queue.size() == this.limit){
            wait();
        }
        if(this.queue.size() == 0) {
           notifyAll();
        }
        queue.add(item);
    }
    /**
     * 出对
     * @return
     * @throws InterruptedException
     * 2015年8月6日 下午8:24:22
     * chenshunyang
     */
    public synchronized Object dequeue() throws InterruptedException{
        while(this.queue.size() == 0){
            wait();
        }
        if(this.queue.size() == this.limit){
            notifyAll();
        }
        return this.queue.remove(0);
    }
}
