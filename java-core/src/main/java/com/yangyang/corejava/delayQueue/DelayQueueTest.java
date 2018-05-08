package com.yangyang.corejava.delayQueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;

/**
 * 参考文章http://www.cnblogs.com/haoxinyue/p/6663720.html
 * 下面定义了三个延迟任务，分别是10秒，5秒和15秒。依次入队列，期望5秒钟后，5秒的消息先被获取到，然后每个5秒钟，依次获取到10秒数据和15秒的那个数据。
 * @author chenshunyang
 * @create 2018-05-08 16:06
 **/
public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //定义延迟队列
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<DelayedTask>();

        //定义三个延迟任务
        DelayedTask task1 = new DelayedTask(1,10);
        DelayedTask task2 = new DelayedTask(2,5);
        DelayedTask task3 = new DelayedTask(3,15);

        delayQueue.add(task1);
        delayQueue.add(task2);
        delayQueue.add(task3);

        System.out.println(sdf.format(new Date()) + " start");

        while (delayQueue.size() != 0) {

            //如果没到时间，该方法会返回
            DelayedTask task = delayQueue.poll();

            if (task != null) {
                Date now = new Date();
                System.out.println("task"+task.getIndex()+"的执行实际time:"+sdf.format(now));
            }

            Thread.sleep(1000);
        }
    }

}
