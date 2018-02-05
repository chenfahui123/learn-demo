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
        System.out.println("employeeList的结果为：");
        employeeList.forEach(System.out::println);
        //anyMatch
        boolean isMatch = employeeList.stream().anyMatch(employee -> employee.getOffice().equals("London"));
        System.out.println("isMatch："+isMatch);

        //返回所有salary大于6000
        isMatch = employeeList.stream().allMatch(employee -> employee.getSalary()>6000);
        System.out.println(isMatch);

        //找出工资最高
        Optional<Employee> highSalarys=  employeeList.stream().max((e1, e2)->Integer.max(e1.getSalary(),e2.getSalary()));
        System.out.println("highSalarys："+highSalarys);

        //返回姓名列表
        employeeList.stream().map(employee -> employee.getName()).forEach(System.out::println);

        //List转换成Map
        Map<String,Employee> employeeMap = employeeList.stream().collect(Collectors.toMap(key->key.getName(), value->value));
        System.out.println("employeeMap的结果为：");
        employeeMap.forEach((key,value)-> System.out.println(key + "=" + value));

       //List转换成Map，解决重复key的问题，通过增加第三个参数解决
        Map<String,String> duplicatedEmployeeMap = employeeList.stream()
                .collect(Collectors.toMap(Employee::getOffice,Employee::getName,(oldValue,newValue)->oldValue));
        duplicatedEmployeeMap.forEach((key,value)-> System.out.println("有重复key的情况："+key + "=" + value));

        //List转换为Set
        Set<String> officeSet = employeeList.stream().map(employee -> employee.getOffice()).distinct().collect(Collectors.toSet());
        officeSet.forEach(System.out::println);

        //统计办公室是New York的个数
        long officeCount = employeeList.stream().filter(employee -> employee.getOffice().equals("New York")).count();
        System.out.println(officeCount);

        //查找办公室地点是New York的员工
        Optional<Employee> allMatchedEmployees = employeeList.stream().filter(employee -> employee.getOffice().equals("New York")).findAny();
        System.out.println(allMatchedEmployees);

        //查找一个名字为Tammy的员工
        Employee anyMatchedEmployees = employeeList.stream().filter(employee -> employee.getName().equals("Tammy")).findAny().orElse(null);
        System.out.println("anyMatchedEmployees："+anyMatchedEmployees);

        //查找一个名字为Tammy的员工所在的办公室地点
        String office = employeeList.stream().
                filter(employee -> employee.getName().equals("Tammy"))
                .map(Employee::getOffice)
                .findAny()
                .orElse(null);
        System.out.println("名字为Tammy的员工所在的办公室地点："+office);


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

        //按照办公室分组 //Group by
        Map<String, List<Employee>> officeGroupMap = employeeList.stream().collect(Collectors.groupingBy(c -> c.getOffice()));
        System.out.println("各办公室的职员："+officeGroupMap);


        //统计各办公室的职员姓名 group by + mapping to Set
        Map<String, Set<String>> officeGroupName = employeeList.stream().collect(
                        Collectors.groupingBy(Employee::getOffice,
                                Collectors.mapping(Employee::getName, Collectors.toSet()))
        );
        System.out.println("各办公室的职员人名："+officeGroupName);

        //统计各办公室的职员人数 //Group by + Count
        Map<String,Long> officeGroupCount = employeeList.stream()
                .collect(Collectors.groupingBy(c->c.getOffice(),Collectors.counting()));
        System.out.println("各办公室的职员人数："+officeGroupCount);


        //统计各办公室的职员的工资总和 //Group by + Sum
        Map<String,Integer> officeSalaryGroupCount = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getOffice,Collectors.summingInt(Employee::getSalary)));
        System.out.println("各办公室的职员工资总和为："+officeSalaryGroupCount);


        //按照办公室的职员人数的多少排序
        Map<String, Long> finalMap = new LinkedHashMap<>();
        employeeList.stream()
                .collect(Collectors.groupingBy(c->c.getOffice(),Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        System.out.println("按照办公室的职员人数的多少排序"+finalMap);

        //按照工资倒序
        Map reversedSalaryMap = employeeList.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .collect(Collectors.toMap(
                                Employee::getName, Employee::getSalary,  // key = name, value = office
                                (oldValue, newValue) -> oldValue,   // if same key, take the old key
                                LinkedHashMap::new));                // returns a LinkedHashMap, keep order
        System.out.println("按工资倒序结果为："+reversedSalaryMap);

        //按照姓名字母顺序排序
        Map result = employeeMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue,newValue) -> oldValue,
                                LinkedHashMap::new));
        System.out.println("按照姓名字母顺序排序为："+result);
    }
}
