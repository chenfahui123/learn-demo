package com.yangyang.corejava.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(10000);
		
		Socket socket =ss.accept();
		InputStream is =socket.getInputStream();
		byte[] bytes = new byte[10];
		int len = is.read(bytes);
		System.out.println(new String(bytes,0,len));
		
		OutputStream os =socket.getOutputStream();
		os.write("我收到了".getBytes());
		socket.close();
		ss.close();
  		
	}

}
