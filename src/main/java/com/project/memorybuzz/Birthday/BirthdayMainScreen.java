package com.project.memorybuzz.Birthday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.memorybuzz.Adapters.BirthdayAdapter;
import com.project.memorybuzz.R;
import com.project.memorybuzz.Recipe.AddRecipe;
import com.project.memorybuzz.Recipe.RecipeMainScreen;
import com.project.memorybuzz.models.BirthdayClass;

import java.util.ArrayList;

public class BirthdayMainScreen extends AppCompatActivity {

    RecyclerView birthdayrecyclerview;
    FloatingActionButton addbuttonbirthday;
    DatabaseReference birthdaydatabase,deletebirthdayref;
    BirthdayAdapter birthdayAdapter;
    ArrayList<BirthdayClass> birthdayList;
    String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_main_screen);

        addbuttonbirthday = findViewById(R.id.addbirthdaybtn);
        birthdayrecyclerview = findViewById(R.id.birthdaylist);

        addbuttonbirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent birthdayaddintent = new Intent(BirthdayMainScreen.this, AddBirthday.class);
                startActivity(birthdayaddintent);
            }
        });

        birthdaydatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Birthday");
        birthdayrecyclerview.setHasFixedSize(true);
        birthdayrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        birthdayList = new ArrayList<>();
        birthdayAdapter = new BirthdayAdapter(this,birthdayList);
        birthdayrecyclerview.setAdapter(birthdayAdapter);

        birthdaydatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    BirthdayClass birthdayClass1 = datasnapshot.getValue(BirthdayClass.class);
                    birthdayList.add(birthdayClass1);
                }
                birthdayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<BirthdayClass> options = null;

        if (str.equals("")){
            options = new FirebaseRecyclerOptions.Builder<BirthdayClass>().setQuery(birthdaydatabase, BirthdayClass.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<BirthdayClass>().setQuery(birthdaydatabase.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),BirthdayClass.class).build();
        }

        FirebaseRecyclerAdapter<BirthdayClass,birthdayViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BirthdayClass,birthdayViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull birthdayViewHolder birthdayViewHolder1, int i, @NonNull BirthdayClass birthdayClass) {
                birthdayViewHolder1.name.setText(birthdayClass.getBirthdayName());
                birthdayViewHolder1.date.setText(birthdayClass.getBirthdate());

                birthdayViewHolder1.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent editInt = new Intent(BirthdayMainScreen.this,EditBirthday.class);
                        editInt.putExtra("name",birthdayClass.getBirthdayName());
                        editInt.putExtra("date",birthdayClass.getBirthdate());
                        startActivity(editInt);
                    }
                });

                birthdayViewHolder1.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletebirthdayref = FirebaseDatabase.getInstance().getReference().child("Birthday").child(birthdayClass.getBirthdayName());
                        deletebirthdayref.removeValue();
                        Toast.makeText(BirthdayMainScreen.this,"Deleted!",Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @NonNull
            @Override
            public birthdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_birthday_card, parent, false);
                birthdayViewHolder viewHolder = new birthdayViewHolder(view);
                return viewHolder;
            }
        };
        birthdayrecyclerview.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class birthdayViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name,date;
        Button edit,delete;



        public birthdayViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.birthdayname);
            date = itemView.findViewById(R.id.birthday);
            cardView = itemView.findViewById(R.id.birthdaycard);
            edit = itemView.findViewById(R.id.birthdayEdit);
            delete = itemView.findViewById(R.id.birthdaydelete);
        }
    }
}