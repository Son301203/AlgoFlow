package com.example.algoflow.visualizer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.List;

public class RecursionSortVisualizer {
    private Paint rectPaint;
    private Paint textPaint;
    private Paint highlightPaint;
    private Paint linePaint;
    private final float RECT_WIDTH = 50f;
    private final float RECT_HEIGHT = 40f;
    private final float HORIZONTAL_SPACING = 60f;
    private final float VERTICAL_SPACING = 80f;
    private final float TEXT_SIZE = 20f;

    public RecursionSortVisualizer() {
        rectPaint = new Paint();
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setColor(Color.parseColor("#8fd9e3"));

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        highlightPaint = new Paint();
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setColor(Color.parseColor("#FFFF66"));
        highlightPaint.setStrokeWidth(4f);

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2f);
    }

    public void drawRecursionTree(Canvas canvas, List<int[]> arrays, List<Integer> highlights, float startX, float startY) {
        if (arrays == null || arrays.isEmpty()) return;

        //  recursion
        for (int i = 0; i < arrays.size(); i++) {
            int[] arr = arrays.get(i);
            float x = startX + (i % 2 == 0 ? -HORIZONTAL_SPACING * (arrays.size() / 2 - i / 2) : HORIZONTAL_SPACING * (i / 2 + 1));
            float y = startY + (i / 2) * VERTICAL_SPACING;

            for (int j = 0; j < arr.length; j++) {
                float rectX = x + j * (RECT_WIDTH + 5f);
                RectF rect = new RectF(rectX, y - RECT_HEIGHT / 2, rectX + RECT_WIDTH, y + RECT_HEIGHT / 2);
                canvas.drawRect(rect, rectPaint);
                drawTextCentered(canvas, String.valueOf(arr[j]), rectX + RECT_WIDTH / 2, y, textPaint);

                // Highlight
                if (highlights.contains(j)) {
                    canvas.drawRect(rect, highlightPaint);
                }
            }

            // line connect
            if (i > 0 && i % 2 == 1) {
                int parentIndex = (i - 1) / 2;
                float parentX = startX + (parentIndex % 2 == 0 ? -HORIZONTAL_SPACING * (arrays.size() / 2 - parentIndex / 2) : HORIZONTAL_SPACING * (parentIndex / 2 + 1));
                float parentY = startY + (parentIndex / 2) * VERTICAL_SPACING;
                canvas.drawLine(parentX, parentY + RECT_HEIGHT / 2, x, y - RECT_HEIGHT / 2, linePaint);
            }
        }
    }

    private void drawTextCentered(Canvas canvas, String text, float x, float y, Paint paint) {
        canvas.drawText(text, x, y + paint.getTextSize() / 3, paint);
    }
}