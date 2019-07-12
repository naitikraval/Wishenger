package com.wishenger.android.wishengerr.WishCards;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wishenger.android.wishengerr.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class DetailCard extends AppCompatActivity {
    ImageView mimageView;
    Button sharebtn;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_card);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Actionbar
//        ActionBar actionBar= getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);

        mimageView=findViewById(R.id.image);
        sharebtn=findViewById(R.id.share);

        String image=getIntent().getStringExtra("image");

        Picasso.get().load(image).into(mimageView);

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();

            }
        });
    }

    private void shareImage() {
        try {
            bitmap =((BitmapDrawable)mimageView.getDrawable()).getBitmap();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            String sharesub= "From Wishenger";
            share.putExtra(Intent.EXTRA_TEXT,sharesub);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    bitmap, "Title", null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, "Share Image Via"));

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //handle on back pressed

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

