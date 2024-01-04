package com.example.perpusku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.List;
import java.util.Map;

public class list_history extends AppCompatActivity {
    ListView menuListHistory
    private Button btnAdd;

    @Override
    protected void onStart() {
        super.onStart();

        showDataJsonHistory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_history);
        menuListHistory = findViewById(R.id.listHistory);
        btnAdd = findViewById(R.id.btnAdd);


        menuListHistory.setOnItemClickListener((adapterView, view, i, l) -> {
            try {
                HashMap<String, String> clickedItem = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                String idHistory = clickedItem.get("id_history");

                Log.i("History Menu", idHistory);

                Intent intent = new Intent(getApplicationContext(), view_history.class);
                intent.putExtra("id_history", idHistory);
                startActivity(intent);
            }
            catch (Exception e) {
                Log.e("Error", e.toString());
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), create_history.class);
            startActivity(intent);
        });
    }
    public void showDataJsonHistory()
    {
        String url = "http://" + list_history.this.getResources().getString(R.string.default_ip) + "/library_database/read_history.php";
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
                                map.put("id_history", data.getString("id_history"));
                                map.put("nama_history", data.getString("nama_history"));
                                map.put("judul_buku", data.getString("judul_buku"));
                                map.put("status_peminjaman", data.getString("status_peminjaman"));
                                feedList.add(map);
                            }

                            SimpleAdapter simpleAdapter = new SimpleAdapter(
                                    getApplicationContext(),
                                    feedList,
                                    R.layout.detail_list_history,
                                    new String[]{"id_history", "nama_history", "judul_buku"},
                                    new int[]{R.id.txt_detail_history_id_history, R.id.txt_detail_history_nama_user, R.id.txt_detail_history_judul_buku}
                            );

                            menuListHistory.setAdapter(simpleAdapter);
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
class CustomAdapter extends SimpleAdapter {
    public CustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        // Get the "status_peminjaman" for the current item
        String statusPeminjaman = ((Map<String, String>) getItem(position)).get("status_peminjaman");

        // Update the ImageView based on the "status" value
        ImageView statusImageView = view.findViewById(R.id.txt_detail_history_status_pinjam);
        if (statusPeminjaman.equals("Dipinjam")) {
            statusImageView.setImageResource(R.drawable.img_not_available_icon);
        } else {
            statusImageView.setImageResource(R.drawable.img_available_icon);
        }

        return view;
    }
}