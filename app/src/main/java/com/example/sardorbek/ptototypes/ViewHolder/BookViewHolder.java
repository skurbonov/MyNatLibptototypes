package com.example.sardorbek.ptototypes.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sardorbek.ptototypes.Interface.ItemClickListener;
import com.example.sardorbek.ptototypes.R;

/**
 * Created by sardorbek on 3/28/18.
 */

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;
    public ImageView food_image, fav_image,share_image;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BookViewHolder(View itemView){
        super(itemView);


        food_name =(TextView)itemView.findViewById(R.id.food_name);
        food_image=(ImageView)itemView.findViewById(R.id.food_image);
        fav_image=(ImageView)itemView.findViewById(R.id.fav);
        share_image=(ImageView)itemView.findViewById(R.id.btnShare);

        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
