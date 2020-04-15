package com.demo;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数组中两个数字之和 等于某个值的组合
 * @Date 2020/3/9 14:41
 * @name LeetCodeTwoSum
 */


public class LeetCodeTwoSum {


    public static void main(String[] args) {
        int[] arr=new int[]{3,4,6,7,8,2,1,9};
        List<Integer[]> integers = LeetCodeTwoSum.twoSum(arr, 10);
        integers.stream().forEach(System.out::println);
        int[] nums1 = new int[]{1,2,3,0,0,0};
        int[] nums2 = new int[]{2,5,6};
        int[] ints = LeetCodeTwoSum.mergeSortArray(nums1, nums2);
        for(int i=0;i<ints.length;i++){
            System.out.println(ints[i]);
        }
    }

    /**
     * @Description 数组中两个数字之和 等于某个值的组合
     * @Date 2020/3/20 17:26
     **/
    public static  List<Integer[]> twoSum(int[] arr,int target){
        List<Integer[]> list= Lists.newArrayList();
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<arr.length;i++){
            int result =target - arr[i];
            if(map.containsKey(result)){
                list.add(new Integer[]{i,map.get(result)});
            }
            map.put(arr[i],i);
        }
        return list;
    }

    /**
     * @Description 合并两个有序数组
     * @Date 2020/3/20 17:26
     **/
    public static  int[] mergeSortArray(int[] nums1,int[] nums2){
        int n1=nums1.length;
        int n2=nums2.length;
        int[] newArray=new int[n1+n2];
        int i=0,j=0,count=0;
        //当下标位置小于数组长度时，比较两个数组的元素的大小，小的放入新数组
        while(i<n1 && j<n2){
            if(nums1[i]< nums2[j]){
                newArray[count++]=nums1[i++];
            }else{
                newArray[count++]=nums1[j++];
            }
        }
        //当第一个数组遍历完成，直接将第二个数组放入新数组
        if(i >=n1){
            while(j<n2){
                newArray[count++]=nums2[j++];
            }
        }
        if(j<=n2){
            while(i<n1){
                newArray[count++]=nums1[i++];
            }
        }

        return newArray;
    }
}

