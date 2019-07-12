package com.wishenger.android.wishengerr.Templates;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.wishenger.android.wishengerr.InternetActivity;
import com.wishenger.android.wishengerr.R;
import com.wishenger.android.wishengerr.SaveMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Anniversary extends AppCompatActivity {


    ListView listView;
    ArrayAdapter<String> adapter;
    DatabaseReference ref;
    ArrayList<String> list;
    AnniversaryModelClass anniversaryModelClass;
    FirebaseDatabase database;
    TextView textView;
    String[] listItem;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        loadingBar = new ProgressDialog(this);

        anniversaryModelClass=new AnniversaryModelClass();
        list=new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.list_anniversary,R.id.AnniversaryTemplates,list);

        database=FirebaseDatabase.getInstance();
        ref= database.getReference("Anniversary");

        textView=(TextView)findViewById(R.id.userTemplates);
        listView=(ListView)findViewById(R.id.listView_anniversary);
//        textView=(TextView)findViewById(R.id.textView);
//        listItem = getResources().getStringArray(R.array.array_technology);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);





        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=adapter.getItem(position);

//

                Intent intent = new Intent(getBaseContext(), SaveMessage.class);
                intent.putExtra("quote", value);
                startActivity(intent);
                finish();

//                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadingBar.setMessage("Please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    anniversaryModelClass=ds.getValue(AnniversaryModelClass.class);
                    list.add(anniversaryModelClass.getQuotes());
                }

                listView.setAdapter(adapter);
                loadingBar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        CheckInternet();

    }

    public void CheckInternet() {
        if (!isNetworkAvailable()) {
            Intent NoInternetIntent = new Intent(Anniversary.this, InternetActivity.class);
            startActivity(NoInternetIntent);
            finish();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

