package com.thread.fork;

/**
 * 插入排序 升序
 * @Date 2020/4/24 17:13
 * @name InsertionSort
 */


public class InsertionSort {

    public static int[] sort(int[] array){
        if(array.length==0){
            return array;
        }
        int currentValue;

        for(int i=0;i<array.length-1;i++){
            int preIndex=i;
            currentValue=array[preIndex+1];
            /*在已被排序过数据中倒序寻找合适的位置，如果当前待排序数据比比较的元素要小，
            将比较的元素元素后移一位*/
            while(preIndex>0&&currentValue<array[preIndex]){
                array[preIndex+1]=array[preIndex];
                preIndex--;
            }
            array[preIndex+1]=currentValue;
        }
        return array;
    }
    public static void main(String[] args) {
        System.out.println("============================================");
        long start=System.currentTimeMillis();
        InsertionSort.sort(MakeArray.makeArray());
        System.out.println("插入排序耗时:"+(start-(System.currentTimeMillis()))+"毫秒");
    }
}
