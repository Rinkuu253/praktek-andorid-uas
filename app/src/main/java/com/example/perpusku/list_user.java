package com.example.perpusku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class list_user extends AppCompatActivity {
    ListView menuListUser;
    FloatingActionButton createUserBtn;

    @Override
    protected void onStart() {
        super.onStart();

        showDataJsonMember();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        menuListUser = findViewById(R.id.listUser);
        createUserBtn = findViewById(R.id.btnAdd_User);

//        showDataJsonMember();

        menuListUser.setOnItemClickListener((adapterView, view, i, l) -> {
            try {
                HashMap<String, String> clickedItem = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                String idMember = clickedItem.get("id_member");

                Intent intent = new Intent(getApplicationContext(), view_user.class);
                intent.putExtra("id_member", idMember);
                startActivity(intent);
            }
            catch (Exception e) {
                Log.e("Error", e.toString());
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        createUserBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), create_user.class);
            startActivity(intent);
        });
    }

    public void showDataJsonMember()
    {
        String url = "http://" + list_user.this.getResources().getString(R.string.default_ip) + "/library_database/read_member.php";
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<HashMap<String, String>> feedList= new ArrayList<HashMap<String, String>>();
                            HashMap<String, String> map = new HashMap<String, String>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject data = (JSONObject) response.get(i);
                                map = new HashMap<String, String>();
                                map.put("id_member", data.getString("id_member"));
                                map.put("nama_member", data.getString("nama_member"));
                                feedList.add(map);
                            }

                            SimpleAdapter simpleAdapter = new SimpleAdapter(
                                    getApplicationContext(),
                                    feedList,
                                    R.layout.detail_list_user,
                                    new String[]{"id_member", "nama_member"},
                                    new int[]{R.id.txt_detail_id_user, R.id.txt_detail_nama_user}
                            );

                            menuListUser.setAdapter(simpleAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        getApplicationContext(),
                        error.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        requestQueue.add(req);
    }
}