package com.sort;

import java.util.LinkedList;

/**
 * 快速找出未知节点数的链表中间节点
 * 双指针的思想  第一个节点步长为1，第二个节点为2，当第二个节点next=null 则第一个节点为中间节点。
 *
 * @Date 2020/7/29 10:11
 * @name LinkedNode
 */


public class LinkedNodeSelector {

    public static LinkedList<Integer> init() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(9);
        linkedList.add(99);
        linkedList.add(5);
        linkedList.add(7);
        linkedList.add(8);
        return linkedList;
    }

    public static void locate(LinkedList linkedList) {
        Integer a = null;
        Integer b = null;
        for (int i = 0; i * 2 < linkedList.size(); i++) {
            a = (Integer) linkedList.get(i);
            b = (Integer) linkedList.get(i * 2);
        }
        System.out.println("链表中间节点是:" + a);
        System.out.println("链表尾部节点是：" + b);
    }

    public static void main(String[] args) {
        LinkedNodeSelector.locate(init());

    }
}
