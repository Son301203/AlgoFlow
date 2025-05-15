package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.IHashing;

import java.util.ArrayList;

public class HashSet implements IHashing {
    private final int SIZE = 1000;
    private ArrayList<Integer>[] buckets;

    public HashSet(){
        buckets = new ArrayList[SIZE];
        for(int i = 0; i < buckets.length; i++){
            buckets[i] = new ArrayList<>();
        }
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

    public int search(int key){
        if(!contains(key))
            return -1;
        int hashValueIndex = hashFunction(key);
        ArrayList<Integer> bucket = buckets[hashValueIndex];
        int keyIndex = bucket.indexOf(key);

        return bucket.get(keyIndex);

    }
}
