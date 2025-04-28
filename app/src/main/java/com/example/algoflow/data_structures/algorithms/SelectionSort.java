package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.data_structures.interfaces.Sorting;
import com.example.algoflow.algorithm_views.SortView;

public class SelectionSort implements Sorting {
    @Override
    public void sort(int[] array, SortView view) {
        for(int i = 0; i < array.length; i++){
            int min = i;
            view.setCurrentIndex(i);
            for(int j = i + 1; j < array.length; j++){
                view.checkPause();
                if(!view.isSorting()) break;
                view.setCompareIndex(j);
                view.setMinIndex(min);

                if(array[j] < array[min]){
                    min = j;
                }
                view.postInvalidate();
                try {
                    Thread.sleep(ANIMATION_DELAY);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    view.setSorting(false);
                    break;
                }
            }
            if(min != i){
                view.getAnimationManager().animateSwap(i, min);
            }
            if(!view.isSorting()) break;

        }

        view.setCompareIndex(-1);
        view.setCurrentIndex(-1);
        view.setMinIndex(-1);
        view.setSorting(false);
        view.postInvalidate();
    }
}
