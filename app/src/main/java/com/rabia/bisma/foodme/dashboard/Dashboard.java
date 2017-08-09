package com.rabia.bisma.foodme.dashboard;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.rabia.bisma.foodme.R;
import com.rabia.bisma.foodme.history.PurchaseHistoryRV;
import com.rabia.bisma.foodme.menu.RVMenu;

public class Dashboard extends AppCompatActivity {

    RelativeLayout menuLayout, historyLayout, contactLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.rgb(114, 163, 240));

        menuLayout = (RelativeLayout) findViewById(R.id.menu_layout);
        historyLayout = (RelativeLayout) findViewById(R.id.history_layout);
        contactLayout = (RelativeLayout) findViewById(R.id.contact_layout);

        menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RVMenu.class));
            }
        });
        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PurchaseHistoryRV.class));
            }
        });
        contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
