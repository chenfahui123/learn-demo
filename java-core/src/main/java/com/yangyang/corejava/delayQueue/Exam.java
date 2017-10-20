package com.yangyang.corejava.delayQueue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 示例代码：http://blog.csdn.net/u013725455/article/details/53116854
 * Created by chenshunyang on 2017/4/12.
 */
public class Exam {
    public static void main(String[] args) throws ParseException {
        //创建延迟队列
        DelayQueue<Student> delayQueue = new DelayQueue<Student>();
        ExecutorService exec = Executors.newCachedThreadPool();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        //将消息放入队列
        delayQueue.put(new Student("学生1", dateFormat.parse("2016-11-10 16:02:10"), "1"));
        delayQueue.put(new Student("学生2", dateFormat.parse("2016-11-10 16:09:10"), "2"));
        delayQueue.put(new Student("学生3", dateFormat.parse("2016-11-10 16:08:10"), "3"));
        delayQueue.put(new Student("学生4", dateFormat.parse("2016-11-10 16:06:10"), "4"));
        delayQueue.put(new Student("学生5", dateFormat.parse("2016-11-10 16:03:10"), "5"));
        delayQueue.put(new Student("学生6", dateFormat.parse("2016-11-10 16:05:10"), "6"));
        exec.execute(new Teacher(delayQueue, exec));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //将消息从队列中移除
        Student remove = new Student();
        remove.setId("6");
        delayQueue.remove(remove);
        delayQueue.put(new Student("学生7", dateFormat.parse("2016-11-10 16:02:10"), "7"));
//        students.put(new Student("学生8", dateFormat.parse("2016-11-10 16:43:20"), "8"));
//        students.put(new Student("学生9", dateFormat.parse("2016-11-10 16:43:01"), "9"));
//        students.put(new Student("学生10", dateFormat.parse("2016-11-10 16:43:03"), "10"));
//        students.put(new Student("学生11", dateFormat.parse("2016-11-10 16:43:11"), "11"));
//        students.put(new Student("学生12", dateFormat.parse("2016-11-10 16:43:17"), "12"));
    }
}
