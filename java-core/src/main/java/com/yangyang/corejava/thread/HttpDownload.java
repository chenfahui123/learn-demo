package com.yangyang.corejava.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenshunyang on 2017/3/7.
 */
public class HttpDownload {
    private static final Log log = LogFactory.getLog(HttpDownload.class);

    // 分段下载的线程个数

    int threadNum = 3;
    URL url = null;
    long threadLength = 0;
    public boolean statusError = false;

    int errprCode;
    String errorMessage="";
    String fileName = "";
    String fileDir = "";
    ChildThread[] childThreads = new ChildThread[threadNum];
    private final static long sleepSeconds = 5;

    public String download(String urlStr, String dir, String fileName) {
        CountDownLatch latch = new CountDownLatch(threadNum);
        long[] startPos = new long[threadNum];
        long endPos;
        try {
            this.fileName = fileName;
            this.fileDir = dir;
//            this.url = new URL(URLDecoder.decode(urlStr, "utf-8"));

            this.url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            setHeader(con);
            // 得到content的长度

            long contentLength = con.getContentLengthLong();
            // 把context分为threadNum段的话，每段的长度。

            this.threadLength = contentLength / threadNum;
            // 第一步，分析已下载的临时文件，设置断点，如果是新的下载任务，则建立目标文件。在第4点中说明。

            Map resultMap = setThreadBreakpoint(contentLength, startPos);
            if (resultMap.get("flag").equals("true")) {
                return fileDir + this.fileName;
            } else {
                startPos = (long[]) resultMap.get("startPos");
            }
            //第二步，分多个线程下载文件

            ExecutorService exec = Executors.newCachedThreadPool();
            for (int i = 0; i < threadNum; i++) {
                // 创建子线程来负责下载数据，每段数据的起始位置为(threadLength * i + 已下载长度)

                startPos[i] += threadLength * i;
                /*设置子线程的终止位置，非最后一个线程即为(threadLength * (i + 1) - 1)

                最后一个线程的终止位置即为下载内容的长度*/
                if (i == threadNum - 1) {
                    endPos = contentLength;
                } else {
                    endPos = threadLength * (i + 1) - 1;
                }
                // 开启子线程，并执行。

                ChildThread thread = new ChildThread(this, latch, i, startPos[i], endPos, contentLength, this.fileDir + this.fileName);
                childThreads[i] = thread;
                exec.execute(thread);
            }
            // 等待CountdownLatch信号为0，表示所有子线程都结束。

            latch.await();
            exec.shutdown();    //TODO 线程池不需要每次都关闭

            // 第三步，把分段下载下来的临时文件中的内容写入目标文件中。在第3点中说明。

            tempFileToTargetFile(childThreads);
        } catch (Exception e) {
            log.error("" + e.getMessage(), e);
        }
        return this.fileDir + this.fileName;
    }

