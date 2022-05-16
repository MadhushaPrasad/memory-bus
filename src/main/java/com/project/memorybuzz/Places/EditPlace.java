package com.project.memorybuzz.Places;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.PlaceClass;
import com.project.memorybuzz.models.RecipeClass;

public class EditPlace extends AppCompatActivity {
    TextView name;
    EditText recipe;
    ImageView image;
    Button update,imagechange;

    Uri imageURI;

    StorageReference storageReference;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference locationref = database.getReference("Location");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place);

        String Name = getIntent().getExtras().get("name").toString();
        String Desc = getIntent().getExtras().get("desc").toString();
        String ImageUri = getIntent().getExtras().get("image").toString();

        name = findViewById(R.id.locationname);
        recipe = findViewById(R.id.locationedittext);
        image = findViewById(R.id.locationeditimage);
        update = findViewById(R.id.locationeditbtn);

        imageURI = Uri.parse(ImageUri);

        name.setText(Name);
        recipe.setText(Desc);
        Glide.with(EditPlace.this).load(ImageUri).into(image);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newDesc = recipe.getText().toString();
                PlaceClass placeClass = new PlaceClass(Name,newDesc, imageURI.toString());
                locationref.child(Name).setValue(placeClass);
                Toast.makeText(EditPlace.this, "Updated!", Toast.LENGTH_SHORT).show();

                Intent backintent = new Intent(EditPlace.this, PlaceMainScreen.class);
                startActivity(backintent);

            }
        });

    }

}