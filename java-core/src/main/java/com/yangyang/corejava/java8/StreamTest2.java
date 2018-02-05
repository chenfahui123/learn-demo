package com.yangyang.corejava.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
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
        createStream4();
        chuangxing();
        bingxing();
    }

    /**
     * stream foreach
     */
    public static void  createStream1(){
        Stream<String> stream = Stream.of("a", "b", "c", "d", "e", "f", "g");
        stream.forEach(p -> System.out.println(p));
    }

    /**
     * list to stream
     */
    public static void  createStream2(){
        List<String> strings = Arrays.asList("1", "2", "3");
        Stream<String> stream = strings.stream();
        stream.forEach(p -> System.out.println(p));
    }

    /**
     * array to stream
     * 两种方式Arrays.stream 或者Stream.of
     */
    public static void  createStream3(){
        String[] array = {"a", "b", "c", "d", "e"};
        Stream<String> stream = Arrays.stream(array);
        stream.forEach(System.out::println);
        Stream<String> stream1 = Stream.of(array);
        stream1.forEach(System.out::println);
    }

    public static void  createStream4(){
        Stream<Double> stream = Stream.generate(()-> {return Math.random();}).limit(5);
        stream.forEach(p -> System.out.println(p));
    }

    /**
     * stream使用supperier方式每次获得一个新的流，避免流关闭的异常
     * 参考实例：http://blog.csdn.net/wangmuming/article/details/78447754
     */
    public static void  createStream5(){
        String[] array = {"java", "python", "node", null, "ruby", null, "php"};
        //使用streamSupplier，每一个get() 将返回一个新的 stream.
        Supplier<Stream<String>> streamSupplier = () -> Stream.of(array);
        //get new stream
        streamSupplier.get().collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("-----------执行过滤null逻辑-----------");

        //get another new stream
        List<String> result = streamSupplier.get().filter(x->x!= null).collect(Collectors.toList());
        result.forEach(System.out::println);

        System.out.println("-----------执行过滤null逻辑方式2-----------");

        result = streamSupplier.get().filter(Objects::nonNull).collect(Collectors.toList());
        result.forEach(System.out::println);
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
