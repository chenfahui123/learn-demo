package com.yangyang.corejava.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileCopyCompare {

	public  static  void main(String[] args) throws Exception { 
	       String sourcePath = "/Users/chenshunyang/Downloads/pd.dmg"; 
//	       String destPath1 = "/Users/chenshunyang/Documents/work/software/pd.dmg"; 
//	       String destPath2 = "/Users/chenshunyang/Documents/work/software/pd1.dmg"; 
//	       String destPath3 = "/Users/chenshunyang/Documents/work/software/pd2.dmg"; 
	       
	       String destPath1 = "source/pd.dmg"; 
	       String destPath2 = "source/pd1.dmg"; 
	       String destPath3 = "source/pd2.dmg"; 
	       System.out.println("测试开始");
	       long t1 = System.currentTimeMillis(); 
	       traditionalCopy(sourcePath,destPath1); 
	       long t2 = System.currentTimeMillis(); 
	       System.out.println("传统IO方法实现文件拷贝耗时:" + (t2-t1) + "ms"); 
	       nioCopy(sourcePath,destPath2); 
	       long t3 = System.currentTimeMillis(); 
	       System.out.println("利用NIO文件通道方法实现文件拷贝耗时:" + (t3-t2) + "ms"); 
	       nioCopy2(sourcePath,destPath3); 
	       long t4 = System.currentTimeMillis(); 
	       System.out.println("利用NIO文件内存映射及文件通道实现文件拷贝耗时:" + (t4-t3) + "ms"); 
	    } 
	   
	    private  static  void traditionalCopy(String sourcePath, String destPath) throws Exception{ 
	       File source = new File(sourcePath); 
	       File dest = new File(destPath); 
	       if(!dest.exists()) { 
	           dest.createNewFile(); 
	       } 
	       FileInputStream fis = new FileInputStream(source); 
	       FileOutputStream fos = new FileOutputStream(dest); 
	       byte [] buf = new byte [512]; 
	       int len = 0; 
	       while((len = fis.read(buf)) != -1) { 
	           fos.write(buf, 0, len); 
	       } 
	       fis.close(); 
	       fos.close(); 
	   
	    } 
	   
	    
	   
	    private  static  void nioCopy(String sourcePath, String destPath) throws Exception{ 
	       File source = new File(sourcePath); 
	       File dest = new File(destPath); 
	       if(!dest.exists()) { 
	           dest.createNewFile(); 
	       } 
	       FileInputStream fis = new FileInputStream(source); 
	       FileOutputStream fos = new FileOutputStream(dest); 
	       FileChannel sourceCh = fis.getChannel(); 
	       FileChannel destCh = fos.getChannel(); 
	       destCh.transferFrom(sourceCh, 0, sourceCh.size()); 
	       sourceCh.close(); 
	       destCh.close(); 
	       fis.close();
	       fos.close();
	    } 
	    
	    
	    private  static  void nioCopy2(String sourcePath, String destPath) throws Exception { 
		       File source = new File(sourcePath); 
		       File dest = new File(destPath); 
		       if(!dest.exists()) { 
		           dest.createNewFile(); 
		       } 
		       FileInputStream fis = new FileInputStream(source); 
		       FileOutputStream fos = new FileOutputStream(dest); 
		       FileChannel sourceCh = fis.getChannel(); 
		       FileChannel destCh = fos.getChannel(); 
		       MappedByteBuffer mbb = sourceCh.map(FileChannel.MapMode.READ_ONLY, 0, sourceCh.size()); 
		       destCh.write(mbb); 
		       sourceCh.close(); 
		       destCh.close(); 
		       fis.close();
		       fos.close();
	    } 
}

