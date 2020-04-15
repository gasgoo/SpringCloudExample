package com.server.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.server.domain.UserBean;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream接口 api使用
 * @Date 2019/5/13 14:48
 */

public class StreamTest {


    @Test
    public void listInit(){
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        Stream<String> stream = list.stream();
    }

    @Test
    public void streamOfInit(){
        // 第一种
        Stream<String> stream = Stream.of("a", "b", "c", "d");

        // 第二种
        String [] strings = {"a","b","c"};
        Stream<String> stream1 = Stream.of(strings);
        System.out.println(stream1.count());
    }

    @Test
    public void filter() {
        String[] strings = {"apple", "banana", "cat", "dog"};
        List<String> list = Stream.of(strings).filter(e -> !StringUtils.equals(e, "dog")).collect(Collectors.toList());
        System.out.println("过滤："+JSONUtils.toJSONString(list));
        Integer[] nums={2,4,6,7,8,23,55,133,567};
        List<Integer> list1=Stream.of(nums).filter(e -> e<=100).collect(Collectors.toList());
        System.out.println(JSONUtils.toJSONString(list1));
    }
    //把list拼接成需要的map
    @Test
    public void testCollectToMap() {
        List<UserBean> list = new ArrayList<>();
        list.add(new UserBean("13684889501", "happyjava1",22));
        list.add(new UserBean("13684889502", "happyjava2",33));
        Map<String, UserBean> personMap = list.stream().collect(Collectors.toMap(UserBean::getMobile, e -> e));
        System.out.println(JSON.toJSONString(personMap));
        Map<String, String> nameMap = list.stream().collect(Collectors.toMap(UserBean::getMobile, UserBean::getPassword));
        System.out.println(JSON.toJSONString(nameMap));

    }

    @Test
    public void sort() {
        // 自然顺序排序 基本数据类型 字符串
        List<String> collect = Stream.of("1", "5", "2", "9", "3", "4").sorted().collect(Collectors.toList());
        List<String> collect1 = Stream.of("Z","A", "BB", "C", "D", "T", "Q").sorted().collect(Collectors.toList());

        System.out.println(JSON.toJSONString(collect));
        System.out.println(JSON.toJSONString(collect1));

        List<UserBean> list = new ArrayList<>();
        list.add(new UserBean("13684889501", "happyjava1",44));
        list.add(new UserBean("13684889502", "happyjava2",55));
        // 自然顺序排序
        List<UserBean> personList = list.stream().sorted((o1, o2) -> {
            if(o1.getAge()> o2.getAge()){
                return 1;
            } else if(o1.getId().equals(o2.getId())){
                return 0;
            } else {
                return -1;
            }
        }).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(personList));

        //以某个字段排序
        List<UserBean> newList=list.stream().sorted(Comparator.comparing(e ->Long.parseLong(e.getMobile()))).collect(Collectors.toList());
        System.out.println("===根据手机号排序:"+JSON.toJSONString(newList));
    }

    //跨过几行记录，取几行记录的意思。
    @Test
    public void skipAndLimit(){
        List<String> list = Stream.of("1", "5", "2", "9", "3", "4").skip(2).limit(3).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));
    }
    @Test
    public void test(){
        Set<String> sets=new HashSet<>();
        sets.add("1");
        sets.add("1");
        sets.add("2");
        sets.add("3");
        sets.add("5");
        sets.add("2");
        sets.add("2");
        sets.add("4");
        for(String id:sets){
            System.out.println(id);
        }
        System.out.println("size: "+sets.size());

    }
}
