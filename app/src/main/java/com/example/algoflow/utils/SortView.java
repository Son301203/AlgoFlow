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
    private Thread sortThread;
    private Sorting algorithms;

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

        float barWidth = (float) getWidth() / arraySize;
        float scaleFactor = 10.0f;

        for (int i = 0; i < arraySize; i++){
            if(i == currentIndex || i == compareIndex) {
                barPaint.setColor(Color.RED);
            }else{
                barPaint.setColor(Color.BLUE);
            }

            float barHeight = array[i] * scaleFactor;
            canvas.drawRect(
                    i * barWidth,
                    getHeight() - barHeight,
                    (i + 1) * barWidth,
                    getHeight(),
                    barPaint
            );

            String value = String.valueOf(array[i]);
            Rect textBounds = new Rect();
            textPaint.getTextBounds(value, 0, value.length(), textBounds);
            canvas.drawText(
                    value,
                    i * barWidth + barWidth / 2,
                    getHeight() - barHeight - textBounds.height() - 5,
                    textPaint
            );
        }
    }
    public void startSorting(){
        if(!isSorting && (sortThread == null || !sortThread.isAlive())) {
            if(algorithms == null) return;
            isSorting = true;
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
        isSorting = false;
    }

    public void reset() {
        isSorting = false;
        currentIndex = -1;
        compareIndex = -1;
        array = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(50);
        }
        invalidate();
    }
}
