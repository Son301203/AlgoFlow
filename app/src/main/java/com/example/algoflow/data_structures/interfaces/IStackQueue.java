package com.example.algoflow.data_structures.interfaces;

public interface IStackQueue {
    public boolean isEmpty();
    public boolean isFull();
    public boolean push(int value);
    public int pop();
    public int peek();
}
