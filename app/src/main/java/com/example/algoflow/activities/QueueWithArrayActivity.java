package com.example.algoflow.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.algoflow.R;
import com.example.algoflow.algorithm_views.QueueWithArrayView;

public class QueueWithArrayActivity extends AppCompatActivity {
    private QueueWithArrayView queueWithArrayView;
    private EditText valueInput;
    private Button enqueueButton, dequeueButton, clearButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_queue_with_array);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        queueWithArrayView = findViewById(R.id.queueWithArrayView);
        valueInput = findViewById(R.id.valueInput);
        enqueueButton = findViewById(R.id.enqueueButton);
        dequeueButton = findViewById(R.id.dequeueButton);
        clearButton = findViewById(R.id.clearButton);

        enqueueButton.setOnClickListener(v -> enqueue(valueInput));

        dequeueButton.setOnClickListener(v -> dequeue());

        clearButton.setOnClickListener(v -> clear());
    }

    private void clear(){
        queueWithArrayView.clear();
    }

    private void dequeue() {
        queueWithArrayView.dequeue();
    }

    private void enqueue(EditText valueInput) {
        String input = valueInput.getText().toString();
        if(!input.isEmpty()){
            try {
                int value = Integer.parseInt(input);
                queueWithArrayView.enqueue(value);
                valueInput.setText("");
            }catch (NumberFormatException e){
                Toast toast = Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        }else {
            Toast toast = Toast.makeText(this, "Please enter value", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }


}