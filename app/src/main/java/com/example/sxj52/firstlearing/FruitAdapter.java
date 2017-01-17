package com.example.sxj52.firstlearing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sxj52 on 2017/1/15.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHoler> {

    private List<Fruit> mFruitList;
    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_item,parent,false);
        final ViewHoler holer=new ViewHoler(view);
        holer.friutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holer.getAdapterPosition();
                Fruit fruit=mFruitList.get(position);
                Toast.makeText(view.getContext(),"you click view"+fruit.getName(),Toast.LENGTH_SHORT).show();

            }
        });
        holer.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int position=holer.getAdapterPosition();
                    Fruit fruit=mFruitList.get(position);
                    Toast.makeText(view.getContext(),"you click image"+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return holer;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        Fruit fruit=mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitImage.setImageResource(fruit.getIamgeid());

    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHoler extends RecyclerView.ViewHolder {
        View friutView;
        private TextView fruitName;
        private ImageView fruitImage;
        public ViewHoler(View itemView) {
            super(itemView);
            friutView=itemView;
            fruitImage= (ImageView) itemView.findViewById(R.id.imageid);
            fruitName= (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }
}
