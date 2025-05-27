package com.example.algoflow.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.algoflow.R;
import com.example.algoflow.algorithm_views.RecursionSortView;
import com.example.algoflow.data_structures.algorithms.MergeSort;

public class MergeSortActivity extends AppCompatActivity {
    private RecursionSortView recursionSortView;
    private EditText numsOfArray;
    private Button randomButton, startButton, pauseButton, continueButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_merge_sort);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.merge_sort_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recursionSortView = findViewById(R.id.recursionSortView);
        numsOfArray = findViewById(R.id.elementCountInput);
        randomButton = findViewById(R.id.randomButton);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        continueButton = findViewById(R.id.continueButton);
        resetButton = findViewById(R.id.resetButton);

        recursionSortView.setAlgorithms(new MergeSort());


        randomButton.setOnClickListener(v -> randomNums(numsOfArray));

        startButton.setOnClickListener(v -> recursionSortView.startSorting());

        pauseButton.setOnClickListener(v -> recursionSortView.pauseSorting());

        continueButton.setOnClickListener(v -> recursionSortView.resumeSorting());

        resetButton.setOnClickListener(v -> recursionSortView.reset());
    }

    private void randomNums(EditText numsOfArray) {
        String input = numsOfArray.getText().toString();
        if(!input.isEmpty()){
            try{
                int size = Integer.parseInt(input);
                if(size > 0 && size <= 10){
                    recursionSortView.setArraySize(size);
                    recursionSortView.randomize();
                }else{
                    Toast.makeText(this, "Please enter a number from range 1 to 10", Toast.LENGTH_SHORT).show();
                }
            }catch (NumberFormatException e){
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Please enter number of elements", Toast.LENGTH_SHORT).show();
        }
    }
}