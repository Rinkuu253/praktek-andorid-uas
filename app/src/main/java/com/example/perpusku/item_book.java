package com.example.perpusku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class item_book extends AppCompatActivity {
    ListView itembuku;
    FloatingActionButton createBukuBtn;

    @Override
    protected void onStart() {
        super.onStart();

        showDataJsonMember();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_book);

        itembuku = findViewById(R.id.itembuku_list);
        createBukuBtn = findViewById(R.id.add_buku_btn);
//      showDataJsonMember();
        itembuku.setOnItemClickListener((adapterView, view, i, l) -> {
            try {
                HashMap<String, String> clickedItem = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                String noBuku = clickedItem.get("no_buku");

//                Toast.makeText(this, i, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ViewBukuDetailActivity.class);
                intent.putExtra("no_buku", noBuku);
                startActivity(intent);
            }
            catch (Exception e) {
                Log.e("Error", e.toString());
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        createBukuBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreateBukuActivity.class);
            startActivity(intent);
        });
    }

    public void showDataJsonMember()
    {
        String url = "http://" + item_book.this.getResources().getString(R.string.default_ip) + "/library_database/read_buku.php";
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
                                map.put("no_buku", data.getString("no_buku"));
                                map.put("judul_buku", data.getString("judul_buku"));
                                feedList.add(map);
                            }

                            SimpleAdapter simpleAdapter = new SimpleAdapter(
                                    getApplicationContext(),
                                    feedList,
                                    R.layout.detail_buku_list,
                                    new String[]{"no_buku", "judul_buku"},
                                    new int[]{R.id.txt_detail_buku_no_buku, R.id.txt_detail_buku_judul_buku}
                            );

                            itembuku.setAdapter(simpleAdapter);
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