package com.example.algoflow.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.algoflow.R;
import com.example.algoflow.algorithm_views.HashSetView;

public class HashSetActivity extends AppCompatActivity {
    private HashSetView hashView;
    private EditText valueInput;
    private Button insertButton, removeButton, searchButton, randomButton, clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_set);

        hashView = findViewById(R.id.hashSetView);
        valueInput = findViewById(R.id.valueInput);
        insertButton = findViewById(R.id.insertButton);
        removeButton = findViewById(R.id.removeButton);
        searchButton = findViewById(R.id.searchButton);
        randomButton = findViewById(R.id.randomButton);
        clearButton = findViewById(R.id.clearButton);

        insertButton.setOnClickListener(v -> insert(valueInput));
        removeButton.setOnClickListener(v -> remove());
        searchButton.setOnClickListener(v -> search(valueInput));
        randomButton.setOnClickListener(v -> hashView.random());
        clearButton.setOnClickListener(v -> clear());
    }

    private void clear() {
        hashView.clear();
        Toast.makeText(this, "HashSet is cleared", Toast.LENGTH_SHORT).show();
    }

    private void search(EditText valueInput) {
        String valueStr = valueInput.getText().toString();
        if (!valueStr.isEmpty()) {
            try {
                int value = Integer.parseInt(valueStr);
                if (hashView.contains(value)) {
                    Toast toast = Toast.makeText(this, "Found value: " + value, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(this, "No value found: " + value, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
                valueInput.setText("");
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

    private void remove() {
        String valueStr = valueInput.getText().toString();
        if (!valueStr.isEmpty()) {
            try {
                int value = Integer.parseInt(valueStr);
                if (!hashView.isEmpty()) {
                    hashView.remove(value);
                    valueInput.setText("");
                } else {
                    Toast.makeText(this, "HashSet is empty!", Toast.LENGTH_SHORT).show();
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

    private void insert(EditText valueInput) {
        String valueStr = valueInput.getText().toString();
        if (!valueStr.isEmpty()) {
            try {
                int value = Integer.parseInt(valueStr);
                hashView.put(value);
                valueInput.setText("");
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