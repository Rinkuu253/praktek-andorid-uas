package com.example.perpusku;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class view_book extends AppCompatActivity {
    private EditText viewBukuJudulBukuEdit, viewBukuPenulisEdit, viewBukuPenerbitEdit;
    private DatePicker viewBukuTanggalTerbitPicker;
    private ImageView viewBukuStatus;
    private TextView viewBukuNoBuku, viewBukuStatusTxt, viewBukuPeminjam;
    private Button viewBukuCreateBtn, viewBukuDeleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        viewBukuNoBuku = findViewById(R.id.view_buku_no_buku);
        viewBukuJudulBukuEdit = findViewById(R.id.view_buku_judul_buku_edit);
        viewBukuPenulisEdit = findViewById(R.id.view_buku_penulis_edit);
        viewBukuPenerbitEdit = findViewById(R.id.view_buku_penerbit_edit);
        viewBukuTanggalTerbitPicker = findViewById(R.id.view_buku_tanggal_terbit_picker);
        viewBukuStatus = findViewById(R.id.view_buku_status);
        viewBukuStatusTxt = findViewById(R.id.view_buku_status_txt);
        viewBukuPeminjam = findViewById(R.id.view_buku_peminjam);
        viewBukuCreateBtn = findViewById(R.id.view_buku_edit_btn);
        viewBukuDeleteBtn = findViewById(R.id.view_buku_delete_btn);

        Intent intent = getIntent();
        if (intent.hasExtra("no_buku")) {
            String receivedNoBuku = intent.getStringExtra("no_buku");
            showDataJsonBukuById(receivedNoBuku);
        }

        viewBukuCreateBtn.setOnClickListener(view -> {
            String noBuku = viewBukuNoBuku.getText().toString();
            String judulBuku = viewBukuJudulBukuEdit.getText().toString();
            String penulis = viewBukuPenulisEdit.getText().toString();
            String penerbit = viewBukuPenerbitEdit.getText().toString();

            int day = viewBukuTanggalTerbitPicker.getDayOfMonth();
            int month = viewBukuTanggalTerbitPicker.getMonth() + 1;
            int year = viewBukuTanggalTerbitPicker.getYear();
            String formattedDate = String.format("%d-%02d-%02d", year, month, day);

            updateRecordMember(noBuku, judulBuku, penulis, penerbit, formattedDate);
        });

        viewBukuDeleteBtn.setOnClickListener(view -> {
            String noBuku = viewBukuNoBuku.getText().toString();
            deleteRecordMember(noBuku);
            finish();
        });
    }

    public void showDataJsonBukuById(String idBuku) {
        String url = "http://" + view_book.this.getResources().getString(R.string.default_ip) + "/library_database/read_buku_by_id.php";
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Create a map to hold your parameters
        final Map<String, String> params = new HashMap<>();
        params.put("no_buku", idBuku);

        // Create a StringRequest with POST method
        StringRequest req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            // Check if the array is not empty
                            if (jsonArray.length() > 0) {
                                // Access the first object directly
                                JSONObject data = jsonArray.getJSONObject(0);

                                String idBuku = data.getString("no_buku");
                                viewBukuNoBuku.setText(idBuku);

                                String judulBuku = data.getString("judul_buku");
                                viewBukuJudulBukuEdit.setText(judulBuku);

                                String penulis = data.getString("penulis");
                                viewBukuPenulisEdit.setText(penulis);

                                String penerbit = data.getString("penerbit");
                                viewBukuPenerbitEdit.setText(penerbit);

                                String tanggalTerbit = data.getString("tanggal_terbit");
                                // Parse the date string to get year, month, and day
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                                Date date;
                                try {
                                    date = sdf.parse(tanggalTerbit);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    // Handle parsing error
                                    return;
                                }

                                // Extract year, month, and day components
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);

                                // Set the date on your DatePicker
                                viewBukuTanggalTerbitPicker.updateDate(year, month, day);

                                String status = data.getString("status_peminjaman");
                                if (status.equals("Dipinjam")) {
                                    viewBukuStatus.setImageResource(R.drawable.img_not_available_icon);
                                    viewBukuStatusTxt.setText(status);
                                }
                                else  {
                                    viewBukuStatus.setImageResource(R.drawable.img_available_icon);
                                    viewBukuStatusTxt.setText(status);
                                }

                                String peminjam = data.getString("nama_member");
                                viewBukuPeminjam.setText(peminjam);

                            } else {
                                // Handle the case where the array is empty
                                Toast.makeText(
                                        getApplicationContext(),
                                        "No data found for the provided id_buku",
                                        Toast.LENGTH_LONG
                                ).show();
                            }

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
        }) {
            // Override the getParams method to pass parameters for POST request
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        requestQueue.add(req);
    }

    public void updateRecordMember (String noBuku, String judulBuku, String penulis, String penerbit, String tanggalTerbit)
    {
        String url = "http://" + view_book.this.getResources().getString(R.string.default_ip) + "/library_database/update_buku.php";
        RequestQueue queue = Volley.newRequestQueue(view_book.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(view_book.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ADD BUKU VOLLEY", error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("no_buku", noBuku);
                params.put("judul_buku", judulBuku);
                params.put("penulis", penulis);
                params.put("penerbit", penerbit);
                params.put("tanggal_terbit", tanggalTerbit);

                return params;
            }
        };
        queue.add(request);
    }

    public void deleteRecordMember (String noBuku)
    {
        String url = "http://" + ViewBukuDetailActivity.this.getResources().getString(R.string.default_ip) + "/library_database/delete_buku.php";
        RequestQueue queue = Volley.newRequestQueue(ViewBukuDetailActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(ViewBukuDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DELETE BUKU VOLLEY", error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("no_buku", noBuku);

                return params;
            }
        };
        queue.add(request);
    }
}
