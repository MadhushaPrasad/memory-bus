package com.project.memorybuzz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;
import com.project.memorybuzz.models.MemoClass;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder>{

    Context context;
    ArrayList<MemoClass> memoList;

    public MemoAdapter(Context context, ArrayList<MemoClass> memoList) {
        this.context = context;
        this.memoList = memoList;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_birthday_card, parent,false);
        return new MemoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
        MemoClass memoClass = memoList.get(position);
        holder.topic.setText(memoClass.getTopic());
        holder.desc.setText(memoClass.getDecription());
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public static class MemoViewHolder extends RecyclerView.ViewHolder{


        TextView topic,desc;
        CardView cardView;


        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);

            topic = itemView.findViewById(R.id.memoname);
            desc = itemView.findViewById(R.id.memo);

            cardView = itemView.findViewById(R.id.memocard);
        }
    }

}
