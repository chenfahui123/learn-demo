package com.yangyang.corejava.thread;

class MyRunnable1 implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"线程第" + i + "次执行！");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MyThread1 extends Thread {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"线程第" + i + "次执行！");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadPriority {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyRunnable1(),"t1");
        Thread t2 = new Thread(new MyRunnable1(),"t2");
        Thread t3 = new MyThread1();
        t3.setName("t3");
        t1.setPriority(10);
        t2.setPriority(1);
        t3.setPriority(6);
        t1.start();
        t2.start();
        t3.start();
    }
}
