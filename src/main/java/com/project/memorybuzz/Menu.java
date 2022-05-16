package com.project.memorybuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.memorybuzz.Birthday.BirthdayMainScreen;
import com.project.memorybuzz.Memo.MemoMainScreen;
import com.project.memorybuzz.Places.PlaceMainScreen;
import com.project.memorybuzz.Recipe.RecipeMainScreen;

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

        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeintent = new Intent(Menu.this, RecipeMainScreen.class);
                startActivity(recipeintent);
            }
        });
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent birthdayintent = new Intent(Menu.this, BirthdayMainScreen.class);
                startActivity(birthdayintent);
            }
        });
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memointent = new Intent(Menu.this, MemoMainScreen.class);
                startActivity(memointent);
            }
        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent placeintent = new Intent(Menu.this, PlaceMainScreen.class);
                startActivity(placeintent);
            }
        });
    }
}