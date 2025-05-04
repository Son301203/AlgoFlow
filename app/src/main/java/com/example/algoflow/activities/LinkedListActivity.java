package com.example.algoflow.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.algoflow.R;
import com.example.algoflow.algorithm_views.LinkedListView;

public class LinkedListActivity extends AppCompatActivity {
    private LinkedListView linkedListView;
    private EditText valueInput;
    private EditText indexInput;
    private Button addHeadButton, randomButton, clearButton, addTailButton, addIndexButton,
            removeHeadButton, removeTailButton, removeIndexButton, searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_list);

        linkedListView = findViewById(R.id.linkedListView);
        valueInput = findViewById(R.id.valueInput);
        indexInput = findViewById(R.id.indexInput);
        addHeadButton = findViewById(R.id.addHeadButton);
        addTailButton = findViewById(R.id.addTailButton);
        addIndexButton = findViewById(R.id.addIndexButton);
        removeHeadButton = findViewById(R.id.removeHeadButton);
        removeTailButton = findViewById(R.id.removeTailButton);
        removeIndexButton = findViewById(R.id.removeIndexButton);
        searchButton = findViewById(R.id.searchButton);
        randomButton = findViewById(R.id.randomButton);
        clearButton = findViewById(R.id.clearButton);

        addHeadButton.setOnClickListener(v -> {
            String valueStr = valueInput.getText().toString();
            if (!valueStr.isEmpty()) {
                try {
                    int value = Integer.parseInt(valueStr);
                    linkedListView.addToHead(value);
                    valueInput.setText("");
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, "Please enter value", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        addTailButton.setOnClickListener(v -> {
            String valueStr = valueInput.getText().toString();
            if (!valueStr.isEmpty()) {
                try {
                    int value = Integer.parseInt(valueStr);
                    linkedListView.addToTail(value);
                    valueInput.setText("");
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, "Please enter value", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        addIndexButton.setOnClickListener(v -> {
            String valueStr = valueInput.getText().toString();
            String indexStr = indexInput.getText().toString();
            if (!valueStr.isEmpty() && !indexStr.isEmpty()) {
                try {
                    int value = Integer.parseInt(valueStr);
                    int index = Integer.parseInt(indexStr);
                    if (index >= 0) {
                        linkedListView.addToIndex(index, value);
                        valueInput.setText("");
                        indexInput.setText("");
                    } else {
                        Toast toast = Toast.makeText(this, "Index must be greater than or equal to 0", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                        toast.show();
                    }
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, "Please enter value and index", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        removeHeadButton.setOnClickListener(v -> {
            if (linkedListView.getHead() != null) {
                linkedListView.removeHead();
                valueInput.setText("");
            } else {
                Toast toast = Toast.makeText(this, "Empty Linked List!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        removeTailButton.setOnClickListener(v -> {
            if (linkedListView.getHead() != null) {
                linkedListView.removeTail();
                valueInput.setText("");
            } else {
                Toast toast = Toast.makeText(this, "Empty Linked List!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        removeIndexButton.setOnClickListener(v -> {
            String indexStr = indexInput.getText().toString();
            if (!indexStr.isEmpty()) {
                try {
                    int index = Integer.parseInt(indexStr);
                    if (index >= 0) {
                        if (linkedListView.getHead() != null) {
                            linkedListView.removeAtIndex(index);
                            valueInput.setText("");
                            indexInput.setText("");
                        } else {
                            Toast toast = Toast.makeText(this, "Empty Linked List!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(this, "Index must be greater than or equal to 0", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                        toast.show();
                    }
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, "Please enter index", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        searchButton.setOnClickListener(v -> {
            String valueStr = valueInput.getText().toString();
            if (!valueStr.isEmpty()) {
                try {
                    int value = Integer.parseInt(valueStr);
                    if (linkedListView.searchNodeValue(value)) {
                        Toast toast = Toast.makeText(this, "Find value: " + value, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                        toast.show();
                        valueInput.setText("");
                        indexInput.setText("");
                    } else {
                        Toast toast = Toast.makeText(this, "No value found " + value, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                        toast.show();
                    }
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, "Please enter value", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        });

        clearButton.setOnClickListener(v -> linkedListView.clear());

        randomButton.setOnClickListener(v -> linkedListView.random());
    }
}