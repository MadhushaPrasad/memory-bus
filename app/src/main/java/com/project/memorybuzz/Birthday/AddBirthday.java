package com.project.memorybuzz.Birthday;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;
public class AddBirthday extends AppCompatActivity {

    EditText birthdate,birthdayname;
    Button addBirthdayButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);

        birthdate = findViewById(R.id.birthdate);
        birthdayname = findViewById(R.id.birthdayaddname);
        addBirthdayButton = findViewById(R.id.birthdayaddbutton);



        addBirthdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bdname = birthdayname.getText().toString();
                String bddate = birthdate.getText().toString();


                if (bdname.equals("") || bddate.equals("")) {
                    Toast.makeText(AddBirthday.this, "Fill all!", Toast.LENGTH_SHORT).show();

                }else{
                    FirebaseDatabase addressDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = addressDatabase.getReference("Birthday");

                    BirthdayClass birthdayClass = new BirthdayClass(bdname,bddate);
                    ref.child(bdname).setValue(birthdayClass);
                    Toast.makeText(AddBirthday.this, "Added!", Toast.LENGTH_SHORT).show();
                    Intent backintent = new Intent(AddBirthday.this,BirthdayMainScreen.class);
                    startActivity(backintent);
                }


            }
        });

    }

    }



