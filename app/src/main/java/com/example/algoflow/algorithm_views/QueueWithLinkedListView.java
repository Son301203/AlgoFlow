package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.algoflow.data_structures.algorithms.QueueWithLinkedList;
import com.example.algoflow.models.ListNode;
import com.example.algoflow.visualizer.LinkedListVisualizer;

public class QueueWithLinkedListView extends View {
    private QueueWithLinkedList queue;
    private LinkedListVisualizer visualizer;
    private int highlightIndex = -1;

    public QueueWithLinkedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        queue = new QueueWithLinkedList();
        visualizer = new LinkedListVisualizer();
    }

    public void enqueue(int value){
        if(!queue.isFull()){
            queue.push(value);
            int count = 0;
            ListNode temp = queue.getHead();
            while (temp != null){
                temp = temp.next;
                count++;
            }
            highlightIndex = count - 1;
            invalidate();
        }
    }

    public int dequeue(){
        if(!queue.isEmpty()){
            highlightIndex = 0;
            int value = queue.pop();
            invalidate();
            return value;
        }
        return -1;
    }

    public void clear(){
        queue.clear();
        highlightIndex = -1;
        invalidate();
    }

    public void random(){
        queue.random();
        int count = 0;
        ListNode temp = queue.getHead();
        while (temp != null){
            temp = temp.next;
            count++;
        }
        highlightIndex = count - 1;
        invalidate();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public boolean isFull(){
        return queue.isFull();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        ListNode head = queue.getHead();
        float totalWidth = visualizer.getTotalWidth(head);
        float totalHeight = visualizer.getTotalHeight();
        float startX = (getWidth() - totalWidth) / 2;
        float startY = getHeight() / 2;
        visualizer.drawLinkedList(canvas, head, highlightIndex, startX, startY, "Head", "Tail");
    }
}
