package com.project.memorybuzz.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.memorybuzz.R;
import com.project.memorybuzz.models.PlaceClass;
import com.project.memorybuzz.models.RecipeClass;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    Context context;
    ArrayList<PlaceClass> placeList;

    public PlaceAdapter(Context context, ArrayList<PlaceClass> placeList) {
        this.context = context;
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceAdapter.PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_place_card, parent,false);
        return new PlaceAdapter.PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.PlaceViewHolder holder, int position) {
        PlaceClass placeClass = placeList.get(position);
        holder.name.setText(placeClass.getLocationName());

        holder.image.setImageURI(Uri.parse(placeClass.getLocationimage()));
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;
        Button edit,delete;


        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.locationtextname);
            edit = itemView.findViewById(R.id.locationEdit);
            delete = itemView.findViewById(R.id.locationdelete);
            image = itemView.findViewById(R.id.locationimage);


        }
    }}
