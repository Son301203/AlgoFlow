package com.example.algoflow.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.algoflow.R;
import com.example.algoflow.models.Algorithm;

import java.util.ArrayList;

public class AlgorithmAdapter extends ArrayAdapter<Algorithm> {
    private int idLayout;
    private Activity context;
    private ArrayList<Algorithm> algorithmArrayList;

    public AlgorithmAdapter(Activity context, int idLayout, ArrayList<Algorithm> algorithmArrayList) {
        super(context, idLayout, algorithmArrayList);
        this.context = context;
        this.idLayout = idLayout;
        this.algorithmArrayList = algorithmArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(idLayout, parent, false);
        }
        Algorithm algorithm = algorithmArrayList.get(position);

        TextView name = convertView.findViewById(R.id.algorithm_name);
        name.setText(algorithm.getName());

        return convertView;
    }
}
