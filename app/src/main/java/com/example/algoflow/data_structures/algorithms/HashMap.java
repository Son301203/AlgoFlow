package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.IHashing;
import com.example.algoflow.models.HashMapData;

import java.util.ArrayList;

public class HashMap implements IHashing {
    private final int SIZE = 10;
    private ArrayList<HashMapData>[] buckets;

    public HashMap(){
        buckets = new ArrayList[SIZE];
        for(int i = 0; i < buckets.length; i++){
            buckets[i] = new ArrayList<>();
        }
    }

    public ArrayList<HashMapData>[] getBuckets() {
        return buckets;
    }

    @Override
    public int hashFunction(int key) {
        return key % SIZE;
    }

    @Override
    public void remove(int key) {
        int hashValueIndex = hashFunction(key);
        ArrayList<HashMapData> bucket = buckets[hashValueIndex];
        HashMapData deleteData = new HashMapData(key, 0);
        int keyIndex = bucket.indexOf(deleteData);
        if(keyIndex >= 0){
            bucket.remove(deleteData);
        }
    }

    // search value
    @Override
    public int get(int key) {
        int hashValueIndex = hashFunction(key);
        ArrayList<HashMapData> bucket = buckets[hashValueIndex];
        HashMapData getData = new HashMapData(key, 0);
        int keyIndex = bucket.indexOf(getData);
        if(keyIndex >= 0){
            return bucket.get(keyIndex).getValue();
        }
        return -1;
    }

    public void put(int key, int value){
        int hashValueIndex = hashFunction(key);
        ArrayList<HashMapData> bucket = buckets[hashValueIndex];
        HashMapData data = new HashMapData(key, value);
        int keyIndex = bucket.indexOf(data);

        if(keyIndex < 0){
            bucket.add(data);
        }else{
            bucket.get(keyIndex).setValue(value);
        }
    }

    public void clear() {
        for (ArrayList<HashMapData> bucket : buckets) {
            bucket.clear();
        }
    }

    public boolean isEmpty() {
        for (ArrayList<HashMapData> bucket : buckets) {
            if (!bucket.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void random(){
        clear();
        int nodeCount = 5 + (int) (Math.random() * 6);
        for (int i = 0; i < nodeCount; i++) {
            int key = 1 + (int) (Math.random() * 100);
            int value = 1 + (int) (Math.random() * 100);
            put(key, value);
        }
    }
}
