package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.algorithm_views.RecursionSortView;
import com.example.algoflow.data_structures.interfaces.RecursionSorting;

public class QuickSort implements RecursionSorting {
    @Override
    public void sort(int[] array, RecursionSortView view) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] arr, int left, int right){
        if(left >= right) return;

        int key = arr[(left + right) / 2];
        int pivot = partition(arr, left, right, key);

        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot, right);
    }

    private int partition(int[] arr, int left, int right, int key){
        int iL = left;
        int iR = right;

        while (iL < iR){
            while(arr[iL] < key) iL++;
            while(arr[iR] > key) iR--;
            if(iL <= iR){
                int temp = arr[iL];
                arr[iL] = arr[iR];
                arr[iR] = temp;
                iL++; iR--;
            }
        }

        return iL;

    }
}
