package com.project.memorybuzz.Recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.project.memorybuzz.Adapters.BirthdayAdapter;
import com.project.memorybuzz.Adapters.RecipeAdapter;
import com.project.memorybuzz.Birthday.BirthdayMainScreen;
import com.project.memorybuzz.Birthday.EditBirthday;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;
import com.project.memorybuzz.models.RecipeClass;


import java.util.ArrayList;

public class RecipeMainScreen extends AppCompatActivity {

    RecyclerView reciperecycleview;
    FloatingActionButton addbutton;
    ArrayList<RecipeClass> recipeList;
    String str="";
    DatabaseReference recipedatabase,deletereciperef;
    RecipeAdapter recipeAdapter;
    StorageReference imageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main_screen);

        addbutton = findViewById(R.id.addrecipebtn);
        reciperecycleview = findViewById(R.id.recipelist);


        
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeaddintent = new Intent(RecipeMainScreen.this,AddRecipe.class);
                startActivity(recipeaddintent);
            }
        });
        recipedatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Recipe");
        reciperecycleview.setHasFixedSize(true);
        reciperecycleview.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(this,recipeList);
        reciperecycleview.setAdapter(recipeAdapter);

        recipedatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    RecipeClass recipeClass1 = datasnapshot.getValue(RecipeClass.class);
                    recipeList.add(recipeClass1);
                }
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<RecipeClass> options = null;

        if (str.equals("")){
            options = new FirebaseRecyclerOptions.Builder<RecipeClass>().setQuery(recipedatabase, RecipeClass.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<RecipeClass>().setQuery(recipedatabase.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),RecipeClass.class).build();
        }

        FirebaseRecyclerAdapter<RecipeClass,recipeViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RecipeClass,recipeViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull recipeViewHolder recipeViewHolder1, int i, @NonNull RecipeClass recipeClass) {
                recipeViewHolder1.name.setText(recipeClass.getRecipeName());
                Glide.with(RecipeMainScreen.this).load(recipeClass.getImage()).into(recipeViewHolder1.image);
                recipeViewHolder1.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent editInt = new Intent(RecipeMainScreen.this, EditRecipe.class);
                        editInt.putExtra("name",recipeClass.getRecipeName());
                        editInt.putExtra("recipe",recipeClass.getRecipe());
                        editInt.putExtra("image",recipeClass.getImage());
                        startActivity(editInt);
                    }
                });

                recipeViewHolder1.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletereciperef = FirebaseDatabase.getInstance().getReference().child("Recipe").child(recipeClass.getRecipeName());
                        deletereciperef.removeValue();
                        Toast.makeText(RecipeMainScreen.this,"Deleted!",Toast.LENGTH_SHORT).show();
                    }
                });
                recipeViewHolder1.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewInt = new Intent(RecipeMainScreen.this, ViewRecipe.class);
                        viewInt.putExtra("name",recipeClass.getRecipeName());
                        viewInt.putExtra("recipe",recipeClass.getRecipe());
                        viewInt.putExtra("image",recipeClass.getImage());
                        startActivity(viewInt);
                    }
                });


            }

            @NonNull
            @Override
            public recipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe_card, parent, false);
                recipeViewHolder viewHolder = new recipeViewHolder(view);
                return viewHolder;
            }
        };
        reciperecycleview.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class recipeViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name;
        Button edit,delete;
        ImageView image;


        public recipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipetextname);
            image = itemView.findViewById(R.id.recipeimage);
            edit = itemView.findViewById(R.id.recipeEdit);
            delete = itemView.findViewById(R.id.recipedelete);
            cardView = itemView.findViewById(R.id.recipeCard);
        }



    }


}