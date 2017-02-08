package com.yangyang.corejava.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by chenshunyang on 2017/1/11.
 */
public class StreamTest2 {
    public static void main(String[] args) {
        createStream1();
        createStream2();
        createStream3();
        chuangxing();
        bingxing();
    }

    public static void  createStream1(){
        Stream<String> stream = Stream.of("a", "b", "c", "d", "e", "f", "g");
        stream.forEach(p -> System.out.println(p));
    }

    public static void  createStream2(){
        List<String> strings = Arrays.asList("1", "2", "3");
        Stream<String> stream = strings.stream();
        stream.forEach(p -> System.out.println(p));
    }

    public static void  createStream3(){
        Stream<Double> stream = Stream.generate(()-> {return Math.random();}).limit(5);
        stream.forEach(p -> System.out.println(p));
    }


    public static void chuangxing(){
        long startTime = System.nanoTime();

        Map<String, List<Integer>> numbersPerThread = IntStream.rangeClosed(1, 100000)
                .boxed()
                .collect(Collectors.groupingBy(i -> Thread.currentThread().getName()));
        long runTime = System.nanoTime() - startTime;
        System.out.println("串行运行时间：" + String.valueOf(runTime));

    }

    public static void bingxing(){
        long startTime = System.nanoTime();

        Map<String, List<Integer>> numbersPerThread = IntStream.rangeClosed(1, 100000)
                .parallel()
                .boxed()
                .collect(Collectors.groupingBy(i -> Thread.currentThread().getName()));
        long runTime = System.nanoTime() - startTime;
        System.out.println("并行运行时间：" + String.valueOf(runTime));

    }
}
