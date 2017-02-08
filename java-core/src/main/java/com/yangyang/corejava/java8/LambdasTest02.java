package com.yangyang.corejava.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by chenshunyang on 2017/1/11.
 */
public class LambdasTest02 {

    static class NameComparator implements Comparator<String> {
        @Override
        public int compare(String first, String second) {
            return first.compareTo(second);
        }
    }

    public static void main(String[] args) {
        List<String> names = Arrays.asList("palmg", "kai", "shui");
        System.out.println("1.方法一： jdk8之前，按照名称排序");
        Collections.sort(names, new NameComparator());
        for (String name : names) { //打印
            System.out.println(name);
        }

        System.out.println("2.方法二： jdk8之前，按照名称排序");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String first, String second) {
                return first.compareTo(second);
            }
        });
        for (String name : names) { //打印
            System.out.println(name);
        }

        System.out.println("---------我只是一条分割线-------------");
        System.out.println("3. jdk8 lamdas");
        Collections.sort(names, (first, second) -> first.compareTo(second));
        names.forEach(System.out::println);
    }
}

