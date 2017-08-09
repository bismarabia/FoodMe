package com.rabia.bisma.foodme.history;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rabia.bisma.foodme.R;
import com.rabia.bisma.foodme.cart.FoodCart;
import com.rabia.bisma.foodme.cart.RVAdapterCart;
import com.rabia.bisma.foodme.cart.RVCart;

import java.util.List;


public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.FoodViewHolder> {

    private static List<History> purchaseList;
    private Context context;
    AdapterCallback adapterCallback;

    static class FoodViewHolder extends RecyclerView.ViewHolder {

        CardView purchase_cv;
        TextView purchased_foodName, purchased_foodPrice, purchased_quantity, purchased_date;
        ImageView purchase_delete_btn;

        FoodViewHolder(View itemView) {
            super(itemView);
            purchase_cv = (CardView) itemView.findViewById(R.id.purchase_history_cv);
            purchased_foodName = (TextView) itemView.findViewById(R.id.purchased_name);
            purchased_foodPrice = (TextView) itemView.findViewById(R.id.purchased_totalPrice);
            purchased_quantity = (TextView) itemView.findViewById(R.id.purchased_quantity);
            purchased_date = (TextView) itemView.findViewById(R.id.purchased_date);
            purchase_delete_btn = (ImageView) itemView.findViewById(R.id.purchased_delete_btn);
        }
    }

    PurchaseAdapter(List<History> purchaseList, Context context, AdapterCallback adapterCallback) {
        PurchaseAdapter.purchaseList = purchaseList;
        this.context = context;
        this.adapterCallback = adapterCallback;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FoodViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchase_history_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder foodViewHolder, final int position) {
        foodViewHolder.purchased_foodName.setText(purchaseList.get(position).purchaseName);
        foodViewHolder.purchased_foodPrice.setText(purchaseList.get(position).purchaseTotalPrice);
        foodViewHolder.purchased_quantity.setText(purchaseList.get(position).purchaseQutantity);
        foodViewHolder.purchased_date.setText(purchaseList.get(position).purchaseDate);
        foodViewHolder.purchased_foodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<FoodCart> foodCart = ((PurchaseHistoryRV)context).getFoodCart();
                //Toast.makeText(context, String.valueOf(foodCart.get(position).getOrderDetails().isM_checked()), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, String.valueOf(adapterCallback.onMethodCallback().size()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void deleteAll() {
        purchaseList.clear();
        PurchaseHistoryRV.recyclerViewPH.getAdapter().notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public static interface AdapterCallback {
        List<FoodCart> onMethodCallback();
    }
}
