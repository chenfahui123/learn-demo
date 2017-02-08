package com.yangyang.corejava.thread;

class MyRunnable2 implements  Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"线程第" + i + "次执行！");
        }
    }
}

class MyThread2 extends Thread {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"线程第" + i + "次执行！");
            Thread.yield();
        }
    }
}

public class ThreadYield {
  public static void main(String[] args) {
      Thread t1 = new MyThread2();
      t1.setName("t1");
      Thread t2 = new Thread(new MyRunnable2(),"t2");
      t2.start();
      t1.start();
}
}
