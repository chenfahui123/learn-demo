package com.yangyang.corejava.delayQueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by chenshunyang on 2017/4/12.
 */
public class Teacher  implements Runnable{
    private DelayQueue<Student> students;

    public Teacher(DelayQueue<Student> students,ExecutorService exec) {
        super();
        this.students = students;
    }

    @Override
    public void run() {
        try {
            System.out.println("考试开始……");
            while (!Thread.interrupted()) {
                students.take().run();
            }
            System.out.println("考试结束……");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
