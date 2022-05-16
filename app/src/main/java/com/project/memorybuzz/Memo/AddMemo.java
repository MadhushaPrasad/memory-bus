package com.project.memorybuzz.Memo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;
import com.project.memorybuzz.models.MemoClass;

public class AddMemo extends AppCompatActivity {

    EditText topic,desc;
    Button addMemoButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        desc = findViewById(R.id.memo);
        topic = findViewById(R.id.memoaddname);
        addMemoButton = findViewById(R.id.memoaddbutton);



        addMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String topic1 = topic.getText().toString();
                String desc1 = desc.getText().toString();


                if (topic1.equals("") || desc1.equals("")) {
                    Toast.makeText(AddMemo.this, "Fill all!", Toast.LENGTH_SHORT).show();

                }else{
                    FirebaseDatabase addressDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = addressDatabase.getReference("Memo");

                    MemoClass memoClass = new MemoClass(topic1,desc1);
                    ref.child(topic1).setValue(memoClass);
                    Toast.makeText(AddMemo.this, "Added!", Toast.LENGTH_SHORT).show();
                    Intent backintent = new Intent(AddMemo.this, MemoMainScreen.class);
                    startActivity(backintent);
                }


            }
        });

    }

    }



