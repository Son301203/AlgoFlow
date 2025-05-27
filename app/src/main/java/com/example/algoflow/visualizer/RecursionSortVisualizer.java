package com.example.algoflow.visualizer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.List;
import java.util.Random;

public class RecursionSortVisualizer {
    private Paint[] barPaints;
    private Paint textPaint;
    private Paint highlightPaint;
    private Paint sortedPaint;
    private final float BAR_WIDTH = 40f;
    private final float BAR_SPACING = 10f;
    private final float TEXT_SIZE = 20f;
    private final float MAX_HEIGHT = 300f;
    private static final int MAX_LEVELS = 4;

    public RecursionSortVisualizer() {
        // Khởi tạo mảng màu cho từng mức đệ quy
        barPaints = new Paint[MAX_LEVELS];
        int[] colors = {
                Color.parseColor("#8fd9e3"),
                Color.parseColor("#FF5722"),
                Color.parseColor("#FFC107"),
                Color.parseColor("#4CAF50")
        };
        for (int i = 0; i < MAX_LEVELS; i++) {
            barPaints[i] = new Paint();
            barPaints[i].setStyle(Paint.Style.FILL);
            barPaints[i].setColor(colors[i % colors.length]);
        }

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        highlightPaint = new Paint();
        highlightPaint.setStyle(Paint.Style.FILL);
        highlightPaint.setColor(Color.parseColor("#FFFF66")); // Highlight

        sortedPaint = new Paint();
        sortedPaint.setStyle(Paint.Style.FILL);
        sortedPaint.setColor(Color.parseColor("#2196F3")); //done
    }

    public void drawBars(Canvas canvas, int[] array, List<Integer> comparingIndices, List<Integer> sortedIndices, int level, float startX, float startY) {
        if (array == null || array.length == 0 || array.length > 10) return;

        float totalWidth = array.length * (BAR_WIDTH + BAR_SPACING) - BAR_SPACING;
        float xOffset = startX - totalWidth / 2;

        int maxValue = array[0];
        for (int value : array) {
            if (value > maxValue) maxValue = value;
        }
        if (maxValue == 0) maxValue = 1;

        // bar
        for (int i = 0; i < array.length; i++) {
            float barHeight = (array[i] / (float) maxValue) * MAX_HEIGHT;
            float barX = xOffset + i * (BAR_WIDTH + BAR_SPACING);
            float barY = startY - barHeight;
            RectF barRect = new RectF(barX, barY, barX + BAR_WIDTH, startY);

            Paint paint = barPaints[Math.min(level, MAX_LEVELS - 1)];
            if (sortedIndices.contains(i)) {
                canvas.drawRect(barRect, sortedPaint);
            } else if (comparingIndices.contains(i)) {
                canvas.drawRect(barRect, highlightPaint);
            } else {
                canvas.drawRect(barRect, paint);
            }

            // value
            drawTextCentered(canvas, String.valueOf(array[i]), barX + BAR_WIDTH / 2, barY - 10, textPaint);
        }
    }

    private void drawTextCentered(Canvas canvas, String text, float x, float y, Paint paint) {
        canvas.drawText(text, x, y, paint);
    }
}