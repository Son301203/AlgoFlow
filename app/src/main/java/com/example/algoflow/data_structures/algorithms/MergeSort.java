package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.algorithm_views.RecursionSortView;
import com.example.algoflow.data_structures.interfaces.RecursionSorting;

import java.util.ArrayList;
import java.util.List;

public class MergeSort implements RecursionSorting {

    @Override
    public void sort(int[] array, RecursionSortView view) {
        view.startSort(0);
        mergeSort(array, 0, array.length - 1, 0, view);
        view.finishSort(array);
        view.setSorting(false);
    }

    private void mergeSort(int[] arr, int start, int end, int level, RecursionSortView view) {
        if (start >= end) {
            return;
        }

        int mid = start + (end - start) / 2;
        mergeSort(arr, start, mid, level + 1, view);
        mergeSort(arr, mid + 1, end, level + 1, view);
        merge(arr, start, mid, end, level, view);
    }

    private int[] splitArr(int[] arr, int i, int size) {
        int[] result = new int[size];
        for (int j = 0; j < size; j++) {
            result[j] = arr[i + j];
        }
        return result;
    }

    private void merge(int[] arr, int start, int mid, int end, int level, RecursionSortView view) {
        int[] left = splitArr(arr, start, mid - start + 1);
        int[] right = splitArr(arr, mid + 1, end - mid);

        int i = 0, j = 0, k = start;
        List<Integer> comparingIndices = new ArrayList<>();
        List<Integer> sortedIndices = new ArrayList<>();

        while (i < left.length && j < right.length) {
            comparingIndices.clear();
            comparingIndices.add(start + i);
            comparingIndices.add(mid + 1 + j);
            view.updateStep(arr.clone(), comparingIndices, sortedIndices, level);
            view.checkPause();

            if (left[i] <= right[j]) {
                arr[k] = left[i++];
            } else {
                arr[k] = right[j++];
            }
            comparingIndices.clear();
            sortedIndices.add(k);
            view.updateStep(arr.clone(), comparingIndices, sortedIndices, level);
            view.checkPause();
            k++;
        }

        while (i < left.length) {
            comparingIndices.clear();
            comparingIndices.add(start + i);
            view.updateStep(arr.clone(), comparingIndices, sortedIndices, level);
            view.checkPause();
            arr[k] = left[i++];
            comparingIndices.clear();
            sortedIndices.add(k);
            view.updateStep(arr.clone(), comparingIndices, sortedIndices, level);
            view.checkPause();
            k++;
        }

        while (j < right.length) {
            comparingIndices.clear();
            comparingIndices.add(mid + 1 + j);
            view.updateStep(arr.clone(), comparingIndices, sortedIndices, level);
            view.checkPause();
            arr[k] = right[j++];
            comparingIndices.clear();
            sortedIndices.add(k);
            view.updateStep(arr.clone(), comparingIndices, sortedIndices, level);
            view.checkPause();
            k++;
        }
    }
}