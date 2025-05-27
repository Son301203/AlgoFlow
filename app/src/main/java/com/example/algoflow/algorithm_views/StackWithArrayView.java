package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.algoflow.data_structures.algorithms.StackWithArray;
import com.example.algoflow.visualizer.ArrayVisualizer;

public class StackWithArrayView extends View {
    private StackWithArray stack;
    private ArrayVisualizer visualizer;
    private int highlightIndex = -1;
    private static final int ANIMATION_DURATION = 500;

    public StackWithArrayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        stack = new StackWithArray(6);
        visualizer = new ArrayVisualizer();
    }

    public StackWithArray getStack() {
        return stack;
    }

    public void push(int value) {
        if (stack.push(value)) {
            highlightIndex = stack.getTopIndex();
            invalidate();
        } else {
            Toast toast = Toast.makeText(getContext(), "Stack full!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }

    public void pop() {
        if (!stack.isEmpty()) {
            int value = stack.pop();
            highlightIndex = stack.getTopIndex();
            invalidate();
            Toast toast = Toast.makeText(getContext(), "Pop: " + value, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getContext(), "Stack is Empty!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }

    public void peek(){
        int value = stack.peek();
        if (value != -1) {
            Toast toast = Toast.makeText(getContext(),"Peek: " + value, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getContext(), "Stack is Empty!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }


    public void clear() {
        while (!stack.isEmpty()) {
            stack.pop();
        }
        highlightIndex = -1;
        invalidate();
        Toast toast = Toast.makeText(getContext(), "Stack cleared!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int[] array = stack.getArray();
        int topIndex = stack.getTopIndex();
        float totalWidth = visualizer.getTotalWidth(array.length);
        float totalHeight = visualizer.getTotalHeight();
        float xOffset = (getWidth() - totalWidth) / 2;
        float yOffset = (getHeight() - totalHeight) / 2;
        visualizer.drawStackArray(canvas, array, highlightIndex, xOffset, yOffset);
        visualizer.drawTopPointer(canvas, topIndex, xOffset, yOffset);
    }
}