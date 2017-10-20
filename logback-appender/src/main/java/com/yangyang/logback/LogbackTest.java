package com.yangyang.logback;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenshunyang on 2017/2/7.
 */
public class LogbackTest {
    static Logger logger = LoggerFactory.getLogger(LogbackTest.class.getName());

    LogbackTest() {
        // 读取使用Java属性文件编写的配置文件

        logger.debug( "Read config file." );
        //PropertyConfigurator.configure(Log4jTest.class.getClassLoader().getResource("/Users/chenshunyang/code/study/learn-demo/log4j-appender/src/main/resources/log4j.properties"));
//        PropertyConfigurator.configure(Log4jTest.class.getClassLoader().getResource("log4j.properties"));

        logger.debug("read end");
    }

    public static void printLog() {
        logger.debug("Log4jTest-->>debug");
        logger.info("Log4jTest-->>info");
        logger.warn("Log4jTest-->>warn");
        logger.error("Log4jTest-->>error");
    }

    public static void main(String[] args) {
        System.out.println(1241);
        LogbackTest.printLog();
        new LogbackTest();
    }
}
