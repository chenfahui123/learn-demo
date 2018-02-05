package com.yangyang.corejava.thread.communicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MethodSeven {
    private final CyclicBarrier barrier = new CyclicBarrier(2,newBarrierAction());
    private final List<String> list = Collections.synchronizedList(new ArrayList<String>());

    public Runnable newThreadOne() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;
            public void run() {
                for (int i = 0, j=0; i < arr.length; i=i+2,j++) {
                    try {
                        list.add(arr[i]);
                        list.add(arr[i+1]);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        list.add(arr[i]);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
    private Runnable newBarrierAction(){
        return new Runnable() {
            @Override
            public void run() {
                Collections.sort(list);
                list.forEach(c->System.out.print(c));
                list.clear();
            }
        };
    }
    public static void main(String args[]){
        MethodSeven seven = new MethodSeven();
        Helper.executor.submit(seven.newThreadOne());
        Helper.executor.submit(seven.newThreadTwo());
        Helper.executor.shutdown();
    }
}
