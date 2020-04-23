package com.thread.fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 用 fork-join框架计算40000000 累加计算
 *
 * @Date 2020/4/23 19:02
 * @name SumArray
 */


public class SumArrayTask extends RecursiveTask<Integer> {


    private final static int THRESHOLD = MakeArray.ARRAY_LENGTH / 10;
    private int[] src;
    private int fromIndex;
    private int toIndex;
    public SumArrayTask(int[] src, int fromIndex, int toIndex) {
        this.src = src;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    protected Integer compute() {
        /*任务的大小是否合适*/
        if (toIndex - fromIndex < THRESHOLD){
//                System.out.println(" from index = "+fromIndex
//                        +" toIndex="+toIndex);
            int count = 0;
            for(int i= fromIndex;i<=toIndex;i++){
                count = count + src[i];
            }
            return count;
        }else{
            //fromIndex....mid.....toIndex
            int mid = (fromIndex+toIndex)/2;
            SumArrayTask left = new SumArrayTask(src,fromIndex,mid);
            SumArrayTask right = new SumArrayTask(src,mid+1,toIndex);
            invokeAll(left,right);
            return left.join()+right.join();
        }
    }

    public static void main(String[] args) {
        int[] src = MakeArray.makeArray();
        ForkJoinPool pool=new ForkJoinPool();
        SumArrayTask task=new SumArrayTask(src,0,src.length-1);
        long start=System.currentTimeMillis();
        pool.invoke(task);
        System.out.println("The count is "+task.join()
                +" spend time:"+(System.currentTimeMillis()-start)+"ms");
    }
}
