package com.yangyang.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import java.io.IOException;
import java.net.*;

/**
 * Created by chenshunyang on 2017/2/8.
 */
public class UDPAppender extends AppenderSkeleton {

    static private int bufferSize = 8 * 1024;

    private byte data[];
    private String remoteHost = "localhost";
    private int port = 5000;

    private InetAddress address = null;
    private DatagramSocket dataSocket = null;
    private DatagramPacket dataPacket = null;

    public UDPAppender() {

    }

    public void activateOptions() {
        init();
    }

    private void init() {
        try {
            dataSocket = new DatagramSocket(this.port + 1);
            address = InetAddress.getByName(remoteHost);
        } catch (SocketException e) {
            LogLog.debug(e.getMessage());
        } catch (UnknownHostException e) {
            LogLog.debug(e.getMessage());
        }

        data = new byte[bufferSize];

        if (this.layout == null) {
            LogLog.debug("The layout is not loaded... we set it.");
            String pattern = "%-4r %-5p %d{yyyy-MM-dd HH:mm:ss} %c %m%n";
            this.setLayout(new org.apache.log4j.PatternLayout(pattern));
        }
    }


    @Override
    protected void append(LoggingEvent event) {
        try {
            String msg = "UDP Appender...send data: "
                    + this.getLayout().format(event);
            System.out.println("【"+remoteHost+"："+port+"】"+msg);
            data = msg.getBytes();
            dataPacket = new DatagramPacket(data, data.length, address, port);
            dataSocket.send(dataPacket);
        } catch (SocketException se) {
            se.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    @Override
    public void close() {
        if (closed)
            return;

        if (!dataSocket.isClosed()) {
            dataSocket.close();
        }
        closed = true;
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }



    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
