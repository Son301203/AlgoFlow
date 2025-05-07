package com.example.algoflow.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.algoflow.R;
import com.example.algoflow.algorithm_views.QueueWithLinkedListView;

public class QueueWithLinkedListActivity extends AppCompatActivity {
    private QueueWithLinkedListView queueView;
    private EditText valueInput;
    private Button enqueueButton, dequeueButton, randomButton, clearButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_with_linked_list);

        queueView = findViewById(R.id.queueWithLinkedListView);
        valueInput = findViewById(R.id.valueInput);
        enqueueButton = findViewById(R.id.enqueueButton);
        dequeueButton = findViewById(R.id.dequeueButton);
        randomButton = findViewById(R.id.randomButton);
        clearButton = findViewById(R.id.clearButton);

        enqueueButton.setOnClickListener(v -> enqueue(valueInput));

        dequeueButton.setOnClickListener(v -> dequeue());

        randomButton.setOnClickListener(v -> random());

        clearButton.setOnClickListener(v -> clear());
    }
    private void random(){
        queueView.random();
    }

    private void clear(){
        queueView.clear();
        Toast toast = Toast.makeText(this, "Queue cleared!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    private void dequeue() {
        if (!queueView.isEmpty()) {
            int value = queueView.dequeue();
            Toast toast = Toast.makeText(this, "Dequeue: " + value, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "Queue is Empty!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }

    private void enqueue(EditText valueInput) {
        String valueStr = valueInput.getText().toString();
        if (!valueStr.isEmpty()) {
            try {
                int value = Integer.parseInt(valueStr);
                if (!queueView.isFull()) {
                    queueView.enqueue(value);
                    valueInput.setText("");
                } else {
                    Toast toast = Toast.makeText(this, "Queue Full! The maximum is 10", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
            } catch (NumberFormatException e) {
                Toast toast = Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }
}