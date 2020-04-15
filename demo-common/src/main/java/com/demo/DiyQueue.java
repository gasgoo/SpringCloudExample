package com.demo;

/**
 * 自定义队列 泛型 队列中可以放任何对象
 * @Date 2019/11/28 10:37
 * @name DiyQueue
 */
public interface DiyQueue<T> {

    /**
     * @Description 放数据
     * @Date 2019/11/28 10:38
     * @Param [item]
     * @return boolean
     **/
    boolean put(T item);

    /**
     * @Description 取数据
     * @Date 2019/11/28 10:38
     **/
    T take();

    //队列中的元素基本结构
    class Node<T>{
        //数据本身
        T item;
        //下一个元素
        Node<T> next;

        public Node(T item){
            this.item=item;
        }
    }
    //队列的大小
    int getSize();


}
