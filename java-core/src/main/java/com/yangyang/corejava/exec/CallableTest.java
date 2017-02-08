package com.yangyang.corejava.exec;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class TaskWithResult implements Callable<String>{
    
    private int id;  
    
    public TaskWithResult(int id) {  
        this.id = id;  
    }  

    @Override
    public String call() throws Exception {
        System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());  
        if (new Random().nextBoolean()) {
            throw new Exception("Meet error in task." + Thread.currentThread().getName());  
        }
        // 一个模拟耗时的操作  
        for (int i = 999999999; i > 0; i--){
            ;  
        }
        return "call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();  
    }
    
}

public class CallableTest {

    public static void main(String[] args) {
        ExecutorService exec=Executors.newCachedThreadPool();
        List<Future<String>> resultList=new ArrayList<Future<String>>();
        // 创建10个任务并执行  
        for (int i = 0; i < 10; i++) {
            // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中  
            Future<String> future= exec.submit(new TaskWithResult(i));
            // 将任务执行结果存储到List中  
            resultList.add(future);
        }
        
       for (Future<String> fs : resultList) {
           try {
            // 打印各个线程（任务）执行的结果  
            System.out.println(fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }finally{
                exec.shutdown();
            }
       }
        
    }
}
