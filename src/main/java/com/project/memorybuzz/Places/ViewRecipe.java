package com.project.memorybuzz.Places;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.project.memorybuzz.R;

public class ViewRecipe extends AppCompatActivity {
    TextView name,recipe;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        String Name = getIntent().getExtras().get("name").toString();
        String Recipe = getIntent().getExtras().get("recipe").toString();
        String ImageUri = getIntent().getExtras().get("image").toString();

        name = findViewById(R.id.RecipeName);
        recipe = findViewById(R.id.recipetext);
        image = findViewById(R.id.recipeimage);

        name.setText(Name);
        recipe.setText(Recipe);
        Glide.with(ViewRecipe.this).load(ImageUri).into(image);
    }
}