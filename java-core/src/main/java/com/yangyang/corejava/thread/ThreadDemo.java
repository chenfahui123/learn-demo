package com.yangyang.corejava.thread;
 
class Bank{
    private int count;
    private Object obj=new Object();
    /**
     * 同步方法块方式同步
     */
    public void addMoney(int money){
        synchronized (obj) {
            count=count+money;
            System.out.println(Thread.currentThread().getName() +"：当前count="+count);
        }
    }
    /**
     * 同步方法块方式同步
     * @param money
     * 2015年7月16日 下午8:13:51
     * chenshunyang
     */
//    public synchronized void addMoney(int money){
//        count=count+money;
//        System.out.println(Thread.currentThread().getName() +"：当前count="+count);
//    }
}

class Customer implements Runnable{
    Bank bank=new Bank();
    @Override
    public void run(){
        for(int i =0; i <3; i++){
           bank.addMoney(100);
        }
    }
}

public class ThreadDemo{
    public static void main(String[] args){
        Customer customer=new Customer();
        Thread ta=new Thread(customer,"threadA");
        Thread tb=new Thread(customer,"threadB");
        ta.start();
        tb.start();
    }
}