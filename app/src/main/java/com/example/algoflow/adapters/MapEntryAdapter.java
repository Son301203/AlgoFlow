package com.example.algoflow.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.algoflow.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapEntryAdapter extends RecyclerView.Adapter<MapEntryAdapter.MapEntryViewHolder> {
    private final List<Map.Entry<String, String>> entryList;

    public MapEntryAdapter(Map<String, String> map) {
        this.entryList = new ArrayList<>(map != null ? map.entrySet() : new ArrayList<>());
    }

    @NonNull
    @Override
    public MapEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_map_entry, parent, false);
        return new MapEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapEntryViewHolder holder, int position) {
        Map.Entry<String, String> entry = entryList.get(position);
        String formattedText = "• " + entry.getKey() + ": " + entry.getValue();
        holder.mapEntryText.setText(formattedText);
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    static class MapEntryViewHolder extends RecyclerView.ViewHolder {
        TextView mapEntryText;

        MapEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            mapEntryText = itemView.findViewById(R.id.item_map_entry);
        }
    }
}