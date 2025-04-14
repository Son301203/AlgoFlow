package com.example.algoflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.algoflow.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TopicDescriptionActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView descriptionText, descriptionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_topic_description);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        descriptionText = findViewById(R.id.description_text);
        descriptionTitle = findViewById(R.id.description_title);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("AlgoFlow");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        String topicId = getIntent().getStringExtra("topicId");
        if(topicId != null){
            loadDescription(topicId);
        }else{
            descriptionText.setText("There is no description for this topic yet.");
        }
    }

    private void loadDescription(String topicId){
        db.collection("Topics")
                .document(topicId)
                .get()
                .addOnSuccessListener(topicDocumentSnapshot ->{
                    String title = topicDocumentSnapshot.getString("name");
                    descriptionTitle.setText(title);
                    db.collection("Topics")
                            .document(topicId)
                            .collection("Description")
                            .get()
                            .addOnSuccessListener(descriptionSnapshots ->{
                                if (!descriptionSnapshots.isEmpty()) {
                                    List<String> introduction = (List<String>) descriptionSnapshots
                                            .getDocuments().get(0).get("introduction");
                                    if (introduction != null) {
                                        StringBuilder formattedText = new StringBuilder();
                                        for (String item : introduction) {
                                            formattedText.append(item).append("\n\n");
                                        }
                                        descriptionText.setText(formattedText.toString());
                                    } else {
                                        descriptionText.setText("No introduction available");
                                    }
                                } else {
                                    descriptionText.setText("No description found");
                                }
                            }).addOnFailureListener(e -> {
                                Log.e("Description", "Error: ", e);
                                descriptionText.setText("Error loading description");
                            });
                }).addOnFailureListener(e -> {
                    Log.e("Topic", "Error: ", e);
                    descriptionText.setText("Error loading topic");
                });
    }
}