package com.example.algoflow.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.algoflow.R;
import com.example.algoflow.activities.AlgorithmDescriptionActivity;
import com.example.algoflow.activities.BSTActivity;
import com.example.algoflow.activities.BubbleSortVisualizationActivity;
import com.example.algoflow.activities.HashMapActivity;
import com.example.algoflow.activities.HashSetActivity;
import com.example.algoflow.activities.InsertionSortVisualizationActivity;
import com.example.algoflow.activities.LinkedListActivity;
import com.example.algoflow.activities.MergeSortActivity;
import com.example.algoflow.activities.QueueWithArrayActivity;
import com.example.algoflow.activities.QueueWithLinkedListActivity;
import com.example.algoflow.activities.SelectionSortVisualizationActivity;
import com.example.algoflow.activities.StackWithArrayActivity;
import com.example.algoflow.activities.StackWithLinkedListActivity;
import com.example.algoflow.activities.TopicDescriptionActivity;
import com.example.algoflow.models.Algorithm;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AlgorithmAdapter extends ArrayAdapter<Algorithm> {
    private int idLayout;
    private Activity context;
    private ArrayList<Algorithm> algorithmArrayList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public AlgorithmAdapter(Activity context, int idLayout, ArrayList<Algorithm> algorithmArrayList) {
        super(context, idLayout, algorithmArrayList);
        this.context = context;
        this.idLayout = idLayout;
        this.algorithmArrayList = algorithmArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(idLayout, parent, false);
        }
        Algorithm algorithm = algorithmArrayList.get(position);

        TextView name = convertView.findViewById(R.id.algorithm_name);
        name.setText(algorithm.getName());

        ImageView imageNote = convertView.findViewById(R.id.note_image_algorithm);
        imageNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlgorithmDescriptionActivity.class);
                intent.putExtra("algorithmId", algorithm.getId());
                context.startActivity(intent);
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String algorithmId = algorithm.getId();
                db.collection("Algorithms")
                        .document(algorithmId)
                        .get()
                        .addOnSuccessListener(task -> {
                            String visualizationPath = task.getString("visualizationPath");
                            switch (visualizationPath){
                                case "bubble_sort":
                                    Intent intentBubbleSort = new Intent(context, BubbleSortVisualizationActivity.class);
                                    context.startActivity(intentBubbleSort);
                                    break;
                                case "insertion_sort":
                                    Intent intentInsertionSort = new Intent(context, InsertionSortVisualizationActivity.class);
                                    context.startActivity(intentInsertionSort);
                                    break;
                                case "selection_sort":
                                    Intent intentSelectionSort = new Intent(context, SelectionSortVisualizationActivity.class);
                                    context.startActivity(intentSelectionSort);
                                    break;
                                case "merge_sort":
                                    Intent megerSort = new Intent(context, MergeSortActivity.class);
                                    context.startActivity(megerSort);
                                    break;
                                case "stack_with_array":
                                    Intent stackWithArray = new Intent(context, StackWithArrayActivity.class);
                                    context.startActivity(stackWithArray);
                                    break;
                                case "queue_with_array":
                                    Intent queueWithArray = new Intent(context, QueueWithArrayActivity.class);
                                    context.startActivity(queueWithArray);
                                    break;
                                case "linked_list":
                                    Intent linkedList = new Intent(context, LinkedListActivity.class);
                                    context.startActivity(linkedList);
                                    break;
                                case "stack_with_linked_list":
                                    Intent stackWithLinkedList = new Intent(context, StackWithLinkedListActivity.class);
                                    context.startActivity(stackWithLinkedList);
                                    break;
                                case "queue_with_linked_list":
                                    Intent queueWithLinkedList = new Intent(context, QueueWithLinkedListActivity.class);
                                    context.startActivity(queueWithLinkedList);
                                    break;
                                case "binary_search_tree":
                                    Intent binarySearchTree = new Intent(context, BSTActivity.class);
                                    context.startActivity(binarySearchTree);
                                    break;
                                case "hash_set":
                                    Intent hashSet = new Intent(context, HashSetActivity.class);
                                    context.startActivity(hashSet);
                                    break;
                                case "hash_map":
                                    Intent hashMap = new Intent(context, HashMapActivity.class);
                                    context.startActivity(hashMap);
                                    break;
                            }
                        });
            }
        });

        return convertView;
    }
}
