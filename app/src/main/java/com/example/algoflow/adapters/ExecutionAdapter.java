package com.example.algoflow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.algoflow.R;

import java.util.List;
import java.util.Map;

public class ExecutionAdapter extends RecyclerView.Adapter<ExecutionAdapter.ExecutionViewHolder> {
    private final List<Object> executionList;

    public ExecutionAdapter(List<Object> executionList) {
        this.executionList = executionList;
    }

    @NonNull
    @Override
    public ExecutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_map_entry, parent, false);
        return new ExecutionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExecutionViewHolder holder, int position) {
        Object item = executionList.get(position);
        if (item instanceof String) {
            holder.executionText.setText((String) item);
        } else if (item instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, String> steps = (Map<String, String>) item;
            StringBuilder formattedSteps = new StringBuilder();
            for (Map.Entry<String, String> entry : steps.entrySet()) {
                formattedSteps.append(entry.getKey())
                        .append(". ")
                        .append(entry.getValue())
                        .append("\n");
            }
            holder.executionText.setText(formattedSteps.toString().trim());
        }
    }

    @Override
    public int getItemCount() {
        return executionList != null ? executionList.size() : 0;
    }

    static class ExecutionViewHolder extends RecyclerView.ViewHolder {
        TextView executionText;

        ExecutionViewHolder(@NonNull View itemView) {
            super(itemView);
            executionText = itemView.findViewById(R.id.item_map_entry);
        }
    }
}