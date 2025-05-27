package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.example.algoflow.data_structures.algorithms.HashMap;
import com.example.algoflow.visualizer.HashVisualizer;

public class HashMapView extends View {
    private HashMap hash;
    private HashVisualizer visualizer;
    private int highlightKey = -1;

    public HashMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        hash = new HashMap();
        visualizer = new HashVisualizer();
    }

    public void setHash(HashMap hash) {
        this.hash = hash;
        invalidate();
    }

    public void put(int key, int value) {
        hash.put(key, value);
        highlightKey = key;
        invalidate();
    }

    public void remove(int key) {
        hash.remove(key);
        highlightKey = key;
        invalidate();
    }

    public int get(int key) {
        int value = hash.get(key);
        if (value != -1) {
            highlightKey = key;
            invalidate();
        }
        return value;
    }

    public void random() {
        hash.random();
        invalidate();
    }

    public void clear() {
        hash.clear();
        highlightKey = -1;
        invalidate();
    }

    public boolean isEmpty() {
        return hash.isEmpty();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hash != null) {
            float startX = getWidth() / 2f;
            float startY = 100f;
            visualizer.drawHash(hash, canvas, startX, startY, highlightKey);
        }
    }
}