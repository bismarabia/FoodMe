package com.rabia.bisma.foodme.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rabia.bisma.foodme.R;
import com.rabia.bisma.foodme.dashboard.Dashboard;
import com.rabia.bisma.foodme.menu.RVMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET, passwordET;
    public static String username;
    public static String password;
    public static ArrayList<HashMap<String, String>> std_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button login_btn = (Button) findViewById(R.id.login_btn);
        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);

        std_list = loadJSONFromAsset();
        final HashMap<String, String> user_input = new HashMap<>();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameET.getText().toString();
                password = passwordET.getText().toString();
                user_input.put("id", username);
                user_input.put("password", password);
                if (std_list.contains(user_input))
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                else {
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "Wrong Match...Feel Free to Try As Many Times As You Want!!",
                            Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    public ArrayList<HashMap<String, String>> loadJSONFromAsset() {
        String json;
        ArrayList<HashMap<String, String>> std_list = null;
        try {
            InputStream is = getAssets().open("students_info.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray array_students = obj.getJSONArray("students_loginInfo");     // size of 4
            JSONObject jsonObjects = array_students.getJSONObject(0);
            std_list = new ArrayList<>();
            HashMap<String, String> userFromJson;

            for (int i = 0; i < array_students.length(); i++) {
                JSONObject std_object = array_students.getJSONObject(i);
                String std_id = std_object.getString("id");
                String std_password = std_object.getString("password");

                //Add your values in your `ArrayList` as below:
                userFromJson = new HashMap<>();
                userFromJson.put("id", std_id);
                userFromJson.put("password", std_password);
                std_list.add(userFromJson);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return std_list;
    }


}
