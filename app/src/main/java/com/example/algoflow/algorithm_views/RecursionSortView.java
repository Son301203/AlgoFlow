package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.algoflow.data_structures.interfaces.RecursionSorting;
import com.example.algoflow.data_structures.interfaces.Sorting;
import com.example.algoflow.utils.AnimationManager;
import com.example.algoflow.visualizer.RecursionSortVisualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecursionSortView extends View {
    private int[] array;
    private int arraySize = 5;
    private RecursionSortVisualizer visualizer;
    private List<int[]> recursionArrays;
    private List<Integer> highlights;
    private boolean isSorting = false;
    private boolean isPaused = false;
    private Thread sortThread;
    private RecursionSorting algorithms;
    private final Object pauseLock = new Object();
    private AnimationManager animationManager;

    public RecursionSortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        array = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(50);
        }
        visualizer = new RecursionSortVisualizer();
        recursionArrays = new ArrayList<>();
        highlights = new ArrayList<>();
        algorithms = null;
        animationManager = new AnimationManager(this, array, pauseLock);
    }

    public void setAlgorithms(RecursionSorting algorithms) {
        this.algorithms = algorithms;
    }

    public void setArraySize(int size) {
        if (size > 0 && size <= 20) {
            arraySize = size;
            reset();
        }
    }

    public void randomize() {
        stopSortingThread();
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(50);
        }
        recursionArrays.clear();
        highlights.clear();
        invalidate();
    }

    public boolean isSorting() {
        return isSorting;
    }

    public void setSorting(boolean sorting) {
        isSorting = sorting;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public void startSort() {
        recursionArrays.clear();
        recursionArrays.add(array.clone());
        highlights.clear();
        invalidate();
    }

    public void updateStep(int[] array, List<Integer> highlightIndices) {
        this.array = array.clone();
        recursionArrays.add(array.clone());
        highlights.clear();
        highlights.addAll(highlightIndices);
        postInvalidateDelayed(Sorting.ANIMATION_DELAY);
    }

    public void finishSort(int[] sortedArray) {
        this.array = sortedArray.clone();
        recursionArrays.add(sortedArray.clone());
        highlights.clear();
        postInvalidateDelayed(Sorting.ANIMATION_DELAY);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (!recursionArrays.isEmpty()) {
            float startX = getWidth() / 2f;
            float startY = 100f;
            visualizer.drawRecursionTree(canvas, recursionArrays, highlights, startX, startY);
        }
    }

    public void startSorting() {
        if (!isSorting && (sortThread == null || !sortThread.isAlive())) {
            if (algorithms == null) return;
            isSorting = true;
            isPaused = false;
            startSort();
            sortThread = new Thread(() -> algorithms.sort(array, this));
            sortThread.start();
        }
    }

    public void pauseSorting() {
        if (isSorting && !isPaused) {
            isPaused = true;
            animationManager.pause();
        }
    }

    public void resumeSorting() {
        if (isPaused) {
            isPaused = false;
            animationManager.resume();
            synchronized (pauseLock) {
                pauseLock.notify();
            }
        }
    }

    public void checkPause() {
        if (isPaused) {
            synchronized (pauseLock) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    isSorting = false;
                }
            }
        }
    }

    public void reset() {
        stopSortingThread();
        isSorting = false;
        isPaused = false;
        array = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(50);
        }
        animationManager = new AnimationManager(this, array, pauseLock);
        recursionArrays.clear();
        highlights.clear();
        invalidate();
    }

    private void stopSortingThread() {
        if (sortThread != null && sortThread.isAlive()) {
            animationManager.cancel();
            isPaused = false;
            synchronized (pauseLock) {
                pauseLock.notify();
            }
            sortThread.interrupt();
            try {
                sortThread.join(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sortThread = null;
        }
    }
}