package com.yangyang.corejava.exec;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 随机生成10个字符的字符串
 *2015年8月5日 下午3:28:47
 *chenshunyang
 */
class Task implements Callable<String> {  
   @Override  
   public String call() throws Exception {  
       String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
       Random random = new Random();  
       StringBuffer sb = new StringBuffer();  
       for (int i = 0; i < 10; i++) {  
           int number = random.nextInt(base.length());  
           sb.append(base.charAt(number));  
       }  
       return sb.toString();  
   }  
 
}

class  LongTask implements Callable<String> {
    @Override
    public String call() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        return "OK";
    }
}

public class ExecutorServiceTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);  
        service.submit(new LongTask());  
        service.submit(new LongTask());  
        service.submit(new LongTask());  
        service.submit(new LongTask());  
        service.submit(new LongTask());  
          
        List<Runnable> runnables = service.shutdownNow();  
        System.out.println(runnables.size());//返回的是等待在工作队列中的任务  
          
        while (!service.awaitTermination(1, TimeUnit.MILLISECONDS)) {  
            System.out.println("线程池没有关闭");  
        }  
        System.out.println("线程池已经关闭");  
    }
}
