package com.rabia.bisma.foodme.cart;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rabia.bisma.foodme.R;
import com.rabia.bisma.foodme.history.History;
import com.rabia.bisma.foodme.history.PurchaseAdapter;
import com.rabia.bisma.foodme.history.PurchaseHistoryRV;
import com.rabia.bisma.foodme.login.LoginActivity;
import com.rabia.bisma.foodme.menu.Food;
import com.rabia.bisma.foodme.menu.RVAdapterMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class RVCart extends AppCompatActivity {

    public static List<FoodCart> foodCart;
    private List<Food> fc;
    static RecyclerView recyclerView;
    private RVAdapterCart adapter;
    static double totalPrice = 0;
    public static List<History> purchaseHistList = new ArrayList<>();
    private Paint p = new Paint();

    public static List<FoodCart> getFoodCart() {
        return foodCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_rv_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("My Cart");

        //get user's info
        final String username = LoginActivity.username;
        String password = LoginActivity.password;
        HashMap<String, String> user = new HashMap<>();
        ArrayList<HashMap<String, String>> allUsers = LoginActivity.std_list;

        foodCart = RVAdapterMenu.getFoodCart();

        adapter = new RVAdapterCart(RVCart.this, foodCart);

        recyclerView = (RecyclerView) findViewById(R.id.cart_rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(RVCart.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        setUpItemTouchHelper();

//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                Toast.makeText(getApplicationContext(), "on Move", Toast.LENGTH_SHORT).show();
//                //adapter.notifyItemChanged(viewHolder.getLayoutPosition());
//                return true;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                Toast.makeText(getApplicationContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
//                //Remove swiped item from list and notify the RecyclerView
//                //adapter.notifyItemChanged(viewHolder.getLayoutPosition());
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_checkout);
        if (foodCart.size() != 0)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < foodCart.size(); i++)
                        totalPrice += RVAdapterCart.foodCart.get(i).getPriceTimesQuantity();
                    Toast.makeText(
                            getApplicationContext(),
                            String.valueOf(totalPrice) + " TL",
                            Toast.LENGTH_SHORT).show();
                    JSONArray arr;
                    try {
                        InputStream is = getAssets().open("students_info.json");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();

                        arr = new JSONObject(new String(buffer, "UTF-8")).getJSONArray("students_loginInfo");
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject jsonObj = (JSONObject) arr.get(i); // get the josn object
                            if (jsonObj.getString("id").equals(username)) { // compare for the key-value
                                double current_credit = jsonObj.getDouble("credit");
                                double net = current_credit - totalPrice;
                                Toast.makeText(getApplicationContext(), "" + net, Toast.LENGTH_SHORT).show();
                                if (net >= 0)
                                    ((JSONObject) arr.get(i)).put("credit", net); // put the new value for the key
                                else
                                    Toast.makeText(getApplicationContext(), "Credit Not Enough!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < foodCart.size(); i++) {
                        purchaseHistList.add(new History(
                                foodCart.get(i).name,
                                "Quantity: " + foodCart.get(i).quantity
                                , String.valueOf(
                                Double.parseDouble(foodCart.get(i).price.substring(0, foodCart.get(i).price.length() - 2)) *
                                        Integer.parseInt(foodCart.get(i).getQuantity())) + "TL"
                                , new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime())
                        ));
                    }
                    new RVAdapterCart(getApplicationContext(), foodCart).emptyCart();
                }
            });

    }

    private void setUpItemTouchHelper() {

        final ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX < 0) {
                        p.setColor(Color.parseColor("#FF0000"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                        dX /= 4;
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions_cart, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        List<FoodCart> foodCart = RVAdapterCart.getFoodCart();
        final RVAdapterCart rvAdapterCart = new RVAdapterCart(getApplicationContext(), foodCart);
        int id = item.getItemId();
        switch (id) {
            case R.id.empty_cart:
                if (rvAdapterCart.getItemCount() != 0) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(RVCart.this);
                    alertDialog.setTitle("Wait...Hold On");
                    alertDialog.setMessage("Are you sure you want to empty the cart?\nThis is cannot be undone");
                    alertDialog.setIcon(android.R.drawable.ic_delete);
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    rvAdapterCart.emptyCart();
                                }
                            });
                    alertDialog.setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                }
                break;
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return true;
    }

    public static List<History> getPurchaseHistList() {
        return purchaseHistList;
    }
}

