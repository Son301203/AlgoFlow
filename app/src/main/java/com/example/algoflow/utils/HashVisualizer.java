package com.example.algoflow.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import com.example.algoflow.data_structures.algorithms.HashSet;
import com.example.algoflow.data_structures.interfaces.IHashing;

import java.util.ArrayList;

public class HashVisualizer {
    private Paint bucketPaint;
    private Paint valueCirclePaint;
    private Paint valueTextPaint;
    private Paint linePaint;
    private Paint highlightPaint;
    private final float BUCKET_SIZE = 50f;
    private final float VALUE_RADIUS = 20f;
    private final float HORIZONTAL_SPACING = 60f;
    private final float VERTICAL_SPACING = 80f;
    private final float TEXT_SIZE = 20f;

    public HashVisualizer() {
        bucketPaint = new Paint();
        bucketPaint.setStyle(Paint.Style.FILL);
        bucketPaint.setColor(Color.parseColor("#8fd9e3"));

        valueCirclePaint = new Paint();
        valueCirclePaint.setStyle(Paint.Style.FILL);
        valueCirclePaint.setColor(Color.parseColor("#8fd9e3"));

        valueTextPaint = new Paint();
        valueTextPaint.setStyle(Paint.Style.FILL);
        valueTextPaint.setColor(Color.parseColor("#000000"));
        valueTextPaint.setTextSize(TEXT_SIZE);
        valueTextPaint.setTextAlign(Paint.Align.CENTER);

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(3f);

        highlightPaint = new Paint();
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setColor(Color.parseColor("#FFFF66"));
        highlightPaint.setStrokeWidth(4f);
    }

    public void drawHash(IHashing hash, Canvas canvas, float startX, float startY, int highlightValue) {
        ArrayList<Integer>[] buckets = null;
        if (hash instanceof HashSet) {
            buckets = ((HashSet) hash).getBuckets();
        }
        if (buckets == null) return;

        int totalBuckets = buckets.length;
        float bucketWidth = totalBuckets * HORIZONTAL_SPACING;
        float startBucketX = startX - bucketWidth / 2;

        // draw bucket
        for (int i = 0; i < totalBuckets; i++) {
            float x = startBucketX + i * HORIZONTAL_SPACING;
            float y = startY;
            canvas.drawCircle(x, y, BUCKET_SIZE / 2, bucketPaint);
            drawTextCentered(canvas, String.valueOf(i), x, y, valueTextPaint);

            // draw value in bucket
            ArrayList<Integer> values = buckets[i];
            if (values.isEmpty()) continue;

            for (int j = 0; j < values.size(); j++) {
                int value = values.get(j);
                float valueX = x;
                float valueY = startY + (j + 1) * VERTICAL_SPACING;
                canvas.drawCircle(valueX, valueY, VALUE_RADIUS, valueCirclePaint);
                drawTextCentered(canvas, String.valueOf(value), valueX, valueY, valueTextPaint);

                // draw arrow to first value
                if (j == 0) {
                    drawArrow(canvas, x, y + BUCKET_SIZE / 2, valueX, valueY - VALUE_RADIUS);
                }

                // arrows connecting the values
                if (j < values.size() - 1) {
                    float nextValueY = startY + (j + 2) * VERTICAL_SPACING;
                    drawArrow(canvas, valueX, valueY + VALUE_RADIUS, valueX, nextValueY - VALUE_RADIUS);
                }

                // Highlight
                if (value == highlightValue) {
                    canvas.drawCircle(valueX, valueY, VALUE_RADIUS, highlightPaint);
                }
            }
        }
    }

    private void drawTextCentered(Canvas canvas, String text, float x, float y, Paint paint) {
        canvas.drawText(text, x, y + paint.getTextSize() / 3, paint);
    }

    private void drawArrow(Canvas canvas, float startX, float startY, float endX, float endY) {
        canvas.drawLine(startX, startY, endX, endY, linePaint);

        // head of arrow
        float arrowSize = 10f;
        Path path = new Path();
        path.moveTo(endX, endY);
        path.lineTo(endX - arrowSize, endY - arrowSize);
        path.lineTo(endX + arrowSize, endY - arrowSize);
        path.close();
        canvas.drawPath(path, linePaint);
    }
}