package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.algorithm_views.RecursionSortView;
import com.example.algoflow.data_structures.interfaces.RecursionSorting;

import java.util.ArrayList;
import java.util.List;

public class MergeSort implements RecursionSorting {

    @Override
    public void sort(int[] array, RecursionSortView view) {
        view.startSorting();
        mergeSort(array, 0, array.length - 1, view);
        view.finishSort(array);
    }

    private void mergeSort(int[] arr, int start, int end, RecursionSortView view) {
        if (start >= end) {
            return;
        }

        int mid = start + (end - start) / 2;
        mergeSort(arr, start, mid, view);
        mergeSort(arr, mid + 1, end, view);
        merge(arr, start, mid, end, view);
    }

    private void merge(int[] arr, int start, int mid, int end, RecursionSortView view) {
        int[] left = new int[mid - start + 1];
        int[] right = new int[end - mid];

        // Copy data to temporary arrays
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[start + i];
        }
        for (int j = 0; j < right.length; j++) {
            right[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = start;
        List<Integer> highlights = new ArrayList<>();

        while (i < left.length && j < right.length) {
            highlights.clear();
            highlights.add(k); // Highlight
            view.updateStep(arr.clone(), highlights);
            view.checkPause();

            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        while (i < left.length) {
            highlights.clear();
            highlights.add(k);
            view.updateStep(arr.clone(), highlights);
            view.checkPause();
            arr[k++] = left[i++];
        }

        while (j < right.length) {
            highlights.clear();
            highlights.add(k);
            view.updateStep(arr.clone(), highlights);
            view.checkPause();
            arr[k++] = right[j++];
        }

        view.updateStep(arr.clone(), new ArrayList<>());
    }
}