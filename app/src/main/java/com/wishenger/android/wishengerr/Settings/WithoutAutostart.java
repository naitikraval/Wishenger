package com.wishenger.android.wishengerr.Settings;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wishenger.android.wishengerr.R;

public class WithoutAutostart extends AppCompatActivity {
    TextView txt1;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_without_autostart);


        txt1=findViewById(R.id.txt1);
        ok=findViewById(R.id.ok);

        String devicename = Build.BRAND;
        String modelname = Build.MODEL;

        txt1.setText(devicename+" "+modelname);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
