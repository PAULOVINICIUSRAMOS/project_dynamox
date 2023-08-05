package com.example.dynamox.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dynamox.R;
import com.example.dynamox.databinding.AdapterItemOptionBinding;

import java.util.ArrayList;

public class AnswerOptionsAdapter extends RecyclerView.Adapter<AnswerOptionsAdapter.ViewHolder> {

    private AdapterItemOptionBinding binding;
    private ArrayList<String> options;
    private int selectedPosition = -1;

    public AnswerOptionsAdapter(ArrayList<String> options) {
        this.options = options;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = AdapterItemOptionBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String option = options.get(position);
        Integer index = position + 1;
        holder.binding.textViewOption.setText(index + ". " + option);

        if (position == selectedPosition) {
            holder.binding.textViewOption.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary));
            holder.binding.textViewOption.setTypeface(null, Typeface.BOLD);
        } else {
            holder.binding.textViewOption.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
            holder.binding.textViewOption.setTypeface(null, Typeface.NORMAL);
        }

        // Defina o OnClickListener para o TextView
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
                setSelectedPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AdapterItemOptionBinding binding;

        public ViewHolder(@NonNull AdapterItemOptionBinding root) {
            super(root.getRoot());
            this.binding = root;
        }
    }
}