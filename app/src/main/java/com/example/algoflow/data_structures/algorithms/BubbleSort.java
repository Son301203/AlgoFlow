package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.Sorting;
import com.example.algoflow.algorithm_views.SortView;

public class BubbleSort implements Sorting {
    @Override
    public void sort(int[] array, SortView view) {
        for (int i = 0; i < array.length - 1 && view.isSorting(); i++) {
            for (int j = 0; j < array.length - i - 1 && view.isSorting(); j++) {
                view.checkPause();
                if (!view.isSorting()) break;

                view.setCurrentIndex(j);
                view.setCompareIndex(j + 1);
                if (array[j] > array[j + 1]) {
                    view.getAnimationManager().animateSwap(j, j + 1);
                } else {
                    view.postInvalidate();
                    try {
                        Thread.sleep(ANIMATION_DELAY);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        view.setSorting(false);
                        break;
                    }
                }
            }
            if (!view.isSorting()) break;
        }
        view.setCurrentIndex(-1);
        view.setCompareIndex(-1);
        view.setSorting(false);
        view.postInvalidate();
    }
}