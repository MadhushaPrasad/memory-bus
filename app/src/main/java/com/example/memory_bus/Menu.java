package com.example.memory_bus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memory_bus.birthday.BirthdayMainScreen;

public class Menu extends AppCompatActivity {

    Button recipe,birthday,memo,place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recipe = findViewById(R.id.recipebtn);
        birthday = findViewById(R.id.birthdaybtn);
        memo = findViewById(R.id.memobtn);
        place = findViewById(R.id.placesbtn);


        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent birthdayintent = new Intent(Menu.this, BirthdayMainScreen.class);
                startActivity(birthdayintent);
            }
        });


    }
}