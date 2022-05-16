package com.project.memorybuzz.Recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.memorybuzz.Birthday.BirthdayMainScreen;
import com.project.memorybuzz.Birthday.EditBirthday;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;
import com.project.memorybuzz.models.RecipeClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditRecipe extends AppCompatActivity {
    TextView name;
    EditText recipe;
    ImageView image;
    Button update,imagechange;

    Uri imageURI;

    StorageReference storageReference;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reciperef = database.getReference("Recipe");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        String Name = getIntent().getExtras().get("name").toString();
        String Recipe = getIntent().getExtras().get("recipe").toString();
        String ImageUri = getIntent().getExtras().get("image").toString();

        name = findViewById(R.id.recipename);
        recipe = findViewById(R.id.recipeedittext);
        image = findViewById(R.id.recipeeditimage);
        update = findViewById(R.id.recipeeditbtn);

        imageURI = Uri.parse(ImageUri);

        name.setText(Name);
        recipe.setText(Recipe);
        Glide.with(EditRecipe.this).load(ImageUri).into(image);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recipe1 = recipe.getText().toString();
                RecipeClass recipeClass = new RecipeClass(Name, recipe1, imageURI.toString());
                reciperef.child(Name).setValue(recipeClass);
                Toast.makeText(EditRecipe.this, "Updated!", Toast.LENGTH_SHORT).show();

                Intent backintent = new Intent(EditRecipe.this, RecipeMainScreen.class);
                startActivity(backintent);

            }
        });

    }

}