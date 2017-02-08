package com.yangyang.corejava.exec;

import java.util.concurrent.*;

/**
 * 组织人员（线程）郊游，约定一个时间地点（路障），人员陆续到达地点，等所有人员全部到达，开始到公园各玩各的，再到约定时间去食堂吃饭，等所有人到齐开饭……
 *2015年8月6日 下午5:45:54
 *chenshunyang
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int  num=Runtime.getRuntime().availableProcessors(); 
        ExecutorService exec=Executors.newFixedThreadPool(num);
        final CyclicBarrier cb=new CyclicBarrier(3,new Runnable() {//约定3个人
            @Override
            public void run() {
                System.out.println("大家都来了，该任务到此结束");
            }
        });
      //产生3个人
        for (int i = 0; i < 3; i++) {
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将到达集合地点1，当前已有" + (cb.getNumberWaiting()+1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));
                        cb.await();
                        TimeUnit.SECONDS.sleep(10);
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将到达集合地点2，当前已有" + (cb.getNumberWaiting()+1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));
                        cb.await();
                         
                        TimeUnit.SECONDS.sleep(10);
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将到达集合地点3，当前已有" + (cb.getNumberWaiting() + 1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));                    
                        cb.await();                    
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.submit(runnable);
        }
        exec.shutdown();
    }
}
