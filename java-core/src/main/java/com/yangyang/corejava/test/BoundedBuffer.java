/**
 * Copyright 2014-2016 www.fangdd.com All Rights Reserved.
 * Author: Chen Shunyang <chenshunyang@fangdd.com>
 * Date:   2015年9月30日
 */
package com.yangyang.corejava.test;

import java.util.concurrent.Semaphore;

/**
 * 基于信号量的有界缓存
 */
public class BoundedBuffer<E> {

    private final Semaphore availableItems;//表示可以从缓存中删除的 元素个数，初始值为0，因为缓存的初始状态为空

    private final Semaphore availableSpaces;//表示可以插入到缓存的元素个数，初始值为缓存的大小

    private final E[] items;

    private int putPos =0 ;//put元素的偏移量，默认为0

    private int takePos =0;//take元素的偏移量，默认为0

    public BoundedBuffer(int capacity){
        availableItems =new Semaphore(0);//可以从缓存中删除的 元素个数，初始值为0
        availableSpaces =new Semaphore(capacity);//可以插入到缓存的元素个数，初始值为缓存的大小
        items =(E[])new Object[capacity];
    }

    public boolean isEmpty(){
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull(){
        return availableSpaces.availablePermits() == 0;
    }

    public synchronized void put(E x) throws InterruptedException{
      //从availableSpaces获得一个许可，如果缓存没有满，那么请求会成功，否则请求会被阻塞直到缓存没有满
        availableSpaces.acquire();
        doInsert(x);
       //返回一个许可给availableItems，表示可以删除一个元素
        availableItems.release();
    }


    public synchronized E take() throws InterruptedException{
        //从availableItems获得一个许可，如果缓存不为空，那么请求会成功，否则请求会被阻塞直到缓存不为空
        availableItems.acquire();
        //删除缓存中的下一个元素
        E item =doExtract();
        //返回一个许可给availableSpaces，表示可以插入一个元素
        availableSpaces.release();
        return item;
    }

    private void doInsert(E x) {
       int i = putPos;
       items[i] = x;
       putPos = (++i == items.length) ? 0 :i ;
    }

    private E doExtract() {
        int i = takePos;
        E x =items[i];
        items[i] = null;
        takePos = (++i == items.length) ?0 :1;
        return x;
    }

}
