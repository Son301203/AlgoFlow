package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.algorithm_views.SortView;
import com.example.algoflow.data_structures.interfaces.Sorting;

public class MergeSort implements Sorting {

    @Override
    public void sort(int[] array, SortView view) {
        mergeSort(array, array.length);
    }

    private int[] mergeSort(int[] arr, int length){
        if(length == 1)
            return arr;

        int[] left = splitArr(arr, 0, length / 2);
        int[] right = splitArr(arr, length / 2, length - length /2);

        left = mergeSort(left, left.length);
        right = mergeSort(right, right.length);

        return merge(left, right);
    }

    private int[] merge(int[] left, int[] right){
        int length = left.length + right.length;
        int[] result = new int[length];
        int i = 0;
        int j = 0;
        int count = 0;

        while (count < length){
            if(i >= left.length){
                result[count++] = right[j++];
                continue;
            }
            if(j >= right.length){
                result[count++] = left[i++];
                continue;
            }

            if(left[i] < right[j]){
                result[count++] = left[i++];
            }
            else{
                result[count++] = right[j++];
            }
        }
        return result;
    }


    private int[] splitArr(int[] arr, int i, int size){
        int[] result = new int[size];
        for (int j = 0; j < size; j++){
            result[j] = arr[i + j];
        }
        return result;
    }
}
