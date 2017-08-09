package com.rabia.bisma.foodme.menu;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuboid.cuboidcirclebutton.CuboidButton;
import com.rabia.bisma.foodme.cart.FoodCart;
import com.rabia.bisma.foodme.R;

import java.util.ArrayList;
import java.util.List;


public class RVAdapterMenu extends RecyclerView.Adapter<RVAdapterMenu.FoodViewHolder> {

    private List<Food> food;
    static List<FoodCart> foodCart;
    private Context context;
    AdapterCallBack adapterCallBack;


    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView foodName, foodPrice;
        ImageView foodPhoto;
        CuboidButton addCartBtn;

        FoodViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            foodName = (TextView) itemView.findViewById(R.id.food_name);
            foodPrice = (TextView) itemView.findViewById(R.id.food_price);
            foodPhoto = (ImageView) itemView.findViewById(R.id.food_photo);
            addCartBtn = (CuboidButton) itemView.findViewById(R.id.addToCartBtn);
        }
    }

    RVAdapterMenu(Context context, List<Food> food, AdapterCallBack adapterCallBack) {
        this.context = context;
        this.food = food;
        foodCart = new ArrayList<>();
        this.adapterCallBack = adapterCallBack;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FoodViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_items, viewGroup, false));
    }

    interface AdapterCallBack {
        void updateCartItemsCount();
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder foodViewHolder, int i) {
        foodViewHolder.foodName.setText(food.get(i).name);
        foodViewHolder.foodPrice.setText(food.get(i).price);
        foodViewHolder.foodPhoto.setImageResource(food.get(i).picId);
        foodViewHolder.addCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallBack.updateCartItemsCount();
                foodCart.add(new FoodCart(
                        foodViewHolder.foodName.getText().toString(),
                        foodViewHolder.foodPrice.getText().toString(),
                        "1", null));

            }
        });


    }

    public static List<FoodCart> getFoodCart() {
        return foodCart;
    }

    @Override
    public int getItemCount() {
        return food.size();
    }


}
