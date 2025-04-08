package com.example.memolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

    private ArrayList<Memo> memoList;
    private static View.OnClickListener mOnItemClickListener;
    private final Context context;

    public MemoAdapter(ArrayList<Memo> memoList, Context context) {
        this.memoList = memoList;
        this.context = context;
    }

    public void setOnItemClickListener(View.OnClickListener listener) {
        mOnItemClickListener = listener;
    }

    public static class MemoViewHolder extends RecyclerView.ViewHolder {
        public TextView textSubject, textNote, textPriority, textDate;

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubject = itemView.findViewById(R.id.textSubject);
            textNote = itemView.findViewById(R.id.textViewNote);
            textPriority = itemView.findViewById(R.id.priorityEdit);
            textDate = itemView.findViewById(R.id.editDate);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MemoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
        Memo currentMemo = memoList.get(position);
        holder.textSubject.setText(currentMemo.getSubject());
        holder.textNote.setText(currentMemo.getNote());
        holder.textPriority.setText(currentMemo.getPriority());
        //need holder for date

        String priority = currentMemo.getPriority();

        if (priority.equalsIgnoreCase("high")) {
            holder.textPriority.setBackgroundColor(context.getResources().getColor(R.color.priorityHigh)); // Red
        } else if (priority.equalsIgnoreCase("medium")) {
            holder.textPriority.setBackgroundColor(context.getResources().getColor(R.color.priorityMedium)); // Yellow
        } else if (priority.equalsIgnoreCase("low")) {
            holder.textPriority.setBackgroundColor(context.getResources().getColor(R.color.priorityLow)); // Green
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    // Method to remove an item
    public void removeItem(int position) {
        memoList.remove(position);
        notifyItemRemoved(position);
    }
}
