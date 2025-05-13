package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.example.algoflow.data_structures.algorithms.BinarySearchTree;
import com.example.algoflow.models.TreeNode;
import com.example.algoflow.utils.TreeVisualizer;

public class BSTView extends View {
    private BinarySearchTree bst;
    private TreeVisualizer visualizer;
    private int highlightValue = -1;

    public BSTView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bst = new BinarySearchTree();
        visualizer = new TreeVisualizer();
    }

    public void insert(int value) {
        bst.insert(value);
        highlightValue = value;
        invalidate();
    }

    public void delete(int value) {
        bst.delete(value);
        highlightValue = value;
        invalidate();
    }

    public boolean search(int value) {
        TreeNode result = bst.search(value);
        if (result != null) {
            highlightValue = value;
            invalidate();
            return true;
        }
        return false;
    }

    public void random() {
        bst.random();
        invalidate();
    }

    public void clear() {
        bst.clear();
        highlightValue = -1;
        invalidate();
    }

    public boolean isEmpty() {
        return bst.mRoot == null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        TreeNode root = bst.mRoot;
        if (root != null) {
            float totalWidth = visualizer.getTreeWidth(root) * 60f;
            float startX = (getWidth() - totalWidth) / 2;
            float startY = 50f;
            visualizer.drawTree(canvas, root, startX, startY, highlightValue);
        }
    }
}