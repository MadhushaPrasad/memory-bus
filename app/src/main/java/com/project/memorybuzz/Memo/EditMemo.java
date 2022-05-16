package com.project.memorybuzz.Memo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;
import com.project.memorybuzz.models.MemoClass;

public class EditMemo extends AppCompatActivity {
    TextView topic;
    EditText desc;
    Button update;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference memoref = database.getReference("Memo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        String Topic = getIntent().getExtras().get("topic").toString();
        String Desc = getIntent().getExtras().get("desc").toString();

        topic = findViewById(R.id.memonameedit);
        desc = findViewById(R.id.memoedit);
        update = findViewById(R.id.memoeditbtn);

        topic.setText(Topic);
        desc.setText(Desc);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newdesc = desc.getText().toString();

                MemoClass memoClass = new MemoClass(Topic,newdesc);
                memoref.child(Topic).setValue(memoClass);
                Toast.makeText(EditMemo.this, "Update!", Toast.LENGTH_SHORT).show();
                Intent backintent = new Intent(EditMemo.this, MemoMainScreen.class);
                startActivity(backintent);
            }
        });
    }
}