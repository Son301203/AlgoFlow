package com.example.algoflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.algoflow.R;
import com.example.algoflow.adapters.ExecutionAdapter;
import com.example.algoflow.adapters.MapEntryAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class AlgorithmDescriptionActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView descriptionTitle, introductionText, timeComplexityText, spaceComplexityText;
    private RecyclerView executionRecyclerView, advantagesRecyclerView, disadvantagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_algorithm_description);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        descriptionTitle = findViewById(R.id.algo_description_title);
        introductionText = findViewById(R.id.introduction_text);
        timeComplexityText = findViewById(R.id.time_complexity_text);
        spaceComplexityText = findViewById(R.id.space_complexity_text);
        executionRecyclerView = findViewById(R.id.execution_recycler_view);
        advantagesRecyclerView = findViewById(R.id.advantages_recycler_view);
        disadvantagesRecyclerView = findViewById(R.id.disadvantages_recycler_view);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        // RecyclerView
        executionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        advantagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        disadvantagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(toolbar);
        toolbar.setTitle("AlgoFlow");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        String algorithmId = getIntent().getStringExtra("algorithmId");

        loadDescription(algorithmId);

    }

    private void loadDescription(String algorithmId) {
        db.collection("Algorithms")
                .document(algorithmId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String title = documentSnapshot.getString("name");
                    descriptionTitle.setText(title);

                    db.collection("Algorithms")
                            .document(algorithmId)
                            .collection("Description")
                            .get()
                            .addOnSuccessListener(descriptionSnapshots -> {
                                if (!descriptionSnapshots.isEmpty()) {
                                    Map<String, Object> descriptionData = descriptionSnapshots.getDocuments().get(0).getData();

                                    if (descriptionData != null) {
                                        // Introduction
                                        String introduction = (String) descriptionData.get("introduction");
                                        introductionText.setText(introduction != null ? introduction : "No introduction available");

                                        // Execution
                                        @SuppressWarnings("unchecked")
                                        List<Object> execution = (List<Object>) descriptionData.get("execution");
                                        if (execution != null && !execution.isEmpty()) {
                                            ExecutionAdapter adapter = new ExecutionAdapter(execution);
                                            executionRecyclerView.setAdapter(adapter);
                                        } else {
                                            executionRecyclerView.setVisibility(View.GONE);
                                            findViewById(R.id.execution_label).setVisibility(View.GONE);
                                        }

                                        // Time Complexity
                                        String timeComplexity = (String) descriptionData.get("timeComplexity");
                                        timeComplexityText.setText(timeComplexity != null ? timeComplexity : "N/A");

                                        // Space Complexity
                                        String spaceComplexity = (String) descriptionData.get("spaceComplexity");
                                        spaceComplexityText.setText(spaceComplexity != null ? spaceComplexity : "N/A");

                                        // Advantages
                                        @SuppressWarnings("unchecked")
                                        Map<String, String> advantages = (Map<String, String>) descriptionData.get("advantages");
                                        if (advantages != null && !advantages.isEmpty()) {
                                            MapEntryAdapter adapter = new MapEntryAdapter(advantages);
                                            advantagesRecyclerView.setAdapter(adapter);
                                        } else {
                                            advantagesRecyclerView.setVisibility(View.GONE);
                                            findViewById(R.id.advantages_label).setVisibility(View.GONE);
                                        }

                                        // Disadvantages
                                        @SuppressWarnings("unchecked")
                                        Map<String, String> disadvantages = (Map<String, String>) descriptionData.get("disadvantages");
                                        if (disadvantages != null && !disadvantages.isEmpty()) {
                                            MapEntryAdapter adapter = new MapEntryAdapter(disadvantages);
                                            disadvantagesRecyclerView.setAdapter(adapter);
                                        } else {
                                            disadvantagesRecyclerView.setVisibility(View.GONE);
                                            findViewById(R.id.disadvantages_label).setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }).addOnFailureListener(e -> {
                                Log.e("Description", "Error loading description: ", e);
                            });
                }).addOnFailureListener(e -> {
                    Log.e("Algorithm", "Error loading algorithm: ", e);
                });
    }
}