package com.example.algoflow.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.example.algoflow.models.TreeNode;

public class TreeVisualizer {
    private Paint nodePaint;
    private Paint textPaint;
    private Paint linePaint;
    private Paint highlightPaint;
    private final float NODE_RADIUS = 30f;
    private final float HORIZONTAL_SPACING = 60f;
    private final float VERTICAL_SPACING = 80f;
    private final float TEXT_SIZE = 24f;

    public TreeVisualizer() {
        nodePaint = new Paint();
        nodePaint.setStyle(Paint.Style.FILL);
        nodePaint.setColor(Color.parseColor("#8fd9e3"));

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(3f);

        highlightPaint = new Paint();
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setColor(Color.parseColor("#FFFF66"));
        highlightPaint.setStrokeWidth(4f);
    }

    public void drawTree(Canvas canvas, TreeNode root, float startX, float startY, int highlightValue) {
        if (root == null) {
            return;
        }

        // Calculate tree width
        float treeWidth = getTreeWidth(root) * HORIZONTAL_SPACING;
        float centerX = startX + treeWidth / 2;
        drawNode(canvas, root, centerX, startY, highlightValue);

        // Draw connections and children
        drawConnections(canvas, root, startX, startY, treeWidth, highlightValue);
    }

    private void drawNode(Canvas canvas, TreeNode node, float x, float y, int highlightValue) {
        // Draw node
        if (node.val == highlightValue) {
            canvas.drawCircle(x, y, NODE_RADIUS, highlightPaint);
        }
        canvas.drawCircle(x, y, NODE_RADIUS, nodePaint);

        // Draw value
        String value = String.valueOf(node.val);
        Rect textBounds = new Rect();
        textPaint.getTextBounds(value, 0, value.length(), textBounds);
        canvas.drawText(value, x, y + textBounds.height() / 2, textPaint);
    }

    private void drawConnections(Canvas canvas, TreeNode root, float startX, float startY, float treeWidth, int highlightValue) {
        if (root == null) return;

        float x = startX + treeWidth / 2;
        float y = startY;

        // Calculate widths of left and right subtrees
        float leftTreeWidth = getTreeWidth(root.left) * HORIZONTAL_SPACING;
        float rightTreeWidth = getTreeWidth(root.right) * HORIZONTAL_SPACING;

        // Adjust positions based on the presence of children
        float leftX = startX + leftTreeWidth / 2;
        float rightX = startX + treeWidth - rightTreeWidth / 2;

        // only one child exists
        if (root.left != null && root.right == null) {
            leftX = x - HORIZONTAL_SPACING / 2;
        } else if (root.right != null && root.left == null) {
            rightX = x + HORIZONTAL_SPACING / 2;
        }

        // Left Child
        if (root.left != null) {
            float leftY = y + VERTICAL_SPACING;
            canvas.drawLine(x, y + NODE_RADIUS, leftX, leftY - NODE_RADIUS, linePaint);
            drawNode(canvas, root.left, leftX, leftY, highlightValue);
            drawConnections(canvas, root.left, startX, leftY, leftTreeWidth, highlightValue);
        }

        // Right Child
        if (root.right != null) {
            float rightY = y + VERTICAL_SPACING;
            canvas.drawLine(x, y + NODE_RADIUS, rightX, rightY - NODE_RADIUS, linePaint);
            drawNode(canvas, root.right, rightX, rightY, highlightValue);
            drawConnections(canvas, root.right, startX + treeWidth - rightTreeWidth, rightY, rightTreeWidth, highlightValue);
        }
    }

    public float getTreeWidth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        float leftWidth = getTreeWidth(root.left);
        float rightWidth = getTreeWidth(root.right);
        //  minimum width for a single node
        if (leftWidth == 0 && rightWidth == 0) {
            return 1;
        }
        return leftWidth + rightWidth + 1;
    }

    public float getTreeHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(getTreeHeight(root.left), getTreeHeight(root.right)) * VERTICAL_SPACING;
    }
}