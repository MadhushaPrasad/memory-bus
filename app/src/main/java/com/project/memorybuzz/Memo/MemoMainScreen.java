package com.project.memorybuzz.Memo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.memorybuzz.Adapters.BirthdayAdapter;
import com.project.memorybuzz.Adapters.MemoAdapter;
import com.project.memorybuzz.R;
import com.project.memorybuzz.models.BirthdayClass;
import com.project.memorybuzz.models.MemoClass;

import java.util.ArrayList;

public class MemoMainScreen extends AppCompatActivity {

    RecyclerView memorecyclerview;
    FloatingActionButton addbuttonmemo;
    DatabaseReference memodatabase,deletememoref;
    MemoAdapter memoAdapter;
    ArrayList<MemoClass> memoList;
    String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_main_screen);

        addbuttonmemo = findViewById(R.id.addmemobtn);
        memorecyclerview = findViewById(R.id.memolist);

        addbuttonmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memoaddintent = new Intent(MemoMainScreen.this, AddMemo.class);
                startActivity(memoaddintent);
            }
        });

        memodatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Memo");
        memorecyclerview.setHasFixedSize(true);
        memorecyclerview.setLayoutManager(new LinearLayoutManager(this));

        memoList = new ArrayList<>();
        memoAdapter = new MemoAdapter(this,memoList);
        memorecyclerview.setAdapter(memoAdapter);

        memodatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    MemoClass memoClass1 = datasnapshot.getValue(MemoClass.class);
                    memoList.add(memoClass1);
                }
                memoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<MemoClass> options = null;

        if (str.equals("")){
            options = new FirebaseRecyclerOptions.Builder<MemoClass>().setQuery(memodatabase, MemoClass.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<MemoClass>().setQuery(memodatabase.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),MemoClass.class).build();
        }

        FirebaseRecyclerAdapter<MemoClass, memoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MemoClass, memoViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull memoViewHolder memoViewHolder1, int i, @NonNull MemoClass memoClass) {
                System.out.println(memoClass.getTopic());
                System.out.println(memoClass.getDecription());

                memoViewHolder1.topic.setText(memoClass.getTopic());
                memoViewHolder1.desc.setText(memoClass.getDecription());

                memoViewHolder1.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent editInt = new Intent(MemoMainScreen.this, EditMemo.class);
                        editInt.putExtra("topic",memoClass.getTopic());
                        editInt.putExtra("desc",memoClass.getDecription());
                        startActivity(editInt);
                    }
                });

                memoViewHolder1.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletememoref = FirebaseDatabase.getInstance().getReference().child("Memo").child(memoClass.getTopic());
                        deletememoref.removeValue();
                        Toast.makeText(MemoMainScreen.this,"Deleted!",Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @NonNull
            @Override
            public memoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_memo_card, parent, false);
                memoViewHolder viewHolder = new memoViewHolder(view);
                return viewHolder;
            }
        };
        memorecyclerview.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class memoViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView topic,desc;
        Button edit,delete;



        public memoViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.memoname);
            desc = itemView.findViewById(R.id.memo);
            cardView = itemView.findViewById(R.id.memocard);
            edit = itemView.findViewById(R.id.memoEdit);
            delete = itemView.findViewById(R.id.memodelete);
        }
    }
}


