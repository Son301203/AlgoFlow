package com.example.algoflow.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.example.algoflow.data_structures.algorithms.HashMap;
import com.example.algoflow.data_structures.algorithms.HashSet;
import com.example.algoflow.data_structures.interfaces.IHashing;
import com.example.algoflow.models.HashMapData;

import java.util.ArrayList;

public class HashVisualizer {
    private Paint bucketPaint;
    private Paint valueCirclePaint;
    private Paint valueTextPaint;
    private Paint linePaint;
    private Paint highlightPaint;
    private final float BUCKET_WIDTH = 60f;
    private final float BUCKET_HEIGHT = 40f;
    private final float VALUE_RADIUS = 20f;
    private final float HORIZONTAL_SPACING = 70f;
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

    public void drawHash(IHashing hash, Canvas canvas, float startX, float startY, int highlightKey) {
        ArrayList<?>[] buckets = null;
        if (hash instanceof HashSet) {
            buckets = ((HashSet) hash).getBuckets();
        } else if (hash instanceof HashMap) {
            buckets = ((HashMap) hash).getBuckets();
        }
        if (buckets == null) return;

        int totalBuckets = buckets.length;
        float bucketWidth = totalBuckets * HORIZONTAL_SPACING;
        float startBucketX = startX - bucketWidth / 2;

        // Draw buckets
        for (int i = 0; i < totalBuckets; i++) {
            float x = startBucketX + i * HORIZONTAL_SPACING;
            float y = startY;
            RectF bucketRect = new RectF(x - BUCKET_WIDTH / 2, y - BUCKET_HEIGHT / 2, x + BUCKET_WIDTH / 2, y + BUCKET_HEIGHT / 2);
            canvas.drawRect(bucketRect, bucketPaint);
            drawTextCentered(canvas, String.valueOf(i), x, y, valueTextPaint);

            // Draw values in bucket
            ArrayList<?> values = buckets[i];
            if (values.isEmpty()) continue;

            for (int j = 0; j < values.size(); j++) {
                float valueX = x;
                float valueY = startY + (j + 1) * VERTICAL_SPACING;
                if (values.get(0) instanceof HashMapData) {
                    HashMapData data = (HashMapData) values.get(j);
                    int key = data.getKey();
                    int value = data.getValue();
                    canvas.drawCircle(valueX, valueY, VALUE_RADIUS, valueCirclePaint);
                    drawTextCentered(canvas, key + ": " + value, valueX, valueY, valueTextPaint);

                    // Highlight
                    if (key == highlightKey) {
                        canvas.drawCircle(valueX, valueY, VALUE_RADIUS, highlightPaint);
                    }
                } else if (values.get(0) instanceof Integer) {
                    int key = (Integer) values.get(j);
                    canvas.drawCircle(valueX, valueY, VALUE_RADIUS, valueCirclePaint);
                    drawTextCentered(canvas, String.valueOf(key), valueX, valueY, valueTextPaint);

                    // Highlight
                    if (key == highlightKey) {
                        canvas.drawCircle(valueX, valueY, VALUE_RADIUS, highlightPaint);
                    }
                }

                // Draw arrow to first value
                if (j == 0) {
                    drawArrow(canvas, x, y + BUCKET_HEIGHT / 2, valueX, valueY - VALUE_RADIUS);
                }

                // Arrows connecting the values
                if (j < values.size() - 1) {
                    float nextValueY = startY + (j + 2) * VERTICAL_SPACING;
                    drawArrow(canvas, valueX, valueY + VALUE_RADIUS, valueX, nextValueY - VALUE_RADIUS);
                }
            }
        }
    }

    private void drawTextCentered(Canvas canvas, String text, float x, float y, Paint paint) {
        canvas.drawText(text, x, y + paint.getTextSize() / 3, paint);
    }

    private void drawArrow(Canvas canvas, float startX, float startY, float endX, float endY) {
        canvas.drawLine(startX, startY, endX, endY, linePaint);

        // Head of arrow
        float arrowSize = 10f;
        Path path = new Path();
        path.moveTo(endX, endY);
        path.lineTo(endX - arrowSize, endY - arrowSize);
        path.lineTo(endX + arrowSize, endY - arrowSize);
        path.close();
        canvas.drawPath(path, linePaint);
    }
}