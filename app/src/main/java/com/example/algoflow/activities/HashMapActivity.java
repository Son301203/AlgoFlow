package com.example.algoflow.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Gravity;
import androidx.appcompat.app.AppCompatActivity;
import com.example.algoflow.R;
import com.example.algoflow.algorithm_views.HashMapView;

public class HashMapActivity extends AppCompatActivity {
    private HashMapView hashView;
    private EditText keyInput, valueInput;
    private Button insertButton, removeButton, searchButton, randomButton, clearButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_map);

        hashView = findViewById(R.id.hashMapView);
        keyInput = findViewById(R.id.keyInput);
        valueInput = findViewById(R.id.valueInput);
        insertButton = findViewById(R.id.insertButton);
        removeButton = findViewById(R.id.removeButton);
        searchButton = findViewById(R.id.searchButton);
        randomButton = findViewById(R.id.randomButton);
        clearButton = findViewById(R.id.clearButton);

        insertButton.setOnClickListener(v -> insert(keyInput, valueInput));
        removeButton.setOnClickListener(v -> remove());
        searchButton.setOnClickListener(v -> search(keyInput));
        randomButton.setOnClickListener(v -> hashView.random());
        clearButton.setOnClickListener(v -> clear());
    }

    private void clear() {
        hashView.clear();
    }

    private void search(EditText keyInput) {
        String keyStr = keyInput.getText().toString();
        if (!keyStr.isEmpty()) {
            try {
                int key = Integer.parseInt(keyStr);
                int value = hashView.get(key);
                if (value != -1) {
                    Toast toast = Toast.makeText(this, "Found value: " + value + " for key: " + key, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(this, "No value found for key: " + key, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
                keyInput.setText("");
            } catch (NumberFormatException e) {
                Toast toast = Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "Please enter a key", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }

    private void remove() {
        String keyStr = keyInput.getText().toString();
        if (!keyStr.isEmpty()) {
            try {
                int key = Integer.parseInt(keyStr);
                if (!hashView.isEmpty()) {
                    hashView.remove(key);
                    keyInput.setText("");
                } else {
                    Toast.makeText(this, "HashMap is empty!", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast toast = Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "Please enter a key", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }

    private void insert(EditText keyInput, EditText valueInput) {
        String keyStr = keyInput.getText().toString();
        String valueStr = valueInput.getText().toString();
        if (!keyStr.isEmpty() && !valueStr.isEmpty()) {
            try {
                int key = Integer.parseInt(keyStr);
                int value = Integer.parseInt(valueStr);
                hashView.put(key, value);
                keyInput.setText("");
                valueInput.setText("");
            } catch (NumberFormatException e) {
                Toast toast = Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "Please enter both key and value", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        }
    }
}