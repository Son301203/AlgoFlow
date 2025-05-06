package com.example.algoflow.data_structures.interfaces;

import com.example.algoflow.models.ListNode;

public interface LStackQueue {
    public boolean isEmpty();
    public boolean isFull();
    public ListNode push(int value);
    public int pop();
}
