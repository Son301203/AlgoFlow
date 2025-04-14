package com.example.algoflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.algoflow.R;
import com.example.algoflow.adapters.AlgorithmAdapter;
import com.example.algoflow.models.Algorithm;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AlgorithmsActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ListView listViewAlgorithms;
    private AlgorithmAdapter algorithmAdapter;
    private ArrayList<Algorithm> algorithmArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_algorithms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        MaterialToolbar toolbar = findViewById(R.id.toolbar_algorithms);

        listViewAlgorithms = findViewById(R.id.list_view_algorithms);
        algorithmArrayList = new ArrayList<>();
        algorithmAdapter = new AlgorithmAdapter(this, R.layout.layout_listview_algorithm, algorithmArrayList);
        listViewAlgorithms.setAdapter(algorithmAdapter);


        String topicId = getIntent().getStringExtra("topicId");

        setSupportActionBar(toolbar);
        toolbar.setTitle("AlgoFlow");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        fetchAlgorithms(topicId);
    }

    private void fetchAlgorithms(String topicId) {
        db.collection("Algorithms")
                .whereIn("topicId", Collections.singletonList(topicId))
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        algorithmArrayList.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            String id = snapshot.getId();
                            String name = snapshot.getString("name");
                            String topicIdField = snapshot.getString("topicId");
                            String visualizationPath = snapshot.getString("visualizationPath");

                            Algorithm algorithm = new Algorithm(id, topicIdField, name, visualizationPath);
                            algorithmArrayList.add(algorithm);
                        }
                        algorithmAdapter.notifyDataSetChanged();
                    }
                    else {
                        Log.d("Algorithms", "Error to fetch Title: ", task.getException());
                    }
                }).addOnFailureListener(e -> {
                    Log.e("Algorithms", "Error in Firebase: ", e);
                });
    }
}