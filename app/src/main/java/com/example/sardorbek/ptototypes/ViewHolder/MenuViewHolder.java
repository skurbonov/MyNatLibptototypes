package com.example.sardorbek.ptototypes.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sardorbek.ptototypes.Interface.ItemClickListener;
import com.example.sardorbek.ptototypes.R;

import org.w3c.dom.Text;

/**
 * Created by sardorbek on 2/8/18.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtMenuName;
    public ImageView imageView;


    private ItemClickListener itemClickListener;



    public MenuViewHolder(View itemView){
        super(itemView);

        txtMenuName = (TextView)itemView.findViewById(R.id.menu_name);
        imageView = (ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
