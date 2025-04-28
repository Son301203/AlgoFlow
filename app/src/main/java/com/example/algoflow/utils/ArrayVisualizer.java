package com.example.algoflow.utils;

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

    public void drawArray(Canvas canvas, int[] array, int activeIndex, float xOffset, float yOffset){
        for (int i = 0; i < array.length; i++){
            float left = xOffset + i * (cellWidth + cellSpacing);
            float top = yOffset;
            float right = left + cellWidth;
            float bottom = top + cellHeight;

            // cell background
            cellPaint.setColor(i <= activeIndex ? Color.parseColor("#1cb81c") : Color.LTGRAY);
            canvas.drawRect(left, top, right, bottom, cellPaint);

            // cell value
            String value = i <= activeIndex ? String.valueOf(array[i]) : "";
            Rect textBounds = new Rect();
            textPaint.getTextBounds(value, 0, value.length(), textBounds);
            canvas.drawText(value, left + cellWidth / 2, top + cellHeight / 2 + textBounds.height() / 2, textPaint);

            // index cell
            canvas.drawText(String.valueOf(i), left + cellWidth / 2, bottom + 20, indexPaint);

            //active cell
            if(i == activeIndex){
                RectF highlightRect = new RectF(left, top, right, bottom);
                canvas.drawRect(highlightRect, highlightPaint);
            }
        }
    }

    public float getTotalWidth(int arraySize) {
        return arraySize * (cellWidth + cellSpacing) - cellSpacing;
    }

    public float getTotalHeight() {
        return cellHeight + 40f; //
    }

    public void drawTopPointer(Canvas canvas, int topIndex, float xOffset, float yOffset) {
        if (topIndex >= 0) {
            float left = xOffset + topIndex * (cellWidth + cellSpacing);
            canvas.drawText("Top", left + cellWidth / 2, yOffset - 20, indexPaint);
        }
    }
}
