package com.yangyang.corejava.delayQueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenshunyang on 2017/4/12.
 */
public class Student implements Runnable,Delayed{
    private String name;    //姓名
    private Date date;   //交卷时间
    private String id; //编号
    public Student() {

    }

    public Student(String name, Date date, String id) {
        super();
        this.name = name;
        this.date=date;
        this.id=id;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(date.getTime()- System.currentTimeMillis(),TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        Student that = (Student) o;
        return date.getTime()> that.getDate().getTime()?1:(date.getTime() < that.getDate().getTime() ? -1 : 0);
    }
    @Override
    public boolean equals(Object obj) {
        Student student=(Student)obj;
        return this.id.equals(student.getId());
    }

    @Override
    public void run() {
        System.out.println(name + " 设置时间=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + ";执行时间="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }


    public static class EndExam extends Student{
        private ExecutorService exec;
        public EndExam(Date date,String id, ExecutorService exec) {
            super(null,date, id);
            this.exec = exec;
        }
        @Override
        public void run() {
            exec.shutdownNow();
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
