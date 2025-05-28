package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.algorithm_views.RecursionSortView;
import com.example.algoflow.data_structures.interfaces.RecursionSorting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergeSort implements RecursionSorting {
    private static final float LEVEL_OFFSET = 60f;

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

        // Show current subarray being processed
        Map<Integer, Float> offsets = new HashMap<>();
        for (int i = start; i <= end; i++) {
            offsets.put(i, level * LEVEL_OFFSET);
        }
        view.updateStepWithOffsets(arr, new ArrayList<>(), new ArrayList<>(), level, offsets);
        view.checkPause();

        int mid = start + (end - start) / 2;

        // Recursively sort left half
        mergeSort(arr, start, mid, level + 1, view);

        // Recursively sort right half
        mergeSort(arr, mid + 1, end, level + 1, view);

        // Merge the sorted halves
        merge(arr, start, mid, end, level, view);

        // After merge, show elements back at current level
        offsets.clear();
        for (int i = start; i <= end; i++) {
            offsets.put(i, level * LEVEL_OFFSET);
        }
        List<Integer> sortedIndices = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            sortedIndices.add(i);
        }
        view.updateStepWithOffsets(arr, new ArrayList<>(), sortedIndices, level, offsets);
        view.checkPause();

        // If we're at the root level, move everything back to original position
        if (level == 0) {
            view.updateStepWithOffsets(arr, new ArrayList<>(), sortedIndices, 0, new HashMap<>());
            view.checkPause();
        }
    }

    private void merge(int[] arr, int start, int mid, int end, int level, RecursionSortView view) {
        int[] left = new int[mid - start + 1];
        int[] right = new int[end - mid];

        // Copy data to temp arrays
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[start + i];
        }
        for (int j = 0; j < right.length; j++) {
            right[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = start;
        Map<Integer, Float> offsets = new HashMap<>();
        List<Integer> sortedIndices = new ArrayList<>();

        // Show merge process
        while (i < left.length && j < right.length) {
            // Highlight elements being compared
            offsets.clear();
            for (int idx = start; idx <= end; idx++) {
                offsets.put(idx, level * LEVEL_OFFSET);
            }

            List<Integer> comparing = new ArrayList<>();
            comparing.add(k);
            view.updateStepWithOffsets(arr, comparing, sortedIndices, level, offsets);
            view.checkPause();

            if (left[i] <= right[j]) {
                arr[k] = left[i++];
            } else {
                arr[k] = right[j++];
            }

            sortedIndices.add(k);
            view.updateStepWithOffsets(arr, new ArrayList<>(), sortedIndices, level, offsets);
            view.checkPause();
            k++;
        }

        // Copy remaining elements
        while (i < left.length) {
            offsets.clear();
            for (int idx = start; idx <= end; idx++) {
                offsets.put(idx, level * LEVEL_OFFSET);
            }

            arr[k] = left[i++];
            sortedIndices.add(k);
            view.updateStepWithOffsets(arr, new ArrayList<>(), sortedIndices, level, offsets);
            view.checkPause();
            k++;
        }

        while (j < right.length) {
            offsets.clear();
            for (int idx = start; idx <= end; idx++) {
                offsets.put(idx, level * LEVEL_OFFSET);
            }

            arr[k] = right[j++];
            sortedIndices.add(k);
            view.updateStepWithOffsets(arr, new ArrayList<>(), sortedIndices, level, offsets);
            view.checkPause();
            k++;
        }
    }
}
