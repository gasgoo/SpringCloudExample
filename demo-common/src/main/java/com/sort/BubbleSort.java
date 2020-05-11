package com.sort;

public class BubbleSort {

   private static final  int[] array = new int[]{100,2, 4, 6, 8, 1, 88,19,23};
    /**
     * 冒泡排序  升序
     * 比较两个相邻的元素 时间复杂度O(n^2)
     * @param array
     * @return
     */
    public static int[] Mapsort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 选择排序  找出最小的或最大的元素然后依次和第一个比较  复杂度O(n^2)
     * 降序的例子
     */
    public static int[] selectSort(int[] array){
        if (array.length == 0) {
            return array;
        }
        for(int i=0;i<array.length;i++){
            //存最小数的下标
            int minIndex=i;
            for(int j=i;j<array.length;j++){
                if(array[j]<array[minIndex]){
                    minIndex=j;
                }
            }
            //最小值
            int temp=array[minIndex];
            array[minIndex]=array[i];
            array[i]=temp;

        }
        return array;
    }

    /**
     * 插入排序  对于部分有序的数组排序十分高效  最坏复杂度O(n^2)  最好 O(logn)
     * 对于已排序的数组从后向前扫描找到合适的插入位置
     * 降序
     */
    public static int[] insertSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int curValue;
        for (int i = 0; i < array.length - 1; i++) {
            //已经排序的数据索引
            int preIndex = i;
            curValue = array[preIndex + 1];
            while(preIndex>=0 && curValue<array[preIndex]){
                array[preIndex+1]=array[preIndex];
                preIndex--;
            }
            array[preIndex+1]=curValue;
        }
        return array;
    }

    /**
     * 希尔排序  根据数据长度/2 找出增量，从而划分成几个数组 然后分别采用插入排序 O(logn)
     * 降序
     */
    public static int[] shellSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int length=array.length;
        int gap=length/2;
        //组内带排序的数据
        int curValue;
        while(gap>0){
            for (int i = gap; i < length; i++) {
                //已经排序的数据索引
                int preIndex = i-gap;
                curValue = array[i];
                while(preIndex>=0 && curValue<array[preIndex]){
                    array[preIndex+gap]=array[preIndex];
                    preIndex=preIndex-gap;
                }
                array[preIndex+gap]=curValue;
            }
            gap=gap/2;
        }

        return array;
    }

    public static void main(String[] args) {

        System.out.println("冒泡排序： ");
        BubbleSort.Mapsort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(+array[i]+"  ");
        }
        System.out.print("选择排序： ");
        BubbleSort.selectSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(+array[i]+"  ");
        }
        System.out.print("插入排序： ");
        BubbleSort.insertSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(+array[i]+"  ");
        }
        System.out.println("希尔排序： ");
        BubbleSort.shellSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(+array[i]+"  ");
        }
    }
}