    /**

     *

     * @param childThreads

     */
    private void tempFileToTargetFile(ChildThread[] childThreads) {
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(
                    new FileOutputStream(fileDir + fileName));
            // 遍历所有子线程创建的临时文件，按顺序把下载内容写入目标文件中

            for (int i = 0; i < threadNum; i++) {
                if (statusError) {
                    for (int k = 0; k < threadNum; k++) {
                        if (childThreads[k].tempFile.length() == 0)
                            childThreads[k].tempFile.delete();
                    }
                    log.info("本次下载任务不成功，请重新设置线程数。errprCode : "+errprCode+" , message : " +errorMessage);
                    break;
                }
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(childThreads[i].tempFile));
                log.info("Now is file " + childThreads[i].id);
                int len;
                long count = 0;
                byte[] b = new byte[1024];
                while ((len = inputStream.read(b)) != -1) {
                    count += len;
                    outputStream.write(b, 0, len);
                    if ((count % 4096) == 0) {
                        outputStream.flush();
                    }
                }
                inputStream.close();
                // 删除临时文件

                if (childThreads[i].status == ChildThread.STATUS_HAS_FINISHED) {
                    childThreads[i].tempFile.delete();
                }
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.error("" + e.getMessage(), e);
        }
    }

    /**

     * 分析已下载的临时文件，设置断点，如果是新的下载任务，则建立目标文件。

     *

     * @param contentLength 源文件大小

     * @param startPos      开始的字节

     * @return map 的key(flag)为true时，证明文件已存在本地且和源文件大小一致，不需要重新下载。  flag=false时，需要获取key(startPos)的值

     */
    private Map setThreadBreakpoint(long contentLength, long[] startPos) {
        Map resultMap = new HashMap();
        File file = new File(fileDir + fileName);
        long localFileSize = file.length();
        if (file.exists()) {
            log.info("file " + fileName + " has exists!");
            // 下载的目标文件已存在，判断目标文件是否完整

            if (localFileSize < contentLength) {
                log.info("Now download continue ... ");
                // 遍历目标文件的所有临时文件，设置断点的位置，即每个临时文件的长度

                File tempFileDir = new File(fileDir);
                File[] files = tempFileDir.listFiles();
                for (int k = 0; k < files.length; k++) {
                    String tempFileName = files[k].getName();
                    // 临时文件的命名方式为：目标文件名+"_"+编号

                    if (tempFileName != null && files[k].length() > 0 && tempFileName.startsWith(fileName + "_")) {
                        int fileLongNum = Integer.parseInt(tempFileName .substring(
                                tempFileName.lastIndexOf("_") + 1, tempFileName.lastIndexOf("_") + 2));
                        // 为每个线程设置已下载的位置

                        startPos[fileLongNum] = files[k].length();
                    }
                }
                resultMap.put("flag", "false");
                resultMap.put("startPos", startPos);
            } else if (contentLength == localFileSize) {
                log.info("源大小和本地文件大小一致，无需重复下载");
                resultMap.put("flag", "true");
            }
        } else {
            // 如果下载的目标文件不存在，则创建新文件

            try {
                file.createNewFile();
                resultMap.put("flag", "false");
                resultMap.put("startPos", startPos);
            } catch (IOException e) {
                log.error("" + e.getMessage(), e);
            }
        }
        return resultMap;
    }

    class ChildThread extends Thread {
        public static final int STATUS_HASNOT_FINISHED = 0;
        public static final int STATUS_HAS_FINISHED = 1;
        public static final int STATUS_HTTPSTATUS_ERROR = 2;

        private HttpDownload task;
        private int id;
        private long startPosition;
        private long endPosition;
        private final CountDownLatch latch;
        // private RandomAccessFile tempFile = null;

        private File tempFile = null;
        //线程状态码

        private int status = ChildThread.STATUS_HASNOT_FINISHED;
        private long contentLength;
        /**

         * @param task     任务

         * @param latch    队列

         * @param id       线程ID

         * @param startPos 开始字节

         * @param endPos   结束字节

         * @param fileUrl  文件路径

         */
        public ChildThread(HttpDownload task, CountDownLatch latch, int id, long startPos, long endPos, long contentLength, String fileUrl) {
            super();
            this.task = task;
            this.id = id;
            this.startPosition = startPos;
            this.endPosition = endPos;
            this.latch = latch;
            this.contentLength = contentLength;
            try {
                tempFile = new File(fileUrl + "_" + id);
            } catch (Exception e) {
                log.error("" + e.getMessage(), e);
            }
        }

        public void run() {
            log.info("Thread " + id + " run ...");
            HttpURLConnection con = null;
            InputStream inputStream = null;
            BufferedOutputStream outputStream = null;
            long count = 0;
            long threadDownloadLength = endPosition - startPosition;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(tempFile.getPath(), true));
            } catch (Exception e) {
                log.error("" + e.getMessage(), e);
            }
            for (int k = 0; k < 10; k++) {//重试10次

                if (k > 0) {
                    log.info("Now thread " + id + "is reconnect, start position is " + startPosition);
                }
                try {
                    //打开URLConnection

                    con = (HttpURLConnection) task.url.openConnection();
                    setHeader(con);
                    if (startPosition < endPosition) {
                        if (endPosition == contentLength) {
                            //设置下载数据的起止区间

                            con.setRequestProperty("Range", "bytes=" + startPosition + "-");
                        } else {
                            //设置下载数据的起止区间

                            con.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
                        }
                        log.info("Thread " + id + " startPosition is " + startPosition + " endPosition is " + endPosition);
                        //判断http status是否为HTTP/1.1 206 Partial Content或者200 OK

                        //如果不是以上两种状态，把status改为STATUS_HTTPSTATUS_ERROR

                        if (con.getResponseCode() != HttpURLConnection.HTTP_OK && con.getResponseCode() != HttpURLConnection.HTTP_PARTIAL) {
                            log.error("Thread " + id + ": code = " + con.getResponseCode() + ", message = " + con.getResponseMessage());
                            this.task.errprCode=con.getResponseCode();
                            this.task.errorMessage=con.getResponseMessage();
                            status = ChildThread.STATUS_HTTPSTATUS_ERROR;
                            this.task.statusError = true;
                            outputStream.close();
                            con.disconnect();
                            log.info("Thread " + id + " finished.");
                            latch.countDown();
                            break;
                        }
                        inputStream = con.getInputStream();
                        int len;
                        byte[] b = new byte[1024];
                        while ((len = inputStream.read(b)) != -1) {
                            outputStream.write(b, 0, len);
                            count += len;
                            startPosition += len;
                            //每读满4096个byte（一个内存页），往磁盘上flush一下

                            if (count % 4096 == 0) {
                                outputStream.flush();
                            }
                        }
                        log.info("count is " + count);
                        if (count >= threadDownloadLength) {
                            status = ChildThread.STATUS_HAS_FINISHED;
                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                        con.disconnect();
                    } else {
                        status = ChildThread.STATUS_HAS_FINISHED;
                    }
                    log.info("Thread " + id + " finished.");
                    latch.countDown();
                    break;
                } catch (IOException e) {
                    try {
                        outputStream.flush();
                        TimeUnit.SECONDS.sleep(sleepSeconds);
                    } catch (Exception e2) {
                        log.error("" + e2.getMessage(), e2);
                    }
                    continue;
                }
            }
        }
    }

    private void setHeader(URLConnection con) {
        con.setAllowUserInteraction(true);
        //设置连接超时时间为10000ms

        con.setConnectTimeout(10000);
        //设置读取数据超时时间为10000ms

        con.setReadTimeout(10000);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092510 Ubuntu/8.04 (hardy) Firefox/3.0.3");
        con.setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
        con.setRequestProperty("Accept-Encoding", "identity");
        con.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        con.setRequestProperty("Keep-Alive", "300");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("If-Modified-Since", "Fri, 02 Jan 2009 17:00:05 GMT");
        con.setRequestProperty("If-None-Match", "\"1261d8-4290-df64d224\"");
        con.setRequestProperty("Cache-Control", "max-age=0");
        con.setRequestProperty("Referer", "http://www.dianping.com");
    }

    public static void main(String[] args) {
        HttpDownload downloadManager = new HttpDownload();
        String urlStr = "http://dldir1.qq.com/qqfile/QQforMac/QQ_V5.4.1.dmg";
        // 从url中获得下载的文件格式与名字

        String fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.lastIndexOf("?") > 0 ? urlStr.lastIndexOf("?") : urlStr.length());
        String dir = "/Users/chenshunyang/code/study/learn-demo/files/";
        String result = downloadManager.download(urlStr, dir, fileName);
        System.out.println(result);
    }
}

