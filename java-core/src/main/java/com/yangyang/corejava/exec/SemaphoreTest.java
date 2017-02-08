package com.yangyang.corejava.exec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


public class SemaphoreTest {

    private static final int THREAD_COUNT =10;
        private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
        // 只能5个线程同时访问
        private static Semaphore semp = new Semaphore(5);
        public static void main(String[] args) {
            
            for (int i = 0; i < THREAD_COUNT; i++) {
                final int num=i;
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                       try {
                           // 获取许可
                            semp.acquire();
                            System.out.println("Accessing: " + num);
                            // 访问完后，释放
                            System.out.println(num+"释放前许可数量-----------------" + semp.availablePermits()); 
                            semp.release();
                            //availablePermits()指的是当前信号灯库中有多少个可以被使用
                            System.out.println(num+"释放后许可数量-----------------" + semp.availablePermits()); 
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            threadPool.shutdown();
            
        }

}
