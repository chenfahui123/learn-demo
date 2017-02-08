/**
 * Copyright 2014-2016 www.fangdd.com All Rights Reserved.
 * Author: Chen Shunyang <chenshunyang@fangdd.com>
 * Date:   2015年9月15日
 */
package com.yangyang.corejava.nio;

import java.nio.*;

public class CharBufferViewDemo {

    public static void main(String[] args) {
        ByteBuffer byteBuffer=ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
        //在ByteBuffer基础上创建视图缓冲区
        CharBuffer charBuffer=byteBuffer.asCharBuffer();
        IntBuffer intBuffer=  byteBuffer.asIntBuffer();

        byteBuffer.put(0,(byte)0);
        byteBuffer.put(1,(byte)'H');
        byteBuffer.put(2,(byte)0);
        byteBuffer.put(3,(byte)'i');
        byteBuffer.put(4,(byte)0);
        byteBuffer.put(5,(byte)'!');
        byteBuffer.put(6,(byte)0);

        println(byteBuffer);
        println(charBuffer);
        println(intBuffer);

    }

    private static void println(Buffer buffer){
        System.out.println("pos="+buffer.position()
                +",limit="+buffer.limit()
                +",capacity="+buffer.capacity()
                +": '"+buffer.toString()+"'");
    }

}
