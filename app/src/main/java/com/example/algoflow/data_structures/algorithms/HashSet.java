package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.IHashing;

import java.util.ArrayList;

public class HashSet implements IHashing {
    private final int SIZE = 10;
    private ArrayList<Integer>[] buckets;

    public HashSet(){
        buckets = new ArrayList[SIZE];
        for(int i = 0; i < buckets.length; i++){
            buckets[i] = new ArrayList<>();
        }
    }
    public ArrayList<Integer>[] getBuckets() {
        return buckets;
    }

    @Override
    public int hashFunction(int key) {
        return key % SIZE;
    }

    @Override
    public void remove(int key) {
        int hashValueIndex = hashFunction(key);
        ArrayList<Integer> bucket = buckets[hashValueIndex];
        int keyIndex = bucket.indexOf(key);
        if(keyIndex >= 0){
            bucket.remove(keyIndex);
        }
    }

    // search key
    @Override
    public int get(int key) {
        if(!contains(key))
            return -1;
        int hashValueIndex = hashFunction(key);
        ArrayList<Integer> bucket = buckets[hashValueIndex];
        int keyIndex = bucket.indexOf(key);
        return bucket.get(keyIndex);
    }

    public void put(int key){
        int hashValueIndex = hashFunction(key);
        ArrayList<Integer> bucket = buckets[hashValueIndex];
        int keyIndex = bucket.indexOf(key);

        if(keyIndex < 0){
            bucket.add(key);
        }
    }

    public boolean contains(int key){
        int hashValueIndex = hashFunction(key);
        ArrayList<Integer> bucket = buckets[hashValueIndex];
        int keyIndex = bucket.indexOf(key);

        return keyIndex >= 0;
    }

    public void clear(){
        for (ArrayList<Integer> bucket : buckets) {
            bucket.clear();
        }
    }

    public void random(){
        clear();
        int nodeCount = 5 + (int) (Math.random() * 6);
        for (int i = 0; i < nodeCount; i++) {
            int value = 1 + (int) (Math.random() * 100);
            put(value);
        }
    }

    public boolean isEmpty() {
        for (ArrayList<Integer> bucket : buckets) {
            if (!bucket.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
