package com.example.algoflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.algoflow.R;
import com.example.algoflow.adapters.TopicAdapter;
import com.example.algoflow.models.Topic;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private GridView gridViewTopic;
    private TopicAdapter topicAdapter;
    private ArrayList<Topic> topicArrayList;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();

        gridViewTopic = findViewById(R.id.grid_view_topic);
        topicArrayList = new ArrayList<>();
        topicAdapter = new TopicAdapter(this, R.layout.layout_gridview_topic, topicArrayList);
        gridViewTopic.setAdapter(topicAdapter);

        fetchTopic();
    }

    private void fetchTopic() {
        db.collection("Topics")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        topicArrayList.clear();
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            String id = documentSnapshot.getId();
                            String name = documentSnapshot.getString("name");
                            String image = documentSnapshot.getString("imageUrl");
                            Topic topic = new Topic(id, name, image);
                            topicArrayList.add(topic);
                        }
                        topicAdapter.notifyDataSetChanged();
                    }else {
                        Log.d("Topic", "Error getting topic: ", task.getException());
                    }
                });
    }

}