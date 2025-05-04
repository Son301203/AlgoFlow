package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.models.ListNode;

public class LinkedList {
    public ListNode addToHead(ListNode head, int value){
        ListNode newNode = new ListNode(value);
        newNode.next = head;
        return newNode;
    }

    public ListNode addToTail(ListNode head, int value){
        ListNode newNode = new ListNode(value);
        if (head == null) return newNode;

        ListNode curNode = head;
        while (curNode.next != null){
            curNode = curNode.next;
        }
        curNode.next = newNode;

        return head;
    }

    public ListNode addToIndex(ListNode head, int index, int value){
        if (index == 0) return addToHead(head, value);
        ListNode newNode = new ListNode(value);
        ListNode curNode = head;
        int count = 0;
        while(curNode != null){
            if(count == index - 1){
                newNode.next = curNode.next;
                curNode.next = newNode;
                break;
            }
            count++;
            curNode = curNode.next;
        }
        if (curNode == null && count < index) {
            return addToTail(head, value);
        }
        return head;
    }

    public ListNode removeHead(ListNode head){
        if(head != null) return head.next;
        return head;
    }

    public ListNode removeTail(ListNode head){
        if (head == null || head.next == null) return null;

        ListNode curNode = head;
        ListNode preNode = null;
        while (curNode.next != null) {
            preNode = curNode;
            curNode = curNode.next;
        }
        preNode.next = null;
        return head;
    }

    public ListNode removeAtIndex(ListNode head, int index){
        if (head == null || index < 0) return head;

        if (index == 0) return removeHead(head);

        ListNode curNode = head;
        ListNode preNode = null;
        int count = 0;
        while (curNode != null && count < index) {
            preNode = curNode;
            curNode = curNode.next;
            count++;
        }
        if (curNode != null) {
            preNode.next = curNode.next;
        }
        return head;
    }

    public boolean searchNodeValue(ListNode head, int value){
        ListNode curNode = head;
        while (curNode != null){
            if(curNode.value == value){
                return true;
            }
            curNode = curNode.next;
        }
        return false;
    }
}
