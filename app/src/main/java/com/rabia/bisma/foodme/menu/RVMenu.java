package com.rabia.bisma.foodme.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rabia.bisma.foodme.R;
import com.rabia.bisma.foodme.cart.RVCart;
import com.rabia.bisma.foodme.history.PurchaseAdapter;
import com.rabia.bisma.foodme.history.PurchaseHistoryRV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RVMenu extends AppCompatActivity implements RVAdapterMenu.AdapterCallBack {

    private List<Food> food;
    private RecyclerView recyclerView;
    private RVAdapterMenu adapter;
    public static TextView noti_addcart_countView;
    public static int noti_addcart_count = 0;
    ImageView CartBadgeMenuBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_rv_activity);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Menu");

        food = new ArrayList<>();
        adapter = new RVAdapterMenu(RVMenu.this, food, this);

        recyclerView = (RecyclerView) findViewById(R.id.menu_rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(RVMenu.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initializeData();
    }

    private void initializeData() {
        ArrayList<HashMap<String, String>> food_list = loadJSONFromAsset();
        for (int i = 0; i < food_list.size(); i++)
            food.add(new Food(
                    food_list.get(i).get("name"),
                    food_list.get(i).get("price"),
                    R.drawable.me)
            );

    }

    public ArrayList<HashMap<String, String>> loadJSONFromAsset() {
        String json;
        ArrayList<HashMap<String, String>> std_list = null;
        try {
            InputStream is = getAssets().open("food_menu.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray array_students = obj.getJSONArray("menu_food");     // size of 4
            std_list = new ArrayList<>();
            HashMap<String, String> userFromJson;

            for (int i = 0; i < array_students.length(); i++) {
                JSONObject std_object = array_students.getJSONObject(i);
                String foodName = std_object.getString("name");
                String foodPrice = std_object.getString("price");

                //Add your values in your `ArrayList` as below:
                userFromJson = new HashMap<>();
                userFromJson.put("name", foodName);
                userFromJson.put("price", foodPrice);
                std_list.add(userFromJson);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return std_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);

        View badgeAddCart = menu.findItem(R.id.addToCartItem).getActionView();
        noti_addcart_countView = (TextView) badgeAddCart.findViewById(R.id.badge_count);
        noti_addcart_countView.setVisibility(View.GONE);

        CartBadgeMenuBar = (ImageView) badgeAddCart.findViewById(R.id.badge_addCart_button);
        CartBadgeMenuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noti_addcart_count == 0)
                    Snackbar.make(view, "Cart is Empty..Pick up Something, Don't be So Mean!!",
                            Snackbar.LENGTH_LONG).show();
                else
                    startActivity(new Intent(getApplicationContext(), RVCart.class));
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.purchase_history:
                startActivity(new Intent(getApplicationContext(), PurchaseHistoryRV.class));
                break;
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return true;
    }

    @Override
    public void updateCartItemsCount() {
        noti_addcart_countView.setText("" + (++noti_addcart_count));
        noti_addcart_countView.setVisibility(View.VISIBLE);
    }

}
