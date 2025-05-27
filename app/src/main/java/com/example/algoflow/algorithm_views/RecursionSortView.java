package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.algoflow.data_structures.interfaces.RecursionSorting;
import com.example.algoflow.utils.AnimationManager;
import com.example.algoflow.visualizer.RecursionSortVisualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecursionSortView extends View {
    private int[] array;
    private int arraySize = 5;
    private RecursionSortVisualizer visualizer;
    private List<Integer> comparingIndices;
    private List<Integer> sortedIndices;
    private boolean isSorting = false;
    private boolean isPaused = false;
    private Thread sortThread;
    private RecursionSorting algorithms;
    private final Object pauseLock = new Object();
    private AnimationManager animationManager;
    private int currentLevel = 0;

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
        comparingIndices = new ArrayList<>();
        sortedIndices = new ArrayList<>();
        algorithms = null;
        animationManager = new AnimationManager(this, array, pauseLock);
    }

    public void setAlgorithms(RecursionSorting algorithms) {
        this.algorithms = algorithms;
    }

    public void setArraySize(int size) {
        if (size > 0 && size <= 10) {
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
        comparingIndices.clear();
        sortedIndices.clear();
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

    public void startSort(int level) {
        currentLevel = level;
        comparingIndices.clear();
        sortedIndices.clear();
        invalidate();
    }

    public void updateStep(int[] array, List<Integer> compareIndices, List<Integer> sortedIndices, int level) {
        this.array = array.clone();
        this.comparingIndices.clear();
        this.comparingIndices.addAll(compareIndices);
        this.sortedIndices.clear();
        this.sortedIndices.addAll(sortedIndices);
        currentLevel = level;
        invalidate();
        animationManager.animateStep(); // Đồng bộ với animation
    }

    public void finishSort(int[] sortedArray) {
        this.array = sortedArray.clone();
        this.comparingIndices.clear();
        this.sortedIndices.clear();
        for (int i = 0; i < array.length; i++) {
            this.sortedIndices.add(i);
        }
        currentLevel = 0;
        invalidate();
        animationManager.animateStep();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        float startX = getWidth() / 2f;
        float startY = getHeight() / 2f;
        visualizer.drawBars(canvas, array, comparingIndices, sortedIndices, currentLevel, startX, startY);
    }

    public void startSorting() {
        if (!isSorting && (sortThread == null || !sortThread.isAlive())) {
            if (algorithms == null) return;
            isSorting = true;
            isPaused = false;
            startSort(0);
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
        comparingIndices.clear();
        sortedIndices.clear();
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