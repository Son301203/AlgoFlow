package com.example.algoflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.algoflow.R;
import com.example.algoflow.adapters.TopicAdapter;
import com.example.algoflow.auth.LoginActivity;
import com.example.algoflow.models.Topic;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private GridView gridViewTopic;
    private TopicAdapter topicAdapter;
    private ArrayList<Topic> topicArrayList;
    private ImageButton logoutButton;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView greetingText;


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
        mAuth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logout_button);
        greetingText = findViewById(R.id.greeting_text);

        gridViewTopic = findViewById(R.id.grid_view_topic);
        topicArrayList = new ArrayList<>();
        topicAdapter = new TopicAdapter(this, R.layout.layout_gridview_topic, topicArrayList);
        gridViewTopic.setAdapter(topicAdapter);

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Logout successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        setGreetingText();
        fetchTopic();
    }

    private void setGreetingText() {
        String nameEmail = mAuth.getCurrentUser().getEmail();
        String username = nameEmail.split("@")[0];
        greetingText.setText("Hi: " + username);
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