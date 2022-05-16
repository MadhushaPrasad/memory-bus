package com.example.memory_bus.birthday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memory_bus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditBirthday extends AppCompatActivity {
    TextView name;
    EditText date;
    Button update;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference birthdayref = database.getReference("Birthday");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_birthday);

        String Name = getIntent().getExtras().get("name").toString();
        String Date = getIntent().getExtras().get("date").toString();

        name = findViewById(R.id.birthdaynameedit);
        date = findViewById(R.id.birthdateedit);
        update = findViewById(R.id.birthdayeditbtn);

        name.setText(Name);
        date.setText(Date);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newdate = date.getText().toString();

                BirthdayClass birthdayClass = new BirthdayClass(Name,newdate);
                birthdayref.child(Name).setValue(birthdayClass);
                Toast.makeText(EditBirthday.this, "Update!", Toast.LENGTH_SHORT).show();
                Intent backintent = new Intent(EditBirthday.this,BirthdayMainScreen.class);
                startActivity(backintent);
            }
        });
    }
}