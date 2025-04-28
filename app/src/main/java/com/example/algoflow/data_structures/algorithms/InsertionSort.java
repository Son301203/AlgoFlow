package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.Sorting;
import com.example.algoflow.algorithm_views.SortView;

public class InsertionSort implements Sorting {
    @Override
    public void sort(int[] array, SortView view) {
        for(int i = 1; i < array.length && view.isSorting(); i++) {
            int key = array[i];
            int j = i - 1;
            view.setCurrentIndex(i);
            view.setCompareIndex(j);

            while (j >= 0 && array[j] > key && view.isSorting()){
                view.checkPause();
                if(!view.isSorting()) break;

                view.setCompareIndex(j);
                view.postInvalidate();

                try {
                    Thread.sleep(ANIMATION_DELAY);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    view.setSorting(false);
                    break;
                }

                view.getAnimationManager().animateShift(j, j + 1);
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
            view.setCurrentIndex(j + 1);
            view.postInvalidate();
            try {
                Thread.sleep(ANIMATION_DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                view.setSorting(false);
                break;
            }
        }

        view.setCurrentIndex(-1);
        view.setCompareIndex(-1);
        view.setSorting(false);
        view.postInvalidate();
    }
}
