package com.project.memorybuzz.Recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.memorybuzz.Birthday.AddBirthday;
import com.project.memorybuzz.Birthday.BirthdayMainScreen;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;
import com.project.memorybuzz.models.RecipeClass;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddRecipe extends AppCompatActivity {

    EditText recipeText,recipename;
    ImageView imageView;
    Button addRecipeButton,addrecipeimageButton;

    StorageReference storageReference;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipeText = findViewById(R.id.recipeaddtext);
        recipename = findViewById(R.id.recipeaddname);
        imageView = findViewById(R.id.recipeimage);
        addRecipeButton = findViewById(R.id.recipeaddbutton);
        addrecipeimageButton = findViewById(R.id.recipeaddimage);


        addrecipeimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recipeName = recipename.getText().toString();
                String recipe = recipeText.getText().toString();


                    FirebaseDatabase addressDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = addressDatabase.getReference("Recipe");


                storageReference = FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                RecipeClass recipeClass = new RecipeClass(recipeName,recipe,uri.toString());
                                ref.child(recipeName).setValue(recipeClass);
                                Toast.makeText(AddRecipe.this, "Added!", Toast.LENGTH_SHORT).show();
                                imageView.setImageURI(null);
                                Intent backintent = new Intent(AddRecipe.this, RecipeMainScreen.class);
                                startActivity(backintent);
                            }
                        });




                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddRecipe.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 100 && data.getData() != null && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
}