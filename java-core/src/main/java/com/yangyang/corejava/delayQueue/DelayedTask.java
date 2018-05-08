package com.yangyang.corejava.delayQueue;

import java.util.Calendar;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 定义放在延迟队列中的对象，需要实现Delayed接口
 * @author chenshunyang
 * @create 2018-05-08 16:05
 **/
public class DelayedTask implements Delayed{
    private int expireInSecond = 0;
    private int index;

    public DelayedTask(int index,int delaySecond) {
        this.index = index;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, delaySecond);
        expireInSecond = (int) (cal.getTimeInMillis() / 1000);
    }

    public int compareTo(Delayed o) {
        long d = (getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    public long getDelay(TimeUnit unit) {
        Calendar cal = Calendar.getInstance();
        return expireInSecond - (cal.getTimeInMillis() / 1000);
    }

    public int getIndex() {
        return index;
    }
}
