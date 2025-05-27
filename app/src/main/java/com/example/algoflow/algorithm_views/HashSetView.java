package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.example.algoflow.data_structures.algorithms.HashSet;
import com.example.algoflow.visualizer.HashVisualizer;

public class HashSetView extends View{
    private HashSet hash;
    private HashVisualizer visualizer;
    private int highlightValue = -1;

    public HashSetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        hash = new HashSet();
        visualizer = new HashVisualizer();
    }

    public void setHash(HashSet hash) {
        this.hash = hash;
        invalidate();
    }

    public void put(int value) {
        hash.put(value);
        highlightValue = value;
        invalidate();
    }

    public void remove(int value) {
        hash.remove(value);
        highlightValue = value;
        invalidate();
    }

    public boolean contains(int value) {
        boolean result = hash.contains(value);
        if (result) {
            highlightValue = value;
            invalidate();
        }
        return result;
    }

    public void random() {
        hash.random();
        invalidate();
    }

    public void clear() {
        hash.clear();
        highlightValue = -1;
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
            visualizer.drawHash(hash, canvas, startX, startY, highlightValue);
        }
    }
}
