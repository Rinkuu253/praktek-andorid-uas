package com.example.perpusku;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

public class create_book extends AppCompatActivity {
    EditText judulBukuEdit, penulisEdit, penerbitEdit;
    DatePicker tanggalTerbitPicker;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        createBtn = findViewById(R.id.create_buku_create_btn);
        judulBukuEdit = findViewById(R.id.view_buku_judul_buku_edit);
        penulisEdit = findViewById(R.id.create_buku_penulis_edit);
        penerbitEdit = findViewById(R.id.create_buku_penerbit_edit);
        tanggalTerbitPicker = findViewById(R.id.create_buku_tanggal_terbit_picker);

        createBtn.setOnClickListener(view -> {
            try {
                String judulBuku = judulBukuEdit.getText().toString();
                String penulis = penulisEdit.getText().toString();
                String penerbit = penerbitEdit.getText().toString();

                int day = tanggalTerbitPicker.getDayOfMonth();
                int month = tanggalTerbitPicker.getMonth() + 1;
                int year = tanggalTerbitPicker.getYear();
                String formattedDate = String.format("%d-%02d-%02d", year, month, day);

                if (!judulBuku.isEmpty() && !penulis.isEmpty() && !penerbit.isEmpty()) {
                    // All fields are filled, proceed with the insertRecordMember method
                    insertRecordMember(judulBuku, penulis, penerbit, formattedDate);
                } else {
                    // Display a toast if any of the fields is empty
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("ERROR ADD", e.toString());
            }
        });
    }

    public void insertRecordMember (String judulBuku, String penulis, String penerbit, String tanggalTerbit)
    {
        String url = "http://" + create_book.this.getResources().getString(R.string.default_ip) + "/library_database/create_buku.php";
        RequestQueue queue = Volley.newRequestQueue(create_book.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(create_book.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // and setting data to edit text as empty
                judulBukuEdit.setText("");
                penulisEdit.setText("");
                penerbitEdit.setText("");
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

                params.put("judul_buku", judulBuku);
                params.put("penulis", penulis);
                params.put("penerbit", penerbit);
                params.put("tanggal_terbit", tanggalTerbit);

                return params;
            }
        };
        queue.add(request);
    }
}
