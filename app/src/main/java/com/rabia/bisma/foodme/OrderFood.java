package com.rabia.bisma.foodme;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

public class OrderFood extends AppCompatActivity{

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_food);

        spinner = (Spinner) findViewById(R.id.spinner);

    }
}
