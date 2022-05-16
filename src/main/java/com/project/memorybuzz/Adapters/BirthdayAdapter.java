package com.project.memorybuzz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;

import java.util.ArrayList;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.BirthdayViewHolder>{

    Context context;
    ArrayList<BirthdayClass> birthdayList;

    public BirthdayAdapter(Context context, ArrayList<BirthdayClass> birthdayList) {
        this.context = context;
        this.birthdayList = birthdayList;
    }

    @NonNull
    @Override
    public BirthdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_birthday_card, parent,false);
        return new BirthdayAdapter.BirthdayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayViewHolder holder, int position) {
        BirthdayClass birthdayClass = birthdayList.get(position);
        holder.name.setText(birthdayClass.getBirthdayName());
        holder.date.setText(birthdayClass.getBirthdate());
    }

    @Override
    public int getItemCount() {
        return birthdayList.size();
    }

    public static class BirthdayViewHolder extends RecyclerView.ViewHolder{


        TextView name,date;
        CardView cardView;


        public BirthdayViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.birthdayname);
            date = itemView.findViewById(R.id.birthday);

            cardView = itemView.findViewById(R.id.birthdaycard);
        }
    }
}
