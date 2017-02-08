package com.yangyang.corejava.exec;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ExchangerTest {
     public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(); 
        final Exchanger<String> exchanger = new Exchanger<String>(); 
        exec.execute(new Runnable(){ 
            public void run() { 
                try {                
                    String data1 = "csy"; 
                    System.out.println("线程" + Thread.currentThread().getName() + "正在把数据" + data1 +"换出去"); 
                    Thread.sleep((long)(Math.random()*10000)); //休息10秒
                    String data2= (String)exchanger.exchange(data1); 
                    System.out.println("线程" + Thread.currentThread().getName() +"换回的数据为" + data2); 
                }catch(Exception e){     
                } 
            }    
        }); 
        exec.execute(new Runnable(){ 
            public void run() { 
                try {                
                    String data1 = "yhy"; 
                    System.out.println("线程" + Thread.currentThread().getName() + "正在把数据" + data1 +"换出去"); 
                    Thread.sleep((long)(Math.random()*5000));                   
                    String data2 = (String)exchanger.exchange(data1); 
                    System.out.println("线程" + Thread.currentThread().getName() + "换回的数据为" + data2); 
                }catch(Exception e){     
                   
                }                
            }    
        });      
         exec.shutdown();
    }
}
