package com.example.algoflow.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.algoflow.R;
import com.example.algoflow.utils.SortView;
import com.example.algoflow.data_structures.algorithms.BubbleSort;

import com.example.algoflow.R;

public class BubbleSortVisualizationActivity extends AppCompatActivity {
    private SortView sortView;
    private EditText numsOfArray;
    private Button randomButton, startButton, pauseButton, continueButton, resetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bubble_sort_visualization);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sortView = findViewById(R.id.sortView);
        numsOfArray = findViewById(R.id.elementCountInput);
        randomButton = findViewById(R.id.randomButton);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        continueButton = findViewById(R.id.continueButton);
        resetButton = findViewById(R.id.resetButton);

        sortView.setAlgorithms(new BubbleSort());

        randomButton.setOnClickListener(v -> randomNums(numsOfArray));

        startButton.setOnClickListener(v -> sortView.startSorting());

        pauseButton.setOnClickListener(v -> sortView.pauseSorting());

        continueButton.setOnClickListener(v -> sortView.resumeSorting());

        resetButton.setOnClickListener(v -> sortView.reset());
    }

    private void randomNums(EditText numsOfArray) {
        String input = numsOfArray.getText().toString();
        if(!input.isEmpty()){
            try{
                int size = Integer.parseInt(input);
                if(size > 0 && size <= 20){
                    sortView.setArraySize(size);
                    sortView.randomize();
                }else{
                    Toast.makeText(this, "Please enter a number from range 1 to 20", Toast.LENGTH_SHORT).show();
                }
            }catch (NumberFormatException e){
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Please enter number of elements", Toast.LENGTH_SHORT).show();
        }
    }
}