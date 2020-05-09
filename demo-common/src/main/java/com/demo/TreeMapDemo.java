package com.demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Date 2019/9/23 15:41
 */
public class TreeMapDemo  {

    class DTO implements  Comparable<DTO>{
        private Integer id;
        private String name;
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DTO(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public DTO(){

        }
        @Override
        public int compareTo(DTO o) {
            return id -o.getId();
        }

    }


    public void testComparable(){
        List<DTO> list= new ArrayList();
        for(int i=5;i>0;i--){
            list.add(new DTO(i,"i"));
        }
        Collections.sort(list);
        System.out.println("===="+ JSON.toJSONString(list));
        System.out.println("<<<<<<<<====>>>>>>");
        // 第二种排序，从大到小排序，利用外部排序器 Comparator 进行排序
        Comparator comparator = (Comparator<DTO>) (o1, o2) -> o2.getId() - o1.getId();
        List<DTO> list2 = new ArrayList<>();
        for (int i = 5; i > 0; i--) {
            list2.add(new DTO(i,"i"));
        }
        Collections.sort(list,comparator);

    }

    public static void main(String[] args) throws InterruptedException {
        TreeMapDemo dto=new TreeMapDemo();
        dto.testComparable();
        HashMap testMap = new HashMap<Integer,Object>(1024);
        System.out.println("init size:"+testMap.size());
        long round1= Math.round(-5.8);
        System.out.println("round1===="+round1+"==");
        int hashNum = Objects.hash("raogugen");
        int a=hashNum%16384;
        int b=16383 & hashNum;
        System.out.println("a="+a+"<<<>>>b="+b);
        BigDecimal d=new BigDecimal(0.8);
        BigDecimal d1=new BigDecimal(0.5);
        if(d.compareTo(d1)>0){
            System.out.println("bigggggg");
        }


        }





}
