package com.project.memorybuzz.Places;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.PlaceClass;
import com.project.memorybuzz.models.RecipeClass;

public class AddPlace extends AppCompatActivity {

    EditText locationText,locationname;
    ImageView imageView;
    Button addlocationButton,addlocationimageButton;

    StorageReference storageReference;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        locationText = findViewById(R.id.locationaddtext);
        locationname = findViewById(R.id.locationaddname);
        imageView = findViewById(R.id.locationimage);
        addlocationButton = findViewById(R.id.locationaddbutton);
        addlocationimageButton = findViewById(R.id.locationaddimage);


        addlocationimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/location/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);
            }
        });

        addlocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String locationName = locationname.getText().toString();
                String locationdesc = locationText.getText().toString();


                    FirebaseDatabase addressDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = addressDatabase.getReference("Location");


                storageReference = FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                PlaceClass placeClass = new PlaceClass(locationName,locationdesc,uri.toString());
                                ref.child(locationName).setValue(placeClass);
                                Toast.makeText(AddPlace.this, "Added!", Toast.LENGTH_SHORT).show();
                                imageView.setImageURI(null);
                                Intent backintent = new Intent(AddPlace.this, PlaceMainScreen.class);
                                startActivity(backintent);
                            }
                        });




                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPlace.this, "Failed!", Toast.LENGTH_SHORT).show();
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