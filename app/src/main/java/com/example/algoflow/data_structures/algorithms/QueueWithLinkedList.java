package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.LStackQueue;
import com.example.algoflow.models.ListNode;

public class QueueWithLinkedList implements LStackQueue {
    private ListNode head;
    private ListNode tail;
    private int length;
    private final int maximum = 10;

    public QueueWithLinkedList() {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }

    public ListNode getHead() {
        return head;
    }

    public ListNode getTail() {
        return tail;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean isFull() {
        return length >= maximum;
    }

    @Override
    public ListNode push(int value) {
        if (isFull()) {
            return head;
        }
        ListNode newNode = new ListNode(value);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        length++;
        return head;
    }

    @Override
    public int pop() {
        if(isEmpty()) return -1;
        int value = head.value;
        head = head.next;
        length--;
        if(isEmpty()) tail = null;
        return value;
    }

    public void clear(){
        head = tail = null;
        length = 0;
    }

    public void random(){
        clear();
        int nodeCount = 5 + (int) (Math.random() * 6);
        for (int i = 0; i < nodeCount; i++) {
            int value = 1 + (int) (Math.random() * 50);
            push(value);
        }
    }
}
