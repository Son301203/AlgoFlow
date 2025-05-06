package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.LStackQueue;
import com.example.algoflow.models.ListNode;

public class StackWithLinkedList implements LStackQueue {
    private ListNode head;
    private int length;
    private final int maximum = 10;

    public StackWithLinkedList() {
        this.head = head;
        this.length = 0;
    }
    public StackWithLinkedList(ListNode head) {
        this.head = head;
        this.length = 0;
        ListNode current = head;
        while (current != null) {
            this.length++;
            current = current.next;
        }
    }

    public ListNode getHead() {
        return head;
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
        if (isFull()) return head;

        ListNode newNode = new ListNode(value);
        if (!isEmpty()) {
            newNode.next = head;
        }
        head = newNode;
        length++;
        return head;
    }

    @Override
    public int pop() {
        if (isEmpty()) return -1;
        int value = head.value;
        head = head.next;
        length--;
        return value;
    }

    public int peek(){
        if (isEmpty()) return -1;
        return head.value;
    }

    public void clear() {
        head = null;
        length = 0;
    }

    public void random() {
        clear();
        int nodeCount = 5 + (int) (Math.random() * 6);
        for (int i = 0; i < nodeCount; i++) {
            int value = 1 + (int) (Math.random() * 50);
            push(value);
        }
    }
}
