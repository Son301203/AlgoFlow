package com.example.algoflow.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.algoflow.data_structures.interfaces.Sorting;

import java.util.Random;

public class SortView extends View{
    private int[] array;
    private Paint barPaint;
    private Paint textPaint;
    private int arraySize = 5;
    private int currentIndex = -1;
    private int compareIndex = -1;
    private boolean isSorting = false;
    private boolean isPaused = false;
    private Thread sortThread;
    private Sorting algorithms;
    private final Object pauseLock = new Object();

    public SortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        array = new int[arraySize];
        Random random = new Random();
        for(int i = 0; i < arraySize; i++){
            array[i] = random.nextInt(50);
        }
        barPaint = new Paint();
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        algorithms = null;
    }

    public void setAlgorithms(Sorting algorithms){
        this.algorithms = algorithms;
    }

    public void setArraySize(int size){
        if(size > 0 && size <= 20) {
            arraySize = size;
            reset();
        }
    }


    public void randomize(){
        stopSortingThread();
        Random random = new Random();
        for (int i = 0; i < arraySize; i++){
            array[i] = random.nextInt(50);
        }
        currentIndex = -1;
        compareIndex = -1;
        invalidate();
    }

    public boolean isSorting(){
        return isSorting;
    }

    public void setSorting(boolean sorting){
        isSorting = sorting;
    }

    public void setCurrentIndex(int index) {
        currentIndex = index;
    }

    public void setCompareIndex(int index) {
        compareIndex = index;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if(array == null) return;

        // width and margin
        float margin = 10.0f;
        float totalMarginWidth = margin * (arraySize - 1);
        float barWidth = (float) (getWidth() - totalMarginWidth) / arraySize;
        float scaleFactor = 10.0f;

        // border
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.GRAY);
        borderPaint.setStrokeWidth(2.0f);

        for (int i = 0; i < arraySize; i++){
            // position X
            float left = i * (barWidth + margin);
            float right = left + barWidth;

            if(i == currentIndex || i == compareIndex) {
                barPaint.setColor(Color.parseColor("#1cb81c"));
            }else{
                barPaint.setColor(Color.parseColor("#8fd9e3"));
            }
            barPaint.setStyle(Paint.Style.FILL);

            // height
            float barHeight = array[i] * scaleFactor;

            // bar column
            canvas.drawRect(
                    left,
                    getHeight() - barHeight,
                    right,
                    getHeight(),
                    barPaint
            );
            //border
            canvas.drawRect(
                    left,
                    getHeight() - barHeight,
                    right,
                    getHeight(),
                    borderPaint
            );

            // value
            String value = String.valueOf(array[i]);
            Rect textBounds = new Rect();
            textPaint.getTextBounds(value, 0, value.length(), textBounds);
            canvas.drawText(
                    value,
                    left + barWidth / 2,
                    getHeight() - barHeight - textBounds.height() - 10,
                    textPaint
            );
        }
    }

    public void startSorting(){
        if(!isSorting && (sortThread == null || !sortThread.isAlive())) {
            if(algorithms == null) return;
            isSorting = true;
            isPaused = false;
            sortThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    algorithms.sort(array, SortView.this);
                }
            });
            sortThread.start();
        }
    }


    public void pauseSorting() {
        if (isSorting && !isPaused) {
            isPaused = true;
        }
    }

    public void resumeSorting() {
        if (isPaused) {
            isPaused = false;
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
        currentIndex = -1;
        compareIndex = -1;
        array = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(50);
        }
        invalidate();
    }

    private void stopSortingThread() {
        if (sortThread != null && sortThread.isAlive()) {
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
