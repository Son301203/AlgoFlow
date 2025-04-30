package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.IStackQueue;

public class StackWithArray implements IStackQueue {
    private int[] array;
    private int size;
    private int topIndex;

    public StackWithArray(int size) {
        array = new int[size];
        this.size = size;
        topIndex = -1;
    }

    public int[] getArray() {
        return array;
    }

    public int getSize() {
        return size;
    }

    public int getTopIndex() {
        return topIndex;
    }


    @Override
    public boolean isEmpty() {
        if(topIndex <= -1){
            return true;
        }
        return false;
    }

    @Override
    public boolean isFull() {
        if(topIndex == size - 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean push(int value) {
        if(!isFull()){
            topIndex++;
            array[topIndex] = value;
            return true;
        }
        return false;
    }

    @Override
    public int pop() {
        if(!isEmpty()){
            int value = array[topIndex];
            topIndex--;
            return value;
        }
        return -1;
    }

    public int peek() {
        if(!isEmpty()){
            int value = array[topIndex];
            return value;
        }
        return -1;
    }
}
