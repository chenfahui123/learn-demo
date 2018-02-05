package com.yangyang.corejava.thread.communicate;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 利用AtomicInteger
 */
public class MethodFive {
    private AtomicInteger threadToGo = new AtomicInteger(1);
    public Runnable newThreadOne() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;
            public void run() {
                for (int i = 0; i < arr.length; i=i+2) {
                    while(threadToGo.get()==2){}
                    Helper.print(arr[i], arr[i + 1]);
                    threadToGo.set(2);
                }
            }
        };
    }
    public Runnable newThreadTwo() {
        final String[] inputArr = Helper.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;
            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    while(threadToGo.get()==1){}
                    Helper.print(arr[i]);
                    threadToGo.set(1);
                }
            }
        };
    }
    public static void main(String args[]) throws InterruptedException {
        MethodFive five = new MethodFive();
        Helper.executor.submit(five.newThreadOne());
        Helper.executor.submit(five.newThreadTwo());
        Helper.executor.shutdown();
    }
}
