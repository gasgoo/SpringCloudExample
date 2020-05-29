package com.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.common.model.Employee;
import com.common.utils.DateTimeUtils;
import com.common.utils.JSONUtil;
import com.google.common.collect.Lists;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream流操作  jdk1.8的新特性：lambda表达式 函数式编程  函数式接口@FunctionalInterface  方法或构造函数引用 ::
 * Date API更新 如 Instant LocalDate
 * @Date 2019/8/12 19:26
 */
public class ListStreamDemo {

    public static void main(String[] args) {

        ListStream();

        //ListsTools();
        //testPredicate();
        //testLamda();
        testListRemove();
    }

    private static void ListsTools() {
        List<String> list = new ArrayList<String>(){{
            add("10");
            add("20");
            add("30");
            add("40");
            add("50");
            add("640");
            add("710");
            add(null);
        }};
        Stream<String> parallel = list.parallelStream().parallel();
        System.out.println("===="+ JSON.toJSONString(list));
        list= Lists.reverse(list);
        System.out.println("====after"+ JSON.toJSONString(list));
        List<List<String>> partition = Lists.partition(list, 3);
        System.out.println("===group"+JSON.toJSONString(partition));
    }

    private static void ListStream() {
        Employee e1 = new Employee(7369L, "SMITH", 800, 20);
        Employee e2 = new Employee(7499L, "ALLEN", 1600, 30);
        Employee e3 = new Employee(7521L, "WARD", 1250, 30);
        Employee e4 = new Employee(7782L, "CLARK", 2450, 10);
        Employee e5 = new Employee(7876L, "ADAMS", 1100, 20);
        Employee e6 = new Employee(78776L, "ADAMS", 11000, 20);

        List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);
        String json=JSON.toJSONString(e1);
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(json);

        System.out.println("============================"+jsonArray.toString());

        //遍历
        employees.stream().forEach(System.out::println);
        //map方法用于根据自定义的规则对stream流中的数据做一对一的映射
        List<String> collect = employees.stream().map(employee -> employee.getName()).collect(Collectors.toList());
        System.out.println("====map");
        collect.stream().forEach(System.out::println);
        //mapToInt/mapToLong/mapToDouble方法  统计
        int sum = employees.stream().mapToInt(em -> em.getSalary()).sum();
        System.out.println("总薪水:"+sum);
        //filter方法用于根据设置的条件对stream流中的数据做过滤操作
        List<Employee> collect1 = employees.stream().filter(employee -> employee.getSalary() > 1500).collect(Collectors.toList());
        collect1.stream().forEach(System.out::println);
        //sorted方法用于对流中的元素进行排序
        List<Employee> sortList = employees.stream().sorted(Comparator.comparing(Employee::getSalary)).collect(Collectors.toList());
        System.out.println("=====排序");
        sortList.stream().forEach(System.out::println);
        //方法串联
        List<String> names = employees.stream().filter(employee -> !employee.getDeptno().equals(20)).sorted(Comparator.comparing(Employee::getSalary).reversed()).map(employee -> employee.getName()).collect(Collectors.toList());
        System.out.println("-----------"+names.size());

        Map<String, List<Employee>> collect2 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getName));
        System.out.println("group by"+JSON.toJSON(collect2));
        Map<Long, Employee> employeeMap = employees.stream().collect(Collectors.toMap(Employee::getEmpno, Function.identity()));
        System.out.println("+++toMap:  "+JSON.toJSONString(employeeMap));
        //reduce 叠加计算
        Optional<Integer> reduce = employees.stream().map(Employee::getSalary).reduce((em1, em2) -> em1 + em2);
        reduce.orElse(0);
        System.out.println("reduce>>>>>>"+reduce.get());
        // 根据name去重
        List<Employee> unique = employees.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Employee::getName))), ArrayList::new)
        );
        System.out.println(unique.size()+"去重复:"+JSON.toJSONString(unique));
    }

    private static void testPredicate(){
        //测试表达式 大于7
        Predicate<Integer> predicate =x ->x >7;
        System.out.println(predicate.test(7));
        //大于7并且是偶数的
        predicate=predicate.and(x -> x%2 ==0);
        System.out.println(predicate.test(10));
    }

    private static void testLamda(){
        List<String> list =Arrays.asList("aaa","ddd","dss","nihao");
        Collections.sort(list,(a,b)-> a.compareTo(b));
        for(String s:list){
            System.out.println("list sort:"+s);
        }

        Collections.sort(list,(Comparator<? super String>) (String m,String n)->{
            if(m.equals("nihao")){
                System.out.println(">>>ok");
            }
            return n.compareTo(m);
        });
        list.stream().forEach(System.out::println);

        System.out.println(new Random().nextInt(2));
    }

    private static void testListRemove(){
        List<String> list = new ArrayList<String>(){{
            add("10");
            add("20");
            add("30");
            add("40");
            add("50");
            add("640");
            add("710");
        }};
        for(int i=0;i<=list.size();i++){
            list.remove(i);
        }
        System.out.println("removed size: "+list.size());

        for(int i=list.size()-1;i>=0;i--){
            list.remove(i);
        }
        System.out.println("removed size: "+list.size());
    }
}
