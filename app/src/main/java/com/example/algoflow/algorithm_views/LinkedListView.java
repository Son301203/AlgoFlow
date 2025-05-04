package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.example.algoflow.data_structures.algorithms.LinkedList;
import com.example.algoflow.models.ListNode;
import com.example.algoflow.utils.LinkedListVisualizer;

public class LinkedListView extends View {
    private LinkedList linkedList = new LinkedList();
    private ListNode head;
    private LinkedListVisualizer visualizer;
    private int highlightIndex = -1;
    private static final int ANIMATION_DURATION = 500;

    public LinkedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        head = null;
        visualizer = new LinkedListVisualizer();
    }

    public ListNode getHead() {
        return head;
    }

    // Highlight new head
    public void addToHead(int value) {
        head = linkedList.addToHead(head, value);
        highlightIndex = 0;
        invalidate();
        postDelayed(() -> {
            highlightIndex = -1;
            invalidate();
        }, ANIMATION_DURATION);
    }

    // find tail index and Highlight new tail
    public void addToTail(int value) {
        head = linkedList.addToTail(head, value);
        int count = 0;
        ListNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        highlightIndex = count - 1;
        invalidate();
        postDelayed(() -> {
            highlightIndex = -1;
            invalidate();
        }, ANIMATION_DURATION);
    }

    public void addToIndex(int index, int value) {
        head = linkedList.addToIndex(head, index, value);
        highlightIndex = index;
        invalidate();
        postDelayed(() -> {
            highlightIndex = -1;
            invalidate();
        }, ANIMATION_DURATION);
    }

    // Highlight head before removing
    public void removeHead() {
        if (head != null) {
            highlightIndex = 0;
            head = linkedList.removeHead(head);
            invalidate();
            postDelayed(() -> {
                highlightIndex = -1;
                invalidate();
            }, ANIMATION_DURATION);
        }
    }

    // find tail index and Highlight tail
    public void removeTail() {
        if (head != null) {
            int count = 0;
            ListNode temp = head;
            while (temp != null) {
                count++;
                temp = temp.next;
            }
            highlightIndex = count - 1;
            head = linkedList.removeTail(head);
            invalidate();
            postDelayed(() -> {
                highlightIndex = -1;
                invalidate();
            }, ANIMATION_DURATION);
        }
    }

    public void removeAtIndex(int index) {
        if (head != null) {
            highlightIndex = index;
            head = linkedList.removeAtIndex(head, index);
            invalidate();
            postDelayed(() -> {
                highlightIndex = -1;
                invalidate();
            }, ANIMATION_DURATION);
        }
    }

    // Find index matching node
    public boolean searchNodeValue(int value) {
        boolean found = linkedList.searchNodeValue(head, value);
        if (found) {
            int index = 0;
            ListNode temp = head;
            while (temp != null) {
                if (temp.value == value) {
                    highlightIndex = index;
                    invalidate();
                    postDelayed(() -> {
                        highlightIndex = -1;
                        invalidate();
                    }, ANIMATION_DURATION);
                    break;
                }
                index++;
                temp = temp.next;
            }
        }
        return found;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float totalWidth = visualizer.getTotalWidth(head);
        float totalHeight = visualizer.getTotalHeight();
        float startX = (getWidth() - totalWidth) / 2;
        float startY = getHeight() / 2;
        visualizer.drawLinkedList(canvas, head, highlightIndex, startX, startY, "Head", "");
    }
}