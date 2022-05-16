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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;
import com.project.memorybuzz.models.RecipeClass;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

        Context context;
        ArrayList<RecipeClass> recipeList;

public RecipeAdapter(Context context, ArrayList<RecipeClass> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_recipe_card, parent,false);
        return new RecipeAdapter.RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        RecipeClass recipeClass = recipeList.get(position);
        holder.name.setText(recipeClass.getRecipeName());

        holder.image.setImageURI(Uri.parse(recipeClass.getImage()));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;
        Button edit,delete;


        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.recipetextname);
            edit = itemView.findViewById(R.id.recipeEdit);
            delete = itemView.findViewById(R.id.recipedelete);
            image = itemView.findViewById(R.id.recipeimage);


        }
    }
}
