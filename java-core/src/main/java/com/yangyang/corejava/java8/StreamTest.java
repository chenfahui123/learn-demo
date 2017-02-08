package com.yangyang.corejava.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by chenshunyang on 2017/1/11.
 */
public class StreamTest {


    static class User {
        private String name;
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public User(String name, String password) {
            this.name = name;
            this.password = password;
        }

        //省略getter and setter
    }


    public static void main(String[] args) {

        List<User> userList = new ArrayList<User>();
        userList.add(new User("name1", "passowrd1"));
        userList.add(new User("name1", "passowrd1"));
        userList.add(new User("name4321", "passowrd4"));
        userList.add(new User("name321", "passowrd3"));
        userList.add(new User("kaishui", "kaishuiPassword"));
        userList.add(new User("name21", "passowrd2"));

        //1. 在jdk7之前的代码中，也许我们经常会这样写
        List<User> otherUsers = new ArrayList<>();
        for (User u : userList) {
            //获取含有 "name"的User
            if (null != u.getName() && u.getName().indexOf("name") >= 0) {
                otherUsers.add(u);
            }
        }
        //按照名字长度排序
        Collections.sort(otherUsers, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().length() - u2.getName().length();
            }
        });
        //打印
        for (User u : otherUsers) {
            System.out.println(u.getName());
        }
        System.out.println("-------我是一条分割线1---------");
        System.out.println("-------jdk8 示例---------");


      List<String> nameList = userList.stream() //在集合collection中新建一个stream的管道，有点类似Linux命令中 grep or awk的用法。
              .filter(u->u.getName()!= null && u.getName().indexOf("name")>=0)// //过滤结果
              .sorted((u1,u2)->u1.getName().length()-u2.getName().length())//排序
              .map(u->u.getName())                                       //提取
              .distinct()                                            //去重
              .collect(Collectors.toList());                   //把上述步骤后的结果转换成list
      //打印
     // nameList.forEach(name-> System.out.println(name));
      nameList.forEach(System.out::println);


    }
}
