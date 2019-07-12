package com.wishenger.android.wishengerr.WishCards;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wishenger.android.wishengerr.R;
import com.squareup.picasso.Picasso;


public class ViewHolderCard extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolderCard(View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onIteamClick(v,getAdapterPosition());

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onIteamLongClick(v,getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails(Context ctx,  String image, String title){
        //views
        TextView mTitleTv=mView.findViewById(R.id.rTitle);
        ImageView mImageTv=mView.findViewById(R.id.rImage);

        //set data to views
        mTitleTv.setText(title);
        Picasso.get().load(image).into(mImageTv);
    }
    private ViewHolderCard.ClickListener mClickListener;
    //interface to send callback
    public interface ClickListener{
        void onIteamClick(View view,int position);
        void onIteamLongClick(View view,int position);
    }
    public void setOnClickListener(ViewHolderCard.ClickListener clickListener){
        mClickListener= clickListener;
    }
}