package com.example.mdbest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sxj52 on 2017/1/15.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHoler> {
    private Context mContext;

    private List<Fruit> mFruitList;
    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext)
                .inflate(R.layout.fruit_item,parent,false);
        final ViewHoler viewHoler=  new ViewHoler(view);
        viewHoler.friutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHoler.getAdapterPosition();
                Fruit fruit=mFruitList.get(position);
                Intent intent=new Intent(mContext,FruitActivty.class);
                intent.putExtra(FruitActivty.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivty.FRUIT_IMAGE_ID,fruit.getImageId());
                mContext.startActivity(intent);
            }
        });
        return viewHoler;

    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        Fruit fruit=mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);

    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHoler extends RecyclerView.ViewHolder {
        CardView friutView;
        private TextView fruitName;
        private ImageView fruitImage;
        public ViewHoler(View itemView) {
            super(itemView);
            friutView= (CardView) itemView;
            fruitImage= (ImageView) itemView.findViewById(R.id.friut_image);
            fruitName= (TextView) itemView.findViewById(R.id.friut_name);
        }
    }
}
