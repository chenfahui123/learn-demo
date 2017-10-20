package com.yangyang.logback;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.io.IOException;

/**
 * Created by chenshunyang on 2017/2/27.
 */
public class CountingConsoleAppender extends AppenderBase<ILoggingEvent> {

    static int DEFAULT_LIMIT = 10;
    int counter = 0;
    int limit = DEFAULT_LIMIT;

    PatternLayoutEncoder encoder;

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public void start() {
        if (this.encoder == null) {
            addError("No encoder set for the appender named ["+ name +"].");
            return;
        }

        try {
            encoder.init(System.out);
        } catch (IOException e) {
        }
        super.start();
    }

    public void append(ILoggingEvent event) {
        if (counter >= limit) {
            return;
        }
        String msg = event.getFormattedMessage();
        System.out.println("msg:"+msg+",count:"+counter+",limit:"+limit);
        // output the events as formatted by our layout
        try {
            this.encoder.doEncode(event);
        } catch (IOException e) {
        }

        // prepare for next event
        counter++;
    }

    public PatternLayoutEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }
}
