package com.wishenger.android.wishengerr.WishCards.Birthday;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wishenger.android.wishengerr.InternetActivity;
import com.wishenger.android.wishengerr.R;
import com.wishenger.android.wishengerr.WishCards.DetailCard;
import com.wishenger.android.wishengerr.WishCards.ModelCard;
import com.wishenger.android.wishengerr.WishCards.ViewHolderCard;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BCards_List extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    FirebaseRecyclerAdapter<ModelCard, ViewHolderCard> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<ModelCard> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcards__list);

        //Actionbar
//        ActionBar actionBar= getSupportActionBar();
//        actionBar.setTitle("Wish Cards");


        //Recyclrview
        mRecyclerView = findViewById(R.id.recyclerViewcards);

        mRecyclerView.setHasFixedSize(true);

        //send query to firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("BirthdayCards");
        showData();

    }

    private void showData() {
        options = new FirebaseRecyclerOptions.Builder<ModelCard>().setQuery(mRef, ModelCard.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelCard, ViewHolderCard>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderCard holder, int position, @NonNull ModelCard model) {
                holder.setDetails(getApplicationContext(), model.getImage(), model.getTitle());

            }

            @NonNull
            @Override
            public ViewHolderCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //inflate layout
                View iteamview = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_cards_list, parent, false);

                ViewHolderCard viewHolderCard = new ViewHolderCard(iteamview);
                //click
                viewHolderCard.setOnClickListener(new ViewHolderCard.ClickListener() {
                    @Override
                    public void onIteamClick(View view, int position) {

                        ImageView mImagView = view.findViewById(R.id.rImage);
                        String mImage = getItem(position).getImage();

//                                 Drawable mdrawable=mImagView.getDrawable();
//                                 Bitmap mBitmap= ((BitmapDrawable)mdrawable).getBitmap();

                        Intent intent = new Intent(view.getContext(), DetailCard.class);
//                                 ByteArrayOutputStream stream=new ByteArrayOutputStream();
//                                 mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
//                                 byte[] bytes=stream.toByteArray();

                        intent.putExtra("image", mImage);
                        startActivity(intent);
                    }

                    @Override
                    public void onIteamLongClick(View view, int position) {

                    }
                });
                return viewHolderCard;
            }
        };
        //set layout
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckInternet();

    }

    public void CheckInternet() {
        if (!isNetworkAvailable()) {
            Intent NoInternetIntent = new Intent(BCards_List.this, InternetActivity.class);
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
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();

        }
    }
}
