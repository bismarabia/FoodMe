package com.rabia.bisma.foodme.history;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rabia.bisma.foodme.R;
import com.rabia.bisma.foodme.cart.FoodCart;
import com.rabia.bisma.foodme.cart.RVCart;

import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryRV extends AppCompatActivity implements PurchaseAdapter.AdapterCallback {

    List<History> purchaseHistoryList = new ArrayList<>();
    static RecyclerView recyclerViewPH;
    PurchaseAdapter purchaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_rv);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("History...");

        purchaseHistoryList = RVCart.getPurchaseHistList();
        purchaseAdapter = new PurchaseAdapter(purchaseHistoryList, PurchaseHistoryRV.this, this);
        recyclerViewPH = (RecyclerView) findViewById(R.id.purchase_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PurchaseHistoryRV.this);
        recyclerViewPH.setLayoutManager(layoutManager);
        recyclerViewPH.setAdapter(purchaseAdapter);

    }

    public List<FoodCart> getFoodCart() {
        return RVCart.foodCart;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        final PurchaseAdapter purchaseadapter = new PurchaseAdapter(purchaseHistoryList, getApplicationContext(), this);
        switch (id) {
            case R.id.empty_cart:
                if (purchaseadapter.getItemCount() != 0) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PurchaseHistoryRV.this);
                    alertDialog.setTitle("Wait...Hold On")
                            .setCancelable(false)
                            .setMessage("Are you sure you want to empty the cart?\nThis is cannot be undone")
                            .setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            purchaseadapter.deleteAll();
                                        }
                                    })
                            .setNegativeButton("CANCEL",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                            .show();
                }
                break;
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return true;
    }

    @Override
    public List<FoodCart> onMethodCallback() {
        return RVCart.getFoodCart();
    }
}
