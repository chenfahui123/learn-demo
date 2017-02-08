package com.yangyang.corejava.thread;


public class ThreadVolatile extends Thread{
  public static volatile int n=0;
  
  public static void add(){
      synchronized (ThreadVolatile.class) {
          n++;
      }
  }
  
  public void run(){
      for (int i = 0; i < 10; i++)
          try {
             add();
             sleep(3); // 为了使运行结果更随机，延迟3毫秒
          } catch (Exception e) {
      }
  }
  
  public static void main(String[] args) throws Exception {

      Thread threads[] = new Thread[100];
      for (int i = 0; i < threads.length; i++){
          // 建立100个线程
          threads[i] = new ThreadVolatile();
      }
      for (int i = 0; i < threads.length; i++){
          // 运行刚才建立的100个线程            
          threads[i].start();
      }
      for (int i = 0; i < threads.length; i++){
          // 100个线程都执行完后继续         
          threads[i].join();
      }
      System.out.println("n=" + ThreadVolatile.n);
  }
}
