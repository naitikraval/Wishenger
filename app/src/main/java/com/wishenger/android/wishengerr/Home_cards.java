package com.wishenger.android.wishengerr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.wishenger.android.wishengerr.WishCards.Anniversary.ACards_List;
import com.wishenger.android.wishengerr.WishCards.Birthday.BCards_List;
import com.wishenger.android.wishengerr.WishCards.Festival.FCards_List;
import com.wishenger.android.wishengerr.WishCards.Friendship.Fr_cards;
import com.wishenger.android.wishengerr.WishCards.Special.Cards_List;

import maes.tech.intentanim.CustomIntent;

public class Home_cards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cards);

        LinearLayout bcard = (LinearLayout) findViewById(R.id.B_cards);
        LinearLayout fecards = (LinearLayout) findViewById(R.id.Fe_cards);
        LinearLayout frcards = (LinearLayout) findViewById(R.id.Fr_card);
        LinearLayout acards = (LinearLayout) findViewById(R.id.A_cards);
        LinearLayout specialCards = (LinearLayout) findViewById(R.id.special_cards);

        bcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_cards.this, BCards_List.class);
                startActivity(i);
                CustomIntent.customType(Home_cards.this, "left-to-right");


            }
        });

        fecards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_cards.this, FCards_List.class);
                startActivity(i);
                CustomIntent.customType(Home_cards.this, "left-to-right");

            }
        });

        frcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_cards.this, Fr_cards.class);
                startActivity(i);
                CustomIntent.customType(Home_cards.this, "left-to-right");

            }
        });

        acards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_cards.this, ACards_List.class);
                startActivity(i);
                CustomIntent.customType(Home_cards.this, "left-to-right");

            }
        });

        specialCards.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_cards.this, Cards_List.class);
                startActivity(i);
                CustomIntent.customType(Home_cards.this, "left-to-right");

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
