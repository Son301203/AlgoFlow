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
import com.example.algoflow.algorithm_views.StackWithArrayView;

public class StackWithArrayActivity extends AppCompatActivity {
    private StackWithArrayView stackWithArrayView;
    private EditText valueInput;
    private Button pushButton;
    private Button popButton;
    private Button peekButton;
    private Button clearButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stack_with_array);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        stackWithArrayView = findViewById(R.id.stackWithArrayView);
        valueInput = findViewById(R.id.valueInput);
        pushButton = findViewById(R.id.pushButton);
        popButton = findViewById(R.id.popButton);
        peekButton = findViewById(R.id.peekButton);
        clearButton = findViewById(R.id.clearButton);

        pushButton.setOnClickListener(v -> push(valueInput));

        popButton.setOnClickListener(v -> pop());
        
        peekButton.setOnClickListener(v -> peek());

        clearButton.setOnClickListener(v -> clear());
    }
    private void clear(){
        stackWithArrayView.clear();
    }

    private void peek() {
        stackWithArrayView.peek();
    }

    private void pop() {
        stackWithArrayView.pop();
    }

    private void push(EditText valueInput) {
        String input = valueInput.getText().toString();
        if (!input.isEmpty()) {
            try {
                int value = Integer.parseInt(input);
                stackWithArrayView.push(value);
                valueInput.setText("");
            } catch (NumberFormatException e) {
                Toast toast = Toast.makeText(StackWithArrayActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(StackWithArrayActivity.this, "Please enter value", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }
}