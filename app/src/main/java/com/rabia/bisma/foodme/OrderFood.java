package com.rabia.bisma.foodme;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class OrderFood extends AppCompatActivity {

    Spinner spinner;
    RelativeLayout foodLayout;
    RelativeLayout picLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_food);

        spinner = (Spinner) findViewById(R.id.drinks_spinner);
        foodLayout = (RelativeLayout) findViewById(R.id.order_food_activity);
        picLayout = (RelativeLayout) foodLayout.findViewById(R.id.header);

        picLayout.setBackground(getResources().getDrawable(R.drawable.doner, getTheme()));

    }


}
