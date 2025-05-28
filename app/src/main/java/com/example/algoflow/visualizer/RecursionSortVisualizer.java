package com.example.algoflow.visualizer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.List;
import java.util.Map;

public class RecursionSortVisualizer {
    private Paint[] barPaints;
    private Paint textPaint;
    private Paint sortedPaint;
    private Paint normalPaint;
    private final float BAR_WIDTH = 40f;
    private final float BAR_SPACING = 10f;
    private final float TEXT_SIZE = 20f;
    private final float MAX_HEIGHT = 300f;
    private static final int MAX_LEVELS = 4;
    private final float LEVEL_OFFSET = 60f;

    public RecursionSortVisualizer() {
        barPaints = new Paint[MAX_LEVELS];
        int[] colors = {
                Color.parseColor("#8fd9e3"), // Level 0
                Color.parseColor("#FF5722"), // Level 1
                Color.parseColor("#FFC107"), // Level 2
                Color.parseColor("#4CAF50")  // Level 3
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

        sortedPaint = new Paint();
        sortedPaint.setStyle(Paint.Style.FILL);
        sortedPaint.setColor(Color.parseColor("#2196F3"));

        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.FILL);
        normalPaint.setColor(Color.parseColor("#CCCCCC")); // Gray for non-active elements
    }

    public void drawBars(Canvas canvas, int[] array, List<Integer> comparingIndices,
                         List<Integer> sortedIndices, int level, float startX, float startY,
                         Map<Integer, Float> elementOffsets) {
        if (array == null || array.length == 0 || array.length > 10) return;

        float totalWidth = array.length * (BAR_WIDTH + BAR_SPACING) - BAR_SPACING;
        float xOffset = startX - totalWidth / 2;

        int maxValue = array[0];
        for (int value : array) {
            if (value > maxValue) maxValue = value;
        }
        if (maxValue == 0) maxValue = 1;

        for (int i = 0; i < array.length; i++) {
            float barHeight = (array[i] / (float) maxValue) * MAX_HEIGHT;
            float barX = xOffset + i * (BAR_WIDTH + BAR_SPACING);

            // Get the offset for this element, default to 0 if not specified
            float elementOffset = elementOffsets != null && elementOffsets.containsKey(i)
                    ? elementOffsets.get(i) : 0f;
            float barY = startY - barHeight + elementOffset;

            RectF barRect = new RectF(barX, barY, barX + BAR_WIDTH, startY + elementOffset);

            // Choose paint based on element state
            Paint paint;
            if (sortedIndices.contains(i)) {
                paint = sortedPaint;
            } else if (elementOffset != 0f) {
                // Element is being processed (moved down)
                paint = barPaints[Math.min(level, MAX_LEVELS - 1)];
            } else {
                // Element is at original position
                paint = normalPaint;
            }

            canvas.drawRect(barRect, paint);
            drawTextCentered(canvas, String.valueOf(array[i]),
                    barX + BAR_WIDTH / 2, barY - 10, textPaint);
        }
    }

    private void drawTextCentered(Canvas canvas, String text, float x, float y, Paint paint) {
        canvas.drawText(text, x, y, paint);
    }
}
