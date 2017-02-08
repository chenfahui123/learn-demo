package com.yangyang.corejava.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1",10000);
		OutputStream out = socket.getOutputStream();
		out.write("hello TCP".getBytes());		
		
		
		InputStream is =socket.getInputStream();
		byte[] bytes=new byte[10];
		int len = is.read(bytes);
		System.out.println("服务器说："+new String(bytes,0,len));
		socket.close();
	}
}
