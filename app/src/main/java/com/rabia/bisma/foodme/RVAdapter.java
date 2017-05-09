package com.rabia.bisma.foodme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FoodViewHolder> {

    private List<Food> food;
    private Context context;

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView foodName;
        TextView foodPrice;
        ImageView foodPhoto;

        FoodViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            foodName = (TextView) itemView.findViewById(R.id.food_name);
            foodPrice = (TextView) itemView.findViewById(R.id.food_price);
            foodPhoto = (ImageView) itemView.findViewById(R.id.food_photo);
        }
    }


    RVAdapter(Context context, List<Food> food) {
        this.context = context;
        this.food = food;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_items, viewGroup, false);
        return new FoodViewHolder(v);
    }

    public void goTo() {

        context.startActivity(new Intent(context, OrderFood.class));

    }

    @Override
    public void onBindViewHolder(FoodViewHolder foodViewHolder, int i) {
        foodViewHolder.foodName.setText(food.get(i).name);
        foodViewHolder.foodPrice.setText(food.get(i).price);
        foodViewHolder.foodPhoto.setImageResource(food.get(i).picId);
        foodViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo();
            }
        });
    }

    @Override
    public int getItemCount() {
        return food.size();
    }
}
