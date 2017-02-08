package com.yangyang.corejava.java8;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chenshunyang on 2017/1/11.
 */
public class ExampleEmployee {
    private static List<Employee> employeeList = new ArrayList<>();

    static{
        employeeList.add(new Employee("Matt",5000,"New York"));
        employeeList.add(new Employee("Steve",6000,"London"));
        employeeList.add(new Employee("Carrie",20000,"New York"));
        employeeList.add(new Employee("Peter",7000,"New York"));
        employeeList.add(new Employee("Pat",8000,"London"));
        employeeList.add(new Employee("Tammy",29000,"Shanghai"));
    }


    public static void main(String[] args) {

        employeeList.forEach(System.out::println);
        //anyMatch
        boolean isMatch = employeeList.stream().anyMatch(employee -> employee.getOffice().equals("London"));
        System.out.println(isMatch);

        //返回所有salary大于6000
        isMatch = employeeList.stream().allMatch(employee -> employee.getSalary()>6000);
        System.out.println(isMatch);

        //找出工资最高
        Optional<Employee> highSalarys=  employeeList.stream().max((e1, e2)->Integer.max(e1.getSalary(),e2.getSalary()));
        System.out.println(highSalarys);

        //返回姓名列表
        employeeList.stream().map(employee -> employee.getName()).forEach(System.out::println);

        //List转换成Map
        Map<String,Employee> employeeMap = employeeList.stream().collect(Collectors.toMap(key->key.getName(), value->value));
        employeeMap.forEach((key,value)-> System.out.println(key + "=" + value));

        //List转换为Set
        Set<String> officeSet = employeeList.stream().map(employee -> employee.getOffice()).distinct().collect(Collectors.toSet());
        officeSet.forEach(System.out::println);

        //统计办公室是New York的个数
        long officeCount = employeeList.stream().filter(employee -> employee.getOffice().equals("New York")).count();
        System.out.println(officeCount);

        //查找办公室地点是New York的员工
        Optional<Employee> allMatchedEmployees = employeeList.stream().filter(employee -> employee.getOffice().equals("New York")).findAny();
        System.out.println(allMatchedEmployees);


        //按照工资的降序来列出员工信息
        List<Employee> sortEmployeeList = employeeList.stream().sorted((e1,e2)->Integer.compare(e2.getSalary(),e1.getSalary())).collect(Collectors.toList());
        System.out.println("按照工资的降序来列出员工信息:"+sortEmployeeList);

        //获取工资最高的前2条员工信息
        List<Employee> top2EmployeeList= employeeList.stream()
                .sorted((e1,e2)->Integer.compare(e2.getSalary(),e1.getSalary()))
                .limit(2)
                .collect(Collectors.toList());
        System.out.println(top2EmployeeList);

        //按照名字的升序列出员工信息
        List<Employee> sortEmployeeByName = employeeList.stream().sorted((e1,e2)->e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
        System.out.println("按照名字的升序列出员工信息:" + sortEmployeeByName);

        //获取平均工资
        OptionalDouble averageSalary = employeeList.stream().mapToInt(employee->employee.getSalary()).average();
        System.out.println("平均工资:" + averageSalary);

        //查找New York办公室平均工资
        OptionalDouble averageSalaryByOffice = employeeList.stream()
                .filter(employee -> employee.getOffice().equals("New York"))
                .mapToInt(employee->employee.getSalary())
                .average();
        System.out.println("New York办公室平均工资:" + averageSalaryByOffice);

        //按照办公室分组
        Map<String, List<Employee>> groupMap = employeeList.stream().collect(Collectors.groupingBy(c -> c.getOffice()));
        System.out.println(groupMap);
    }
}
