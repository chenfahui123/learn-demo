/**
 * Copyright 2014-2016 www.fangdd.com All Rights Reserved.
 * Author: Chen Shunyang <chenshunyang@fangdd.com>
 * Date:   2015年9月30日
 */
package com.yangyang.corejava.test;

import junit.framework.TestCase;
import org.junit.Test;

public class BoundedBufferTest extends TestCase{

    private final static int LOCKUP_DETECT_TIMEOUT =10;

    @Test
    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb =new BoundedBuffer<Integer>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb =new BoundedBuffer<Integer>(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }

    public void testTakeBlocksWhenEmpty() {
        final BoundedBuffer<Integer> bb =new BoundedBuffer<Integer>(10);
        Thread takerThread =new Thread(){
            public void run(){
                try {
                    int unused = bb.take();
                    fail();//如果执行到这里，表示出现了错误
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            takerThread.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            takerThread.interrupt();
            takerThread.join(LOCKUP_DETECT_TIMEOUT);
            assertFalse(takerThread.isAlive());
        } catch (InterruptedException e) {
            fail();
        }
    }

}
