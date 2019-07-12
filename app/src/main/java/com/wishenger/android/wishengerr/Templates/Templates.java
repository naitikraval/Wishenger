package com.wishenger.android.wishengerr.Templates;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wishenger.android.wishengerr.R;
import com.wishenger.android.wishengerr.SaveMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Templates extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    DatabaseReference ref;
    ArrayList<String> list;
    BirthdayModelClass birthdayModelClass;
    FirebaseDatabase database;
    TextView textView;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        birthdayModelClass=new BirthdayModelClass();
        list=new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.list_temp,R.id.userTemplates,list);

        database=FirebaseDatabase.getInstance();
        ref= database.getReference("Templates");

        textView=(TextView)findViewById(R.id.userTemplates);
        listView=(ListView)findViewById(R.id.listView_temp);
//        textView=(TextView)findViewById(R.id.textView);
//        listItem = getResources().getStringArray(R.array.array_technology);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);




        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    birthdayModelClass=ds.getValue(BirthdayModelClass.class);
                    list.add(birthdayModelClass.getQuote());
                }

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=adapter.getItem(position);

//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, value);
//                startActivity(Intent.createChooser(sharingIntent, "Share via"));

                Intent intent = new Intent(getBaseContext(), SaveMessage.class);
                intent.putExtra("quote", value);
                startActivity(intent);
                finish();

//                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

