package com.wishenger.android.wishengerr;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Faq extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

//        MobileAds.initialize(this,"ca-app-pub-1263412267579401~9998817711");

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-1263412267579401/6933670673");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

//    mAdView = findViewById(R.id.adView);
//    AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    @Override
//    protected void onStart() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("/TAG", "The interstitial wasn't loaded yet.");
//        }
//        super.onStart();
//    }
}
