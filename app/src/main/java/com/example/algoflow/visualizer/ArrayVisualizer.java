package com.example.algoflow.visualizer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class ArrayVisualizer {
    private Paint cellPaint;
    private Paint textPaint;
    private Paint indexPaint;
    private Paint highlightPaint;
    private final float cellWidth = 80f;
    private final float cellHeight = 80f;
    private final float cellSpacing = 10f;
    private final float textSize = 30f;

    public ArrayVisualizer() {
        cellPaint = new Paint();
        cellPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        indexPaint = new Paint();
        indexPaint.setColor(Color.BLACK);
        indexPaint.setTextSize(20f);
        indexPaint.setTextAlign(Paint.Align.CENTER);

        highlightPaint = new Paint();
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setColor(Color.RED);
        highlightPaint.setStrokeWidth(5f);
    }

    // drawArray Stack or Queue
    public void drawArray(Canvas canvas, int[] array, int head, int tail, int highlightIndex, float xOffset, float yOffset) {
        for (int i = 0; i < array.length; i++) {
            float left = xOffset + i * (cellWidth + cellSpacing);
            float top = yOffset;
            float right = left + cellWidth;
            float bottom = top + cellHeight;

            // Cell background
            boolean isActive = (head == -1 && tail == -1) ? false : (head <= tail ? i >= head && i <= tail : i <= tail);
            cellPaint.setColor(isActive ? Color.parseColor("#1cb81c") : Color.LTGRAY);
            canvas.drawRect(left, top, right, bottom, cellPaint);

            // Cell value
            String value = isActive ? String.valueOf(array[i]) : "";
            Rect textBounds = new Rect();
            textPaint.getTextBounds(value, 0, value.length(), textBounds);
            canvas.drawText(value, left + cellWidth / 2, top + cellHeight / 2 + textBounds.height() / 2, textPaint);

            // Index cell
            canvas.drawText(String.valueOf(i), left + cellWidth / 2, bottom + 20, indexPaint);

            // Highlight active cell
            if (i == highlightIndex) {
                RectF highlightRect = new RectF(left, top, right, bottom);
                canvas.drawRect(highlightRect, highlightPaint);
            }
        }
    }

    // draw Array stack
    public void drawStackArray(Canvas canvas, int[] array, int activeIndex, float xOffset, float yOffset) {
        drawArray(canvas, array, 0, activeIndex, activeIndex, xOffset, yOffset);
    }

    // draw Queue stack
    public void drawQueueArray(Canvas canvas, int[] array, int head, int tail, int highlightIndex, float xOffset, float yOffset) {
        drawArray(canvas, array, head, tail, highlightIndex, xOffset, yOffset);
    }

    // width
    public float getTotalWidth(int arraySize) {
        return arraySize * (cellWidth + cellSpacing) - cellSpacing;
    }

    // height
    public float getTotalHeight() {
        return cellHeight + 40f;
    }

    //  Queue
    public void drawQueuePointers(Canvas canvas, int front, int rear, float xOffset, float yOffset) {
        if(front == rear){
            float left = xOffset + front * (cellWidth + cellSpacing);
            canvas.drawText("Head", left + cellWidth / 2, yOffset - 50, indexPaint);
        }
        if (front >= 0 && front != rear) {
            float left = xOffset + front * (cellWidth + cellSpacing);
            canvas.drawText("Head", left + cellWidth / 2, yOffset - 20, indexPaint);
        }
        if (rear >= 0) {
            float left = xOffset + rear * (cellWidth + cellSpacing);
            canvas.drawText("Tail", left + cellWidth / 2, yOffset - 20, indexPaint);
        }
    }

    // Stack
    public void drawTopPointer(Canvas canvas, int topIndex, float xOffset, float yOffset) {
        drawQueuePointers(canvas, -1, topIndex, xOffset, yOffset);
    }
}