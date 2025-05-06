package com.example.algoflow.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.algoflow.R;
import com.example.algoflow.algorithm_views.StackWithLinkedListView;

public class StackWithLinkedListActivity extends AppCompatActivity {
    private StackWithLinkedListView stackView;
    private EditText valueInput;
    private Button pushButton, popButton, peekButton, clearButton, randomButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_with_linked_list);

        stackView = findViewById(R.id.stackWithLinkedListView);
        valueInput = findViewById(R.id.valueInput);
        pushButton = findViewById(R.id.pushButton);
        popButton = findViewById(R.id.popButton);
        peekButton = findViewById(R.id.peekButton);
        randomButton = findViewById(R.id.randomButton);
        clearButton = findViewById(R.id.clearButton);

        pushButton.setOnClickListener(v -> push(valueInput));

        popButton.setOnClickListener(v -> pop());

        peekButton.setOnClickListener(v -> peek());

        randomButton.setOnClickListener(v -> random());

        clearButton.setOnClickListener(v -> clear());
    }

    private void peek() {
        if (!stackView.isEmpty()) {
            int value = stackView.peek();
            Toast toast = Toast.makeText(this, "Peek: " + value, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "Stack is Empty!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }

    private void pop() {
        if (!stackView.isEmpty()) {
            int value = stackView.pop();
            Toast toast = Toast.makeText(this, "Pop: " + value, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "Stack is Empty!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }

    }

    private void push(EditText valueInput) {
            String valueStr = valueInput.getText().toString();
            if (!valueStr.isEmpty()) {
                try {
                    int value = Integer.parseInt(valueStr);
                    if (!stackView.isFull()) {
                        stackView.push(value);
                        valueInput.setText("");
                    } else {
                        Toast toast = Toast.makeText(this, "Stack Full! The maximum is 10", Toast.LENGTH_SHORT);
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


    private void clear(){
        stackView.clear();
        Toast toast = Toast.makeText(this, "Stack cleared!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    private void random(){
        stackView.random();
    }
}