package com.demo;

/**
 * LinkList链表源码实现
 * @Date 2019/9/18 16:02
 */
public class DiyLinkedList<E> {

    Node<E> last;
    Node<E> first;
    //链表长度
    int size=0;
    //修改次数统计
    int modCount=0;
    private static class Node<E>{
        E item;
        Node<E> next;//后一个节点
        Node<E> pre;//前一个节点

        Node(Node<E> pre,E element,Node<E> next){
            this.item=element;
            this.pre=pre;
            this.next=next;
        }
    }

    //尾部追加元素
    void addLast(E e){
        // 把尾节点数据暂存
        final Node<E> i=last;
        final Node<E> newNode=new Node<>(i,e,null);
        last=newNode;
        if(i==null){
            first=newNode;
        }else{
            i.next=newNode;
        }
        size++;
        modCount++;
    }

}
