package com.example.algoflow.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.algoflow.R;
import com.example.algoflow.algorithm_views.BSTView;

public class BSTActivity extends AppCompatActivity {
    private BSTView bstView;
    private EditText valueInput;
    private TextView traversalResult;
    private Button insertButton, deleteButton, searchButton, randomButton, clearButton;
    private Button preOrderButton, inOrderButton, postOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bst);

        bstView = findViewById(R.id.bstView);
        valueInput = findViewById(R.id.valueInput);
        traversalResult = findViewById(R.id.traversalResult);
        insertButton = findViewById(R.id.insertButton);
        deleteButton = findViewById(R.id.deleteButton);
        searchButton = findViewById(R.id.searchButton);
        randomButton = findViewById(R.id.randomButton);
        clearButton = findViewById(R.id.clearButton);
        preOrderButton = findViewById(R.id.preOrderButton);
        inOrderButton = findViewById(R.id.inOrderButton);
        postOrderButton = findViewById(R.id.postOrderButton);

        // Đăng ký listener để cập nhật TextView khi duyệt
        bstView.setTraversalUpdateListener(result -> {
            runOnUiThread(() -> traversalResult.setText(result));
        });

        insertButton.setOnClickListener(v -> insert(valueInput));

        deleteButton.setOnClickListener(v -> delete());

        searchButton.setOnClickListener(v -> search(valueInput));

        randomButton.setOnClickListener(v -> bstView.random());

        clearButton.setOnClickListener(v -> clear());

        preOrderButton.setOnClickListener(v -> {
            if (bstView.isEmpty()) {
                Toast toast = Toast.makeText(this, "Tree is Empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            } else {
                bstView.preOrderTraversal();
            }
        });

        inOrderButton.setOnClickListener(v -> {
            if (bstView.isEmpty()) {
                Toast toast = Toast.makeText(this, "Tree is Empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            } else {
                bstView.inOrderTraversal();
            }
        });

        postOrderButton.setOnClickListener(v -> {
            if (bstView.isEmpty()) {
                Toast toast = Toast.makeText(this, "Tree is Empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            } else {
                bstView.postOrderTraversal();
            }
        });
    }

    private void clear() {
        bstView.clear();
    }

    private void search(EditText valueInput) {
        String valueStr = valueInput.getText().toString();
        if (!valueStr.isEmpty()) {
            try {
                int value = Integer.parseInt(valueStr);
                if (bstView.search(value)) {
                    Toast toast = Toast.makeText(this, "Find value: " + value, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(this, "No value found " + value, Toast.LENGTH_SHORT);
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

    private void delete() {
        String valueStr = valueInput.getText().toString();
        if (!valueStr.isEmpty()) {
            try {
                int value = Integer.parseInt(valueStr);
                if (!bstView.isEmpty()) {
                    bstView.delete(value);
                    valueInput.setText("");
                } else {
                    Toast toast = Toast.makeText(this, "Tree is Empty", Toast.LENGTH_SHORT);
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

    private void insert(EditText valueInput) {
        String valueStr = valueInput.getText().toString();
        if (!valueStr.isEmpty()) {
            try {
                int value = Integer.parseInt(valueStr);
                bstView.insert(value);
                valueInput.setText(""); // Clear input
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