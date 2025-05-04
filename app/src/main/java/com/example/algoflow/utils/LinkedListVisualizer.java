package com.example.algoflow.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.example.algoflow.models.ListNode;

public class LinkedListVisualizer {
    private Paint nodePaint;
    private Paint textPaint;
    private Paint arrowPaint;
    private Paint highlightPaint;
    private final float NODE_RADIUS = 40f;
    private final float NODE_SPACING = 20f;
    private final float TEXT_SIZE = 30f;

    public LinkedListVisualizer() {
        nodePaint = new Paint();
        nodePaint.setStyle(Paint.Style.FILL);
        nodePaint.setColor(Color.parseColor("#8fd9e3"));

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        arrowPaint = new Paint();
        arrowPaint.setColor(Color.BLACK);
        arrowPaint.setStrokeWidth(5f);

        highlightPaint = new Paint();
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setColor(Color.parseColor("#FFFF66"));
        highlightPaint.setStrokeWidth(5f);
    }

    public void drawLinkedList(Canvas canvas, ListNode head, int highlightIndex, float startX, float startY, String headLabel, String tailLabel) {
        if (head == null) {
            return;
        }

        float x = startX;
        float y = startY;
        ListNode current = head;
        int index = 0;

        while (current != null) {
            // Draw node
            if (index == highlightIndex) {
                canvas.drawCircle(x, y, NODE_RADIUS, highlightPaint);
            }
            canvas.drawCircle(x, y, NODE_RADIUS, nodePaint);

            // Draw value
            String value = String.valueOf(current.value);
            Rect textBounds = new Rect();
            textPaint.getTextBounds(value, 0, value.length(), textBounds);
            canvas.drawText(value, x, y + textBounds.height() / 2, textPaint);

            // Draw arrow to next node
            if (current.next != null) {
                float arrowStartX = x + NODE_RADIUS;
                float arrowEndX = x + NODE_RADIUS + NODE_SPACING;
                canvas.drawLine(arrowStartX, y, arrowEndX, y, arrowPaint);
                // Draw arrowhead
                canvas.drawLine(arrowEndX, y, arrowEndX - 10, y - 10, arrowPaint);
                canvas.drawLine(arrowEndX, y, arrowEndX - 10, y + 10, arrowPaint);
            }

            // Draw labels
            if (index == 0 && headLabel != null && !headLabel.isEmpty()) {
                canvas.drawText(headLabel, x, y - NODE_RADIUS - 20, textPaint);
            }
            if (current.next == null && tailLabel != null && !tailLabel.isEmpty()) {
                canvas.drawText(tailLabel, x, y - NODE_RADIUS - 20, textPaint);
            }

            // Move to next node position
            x += 2 * NODE_RADIUS + NODE_SPACING;
            current = current.next;
            index++;
        }
    }

    public float getTotalWidth(ListNode head) {
        int count = 0;
        ListNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count * (2 * NODE_RADIUS + NODE_SPACING) - NODE_SPACING + 2 * NODE_RADIUS;
    }

    public float getTotalHeight() {
        return 2 * NODE_RADIUS + 40f;
    }
}