package com.rabia.bisma.foodme.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rabia.bisma.foodme.R;
import com.rabia.bisma.foodme.history.PurchaseAdapter;
import com.rabia.bisma.foodme.menu.RVMenu;

import java.security.cert.Extension;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RVAdapterCart extends RecyclerView.Adapter<RVAdapterCart.FoodViewHolder> {

    public static List<FoodCart> foodCart;
    private Context context;

    static class FoodViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView foodName, foodPrice, quantityCart, detail;
        ImageView deleteItem, decreaseBtn, increaseBtn;

        FoodViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_cart);
            foodName = (TextView) itemView.findViewById(R.id.food_name);
            foodPrice = (TextView) itemView.findViewById(R.id.food_price);
            detail = (TextView) itemView.findViewById(R.id.detail_editText);
            deleteItem = (ImageView) itemView.findViewById(R.id.delete_btn);
            decreaseBtn = (ImageView) itemView.findViewById(R.id.decrease_btn);
            quantityCart = (TextView) itemView.findViewById(R.id.quantity_menu);
            increaseBtn = (ImageView) itemView.findViewById(R.id.increase_btn);
        }
    }

    RVAdapterCart(Context context, List<FoodCart> foodCart) {
        this.context = context;
        RVAdapterCart.foodCart = foodCart;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FoodViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_food_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder foodViewHolder, final int i) {
        foodViewHolder.foodName.setText(foodCart.get(i).name);
        foodViewHolder.foodPrice.setText(foodCart.get(i).price);
        final FoodCart fc = foodCart.get(i);
        foodViewHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(fc);
                RVCart.totalPrice -= Integer.parseInt(foodViewHolder.quantityCart.getText().toString())
                        * Integer.parseInt(foodViewHolder.foodPrice.getText().toString().substring(0, foodViewHolder.foodPrice.getText().toString().length() - 2));

            }
        });
        foodViewHolder.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.valueOf(foodViewHolder.quantityCart.getText().toString());
                if (quantity != 1)
                    foodViewHolder.quantityCart.setText(String.valueOf((--quantity)));
                foodCart.get(i).setQuantity(foodViewHolder.quantityCart.getText().toString());
            }
        });
        foodViewHolder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.valueOf(foodViewHolder.quantityCart.getText().toString());
                foodViewHolder.quantityCart.setText(String.valueOf((++quantity)));
                foodCart.get(i).setQuantity(foodViewHolder.quantityCart.getText().toString());
            }
        });

        foodViewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View v = LayoutInflater.from(context).inflate(R.layout.order_details, null);
                final Spinner drinkSpinner = (Spinner) v.findViewById(R.id.drinks_spinner);
                final CheckBox mayonnaise = (CheckBox) v.findViewById(R.id.mayonnaise);
                final CheckBox ketchup = (CheckBox) v.findViewById(R.id.ketchup);

                builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        foodCart.get(i).setOrderDetails(new OrderDetails(
                                mayonnaise.isChecked(),
                                ketchup.isChecked(),
                                drinkSpinner.getSelectedItem().toString()));
                        dialogInterface.dismiss();
                    }
                });

                builder.setView(v);
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodCart.size();
    }

    private void deleteItem(FoodCart foodCart) {
        int currPosition = RVAdapterCart.foodCart.indexOf(foodCart);
        RVAdapterCart.foodCart.remove(currPosition);
        notifyItemRemoved(currPosition);
        RVMenu.noti_addcart_countView.setText("" + (--RVMenu.noti_addcart_count));
        if (RVAdapterCart.foodCart.size() == 0)
            RVMenu.noti_addcart_countView.setVisibility(View.GONE);
    }

    void emptyCart() {
        foodCart.clear();
        RVCart.recyclerView.getAdapter().notifyDataSetChanged();
        RVMenu.noti_addcart_count = 0;
        RVMenu.noti_addcart_countView.setText("" + 0);
        RVMenu.noti_addcart_countView.setVisibility(View.GONE);
        RVCart.totalPrice = 0;
    }

    public static List<FoodCart> getFoodCart() {
        return foodCart;
    }

    private void doDelete(int adapterPosition) {
        foodCart.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1) {
            return 1002;
        }
        if (position == 2) {
            return 1000;
        }
        return 1001;
    }


}
