package com.rabia.bisma.foodme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewActivity extends AppCompatActivity{

    private List<Food> food;
    private RecyclerView recyclerView;
    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        food = new ArrayList<>();
        adapter = new RVAdapter(RecyclerViewActivity.this, food);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(RecyclerViewActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initializeData();
    }

    private void initializeData(){
        food.add(new Food("Lahmacun", "5TL", R.drawable.me));
        food.add(new Food("Doner", "6TL", R.drawable.me));
        food.add(new Food("Pizza", "4TL", R.drawable.me));
        food.add(new Food("Lahmacun", "5TL", R.drawable.me));
        food.add(new Food("Doner", "6TL", R.drawable.me));
        food.add(new Food("Pizza", "4TL", R.drawable.me));
        food.add(new Food("Lahmacun", "5TL", R.drawable.me));
        food.add(new Food("Doner", "6TL", R.drawable.me));
        food.add(new Food("Pizza", "4TL", R.drawable.me));
        food.add(new Food("Lahmacun", "5TL", R.drawable.me));
        food.add(new Food("Doner", "6TL", R.drawable.me));
        food.add(new Food("Pizza", "4TL", R.drawable.me));
    }

}
