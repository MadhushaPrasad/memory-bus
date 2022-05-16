package com.project.memorybuzz.Places;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.project.memorybuzz.Adapters.PlaceAdapter;
import com.project.memorybuzz.Adapters.RecipeAdapter;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.PlaceClass;
import com.project.memorybuzz.models.RecipeClass;

import java.util.ArrayList;

public class PlaceMainScreen extends AppCompatActivity {

    RecyclerView locationrecycleview;
    FloatingActionButton addbutton;
    ArrayList<PlaceClass> locationList;
    String str="";
    DatabaseReference locationdatabase,deletelocationref;
    PlaceAdapter locationAdapter;
    StorageReference imageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_main_screen);

        addbutton = findViewById(R.id.addlocationbtn);
        locationrecycleview = findViewById(R.id.locationlist);


        
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeaddintent = new Intent(PlaceMainScreen.this, AddPlace.class);
                startActivity(recipeaddintent);
            }
        });
        locationdatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Location");
        locationrecycleview.setHasFixedSize(true);
        locationrecycleview.setLayoutManager(new LinearLayoutManager(this));

        locationList = new ArrayList<>();
        locationAdapter = new PlaceAdapter(this,locationList);
        locationrecycleview.setAdapter(locationAdapter);

        locationdatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    PlaceClass locationClass1 = datasnapshot.getValue(PlaceClass.class);
                    locationList.add(locationClass1);
                }
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<PlaceClass> options = null;

        if (str.equals("")){
            options = new FirebaseRecyclerOptions.Builder<PlaceClass>().setQuery(locationdatabase, PlaceClass.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<PlaceClass>().setQuery(locationdatabase.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),PlaceClass.class).build();
        }

        FirebaseRecyclerAdapter<PlaceClass, placeViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PlaceClass, placeViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull placeViewHolder locationViewHolder1, int i, @NonNull PlaceClass placeClass) {
                locationViewHolder1.name.setText(placeClass.getLocationName());
                Glide.with(PlaceMainScreen.this).load(placeClass.getLocationimage()).into(locationViewHolder1.image);
                locationViewHolder1.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent editInt = new Intent(PlaceMainScreen.this, EditPlace.class);
                        editInt.putExtra("name",placeClass.getLocationName());
                        editInt.putExtra("desc",placeClass.getLocationdesc());
                        editInt.putExtra("image",placeClass.getLocationimage());
                        startActivity(editInt);
                    }
                });

               locationViewHolder1.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletelocationref = FirebaseDatabase.getInstance().getReference().child("Location").child(placeClass.getLocationName());
                        deletelocationref.removeValue();
                        Toast.makeText(PlaceMainScreen.this,"Deleted!",Toast.LENGTH_SHORT).show();
                    }
                });
                locationViewHolder1.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewInt = new Intent(PlaceMainScreen.this, ViewRecipe.class);
                        viewInt.putExtra("name",placeClass.getLocationName());
                        viewInt.putExtra("desc",placeClass.getLocationdesc());
                        viewInt.putExtra("image",placeClass.getLocationimage());
                        startActivity(viewInt);
                    }
                });


            }

            @NonNull
            @Override
            public placeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_place_card, parent, false);
                placeViewHolder viewHolder = new placeViewHolder(view);
                return viewHolder;
            }
        };
        locationrecycleview.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class placeViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name;
        Button edit,delete;
        ImageView image;


        public placeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.locationtextname);
            image = itemView.findViewById(R.id.locationimage);
            edit = itemView.findViewById(R.id.locationEdit);
            delete = itemView.findViewById(R.id.locationdelete);
            cardView = itemView.findViewById(R.id.locationCard);
        }



    }


}