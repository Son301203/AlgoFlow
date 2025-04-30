package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.IStackQueue;

public class QueueWithArray implements IStackQueue {
    private int[] array;
    private int size;
    private int headIndex;
    private int tailIndex;

    public QueueWithArray(int size) {
        array = new int[size];
        this.size = size;
        headIndex = tailIndex = -1;
    }

    public int[] getArray() {
        return array;
    }

    public int getHeadIndex(){
        return headIndex;
    }

    public int getTailIndex(){
        return tailIndex;
    }

    @Override
    public boolean isEmpty() {
        return headIndex == -1 && tailIndex == -1;
    }

    @Override
    public boolean isFull() {
        return tailIndex == size - 1;
    }

    @Override
    public boolean push(int value) {
        if(!isFull()){
            if(isEmpty()){
                headIndex++;
            }
            tailIndex++;
            array[tailIndex] = value;
            return true;
        }
        return false;
    }

    @Override
    public int pop() {
        int value = -1;
        if(!isEmpty()){
            value = array[headIndex];
            headIndex++;
            if(headIndex > tailIndex){
                headIndex = tailIndex = -1;
            }
        }
        return value;
    }

    public int count(){
        if(isEmpty()) return 0;
        return tailIndex  - headIndex + 1;
    }
}
