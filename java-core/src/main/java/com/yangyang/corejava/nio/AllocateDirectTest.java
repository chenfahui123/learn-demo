package com.yangyang.corejava.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class AllocateDirectTest {

    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream("d:\\a.exe");
        FileOutputStream fos=new FileOutputStream("d:\\b.exe");

        long l1=System.currentTimeMillis();
        FileChannel fic=fis.getChannel();
        FileChannel foc=fos.getChannel();

        ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
        l1=System.currentTimeMillis();
        while(true){
            buffer.clear();
            int r=fic.read(buffer);
            if(r==-1){//到达文件末尾
                break;
            }
            buffer.flip();
            foc.write(buffer);//它们的作用是让foc写入pos - limit之间的数据
        }
        fic.close();
        foc.close();
        fis.close();
        fos.close();
        System.out.println("NIO complete:"+(System.currentTimeMillis()-l1));
    }
}
