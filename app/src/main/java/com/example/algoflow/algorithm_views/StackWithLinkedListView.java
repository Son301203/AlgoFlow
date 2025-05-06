package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.algoflow.data_structures.algorithms.StackWithLinkedList;
import com.example.algoflow.models.ListNode;
import com.example.algoflow.utils.LinkedListVisualizer;

public class StackWithLinkedListView extends View {
    private StackWithLinkedList stack;
    private LinkedListVisualizer visualizer;
    private int highlightIndex = -1;

    public StackWithLinkedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        stack = new StackWithLinkedList();
        visualizer = new LinkedListVisualizer();
    }

    public void push(int value){
        if(!stack.isFull()){
            stack.push(value);
            highlightIndex = 0;
            invalidate();
        }
    }

    public int pop(){
        highlightIndex = 0;
        int value = stack.pop();
        invalidate();

        return value;
    }

    public int peek(){
        int value = stack.peek();
        return value;
    }

    public void clear(){
        stack.clear();
        highlightIndex = -1;
        invalidate();
    }

    public void random(){
        stack.random();
        ListNode temp = stack.getHead();
        while (temp != null) {
            temp = temp.next;
        }
        highlightIndex = 0;
        invalidate();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public boolean isFull() {
        return stack.isFull();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        ListNode head = stack.getHead();
        float totalWidth = visualizer.getTotalWidth(head);
        float totalHeight = visualizer.getTotalHeight();
        float startX = (getWidth() - totalWidth) / 2;
        float startY = getHeight() / 2;
        visualizer.drawLinkedList(canvas, head, highlightIndex, startX, startY, "Head/Top", "Tail");
    }
}
