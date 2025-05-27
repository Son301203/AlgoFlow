package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.example.algoflow.data_structures.algorithms.BinarySearchTree;
import com.example.algoflow.models.TreeNode;
import com.example.algoflow.visualizer.TreeVisualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BSTView extends View {
    private BinarySearchTree bst;
    private TreeVisualizer visualizer;
    private List<Integer> highlightValues = new ArrayList<>();
    private String traversalType = "";
    private Consumer<String> traversalUpdateListener;

    public BSTView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bst = new BinarySearchTree();
        visualizer = new TreeVisualizer();

        bst.setTraversalListener(new BinarySearchTree.OnTraversalStepListener() {
            @Override
            public void onStep(int value) {
                highlightValues.add(value);
                invalidate();
                if (traversalUpdateListener != null) {
                    String result = traversalType + ": " + bst.listOrderTraversal.stream()
                            .map(String::valueOf)
                            .reduce((a, b) -> a + " -> " + b)
                            .orElse("");
                    traversalUpdateListener.accept(result);
                }
            }

            @Override
            public void onTraversalComplete() {

            }
        });
    }

    public void setTraversalUpdateListener(Consumer<String> listener) {
        this.traversalUpdateListener = listener;
    }

    public void setTraversalType(String type) {
        this.traversalType = type;
    }

    public void insert(int value) {
        highlightValues.clear();
        bst.insert(value);
        highlightValues.add(value);
        invalidate();
    }

    public void delete(int value) {
        highlightValues.clear();
        bst.delete(value);
        highlightValues.add(value);
        invalidate();
    }

    public boolean search(int value) {
        TreeNode result = bst.search(value);
        if (result != null) {
            highlightValues.clear();
            highlightValues.add(value);
            invalidate();
            return true;
        }
        return false;
    }

    public void random() {
        highlightValues.clear();
        bst.random();
        invalidate();
    }

    public void clear() {
        highlightValues.clear();
        bst.clear();
        invalidate();
        if (traversalUpdateListener != null) {
            traversalUpdateListener.accept("");
        }
    }

    public boolean isEmpty() {
        return bst.mRoot == null;
    }

    public void preOrderTraversal() {
        highlightValues.clear();
        bst.cancelTraversal();
        setTraversalType("Pre-order");
        bst.preOrderTraversal();
    }

    public void inOrderTraversal() {
        highlightValues.clear();
        bst.cancelTraversal();
        setTraversalType("In-order");
        bst.inOrderTraversal();
    }

    public void postOrderTraversal() {
        highlightValues.clear();
        bst.cancelTraversal();
        setTraversalType("Post-order");
        bst.postOrderTraversal();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        TreeNode root = bst.mRoot;
        if (root != null) {
            float totalWidth = visualizer.getTreeWidth(root) * 60f;
            float startX = (getWidth() - totalWidth) / 2;
            float startY = 50f;
            visualizer.drawTree(canvas, root, startX, startY, highlightValues);
        }
    }
}