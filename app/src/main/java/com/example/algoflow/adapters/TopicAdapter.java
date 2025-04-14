package com.example.algoflow.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.algoflow.R;
import com.example.algoflow.activities.AlgorithmsActivity;
import com.example.algoflow.activities.TopicDescriptionActivity;
import com.example.algoflow.models.Topic;

import java.util.ArrayList;

public class TopicAdapter extends ArrayAdapter<Topic> {
    private final Activity context;
    private final int idLayout;
    private final ArrayList<Topic> topicArrayList;

    public TopicAdapter(Activity context, int idLayout, ArrayList<Topic> topicArrayList) {
        super(context, idLayout, topicArrayList);
        this.context = context;
        this.idLayout = idLayout;
        this.topicArrayList = topicArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(idLayout, parent, false);
        }
        Topic topic = topicArrayList.get(position);

        //name
        TextView name = convertView.findViewById(R.id.topic_name);
        name.setText(topic.getName());

        //image
        ImageView image = convertView.findViewById(R.id.topic_image);
        String imageUrl = topic.getImageUrl();
        if(imageUrl != null && !imageUrl.isEmpty()) {
            String imageName = imageUrl.substring(0, imageUrl.lastIndexOf("."));
            int resourceId = context.getResources().getIdentifier(
                    imageName,
                    "drawable",
                    context.getPackageName()
            );
            if(resourceId != 0) {
                image.setImageResource(resourceId);
            }else{
                image.setImageResource(R.drawable.topic_default);
            }
        }else{
            image.setImageResource(R.drawable.topic_default);
        }

        ImageView imageNote = convertView.findViewById(R.id.note_image);
        imageNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TopicDescriptionActivity.class);
                intent.putExtra("topicId", topic.getId());
                context.startActivity(intent);
            }
        });

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AlgorithmsActivity.class);
            intent.putExtra("topicId", topic.getId());
            context.startActivity(intent);
        });

        return convertView;

    }
}
