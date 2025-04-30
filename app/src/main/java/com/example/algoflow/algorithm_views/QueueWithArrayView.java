package com.example.algoflow.algorithm_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.algoflow.data_structures.algorithms.QueueWithArray;
import com.example.algoflow.utils.ArrayVisualizer;

public class QueueWithArrayView extends View {
    private QueueWithArray queue;
    private ArrayVisualizer visualizer;
    private int highlightIndex = -1;

    public QueueWithArrayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        queue = new QueueWithArray(6); // Default size 6
        visualizer = new ArrayVisualizer();
    }

    public QueueWithArray getQueue() {
        return queue;
    }

    public void enqueue(int value) {
        if (queue.push(value)) {
            highlightIndex = queue.getTailIndex();
            invalidate();
        } else {
            Toast toast = Toast.makeText(getContext(), "Queue full!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }

    public void dequeue() {
        if (!queue.isEmpty()) {
            int value = queue.pop();
            highlightIndex = queue.getHeadIndex() - 1;
            invalidate();
            Toast toast = Toast.makeText(getContext(), "Dequeue: " + value, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getContext(), "Queue is empty!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }

    public void clear() {
        while (!queue.isEmpty()) {
            queue.pop();
        }
        highlightIndex = -1;
        invalidate();
        Toast toast = Toast.makeText(getContext(), "Queue cleared!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        int[] array = queue.getArray();
        int front = queue.getHeadIndex();
        int rear = queue.getTailIndex();
        float totalWidth = visualizer.getTotalWidth(array.length);
        float totalHeight = visualizer.getTotalHeight();
        float xOffset = (getWidth() - totalWidth) / 2;
        float yOffset = (getHeight() - totalHeight) / 2;
        visualizer.drawQueueArray(canvas, array, front, rear, highlightIndex, xOffset, yOffset);
        visualizer.drawQueuePointers(canvas, front, rear, xOffset, yOffset);
    }
}