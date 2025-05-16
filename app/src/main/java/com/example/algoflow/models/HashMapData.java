package com.example.algoflow.models;

import androidx.annotation.Nullable;

public class HashMapData {
    private int key;
    private int value;

    public HashMapData(int key, int value){
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof HashMapData)
            return this.key == ((HashMapData)obj).key;
        return false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
