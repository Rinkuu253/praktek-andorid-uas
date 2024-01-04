package com.example.perpusku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

public class create_user extends AppCompatActivity {
    EditText namaUserEdit;
    Button createUserBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        namaUserEdit = findViewById(R.id.create_member_nama_member);
        createUserBtn = findViewById(R.id.create_member_create_btn);

        createUserBtn.setOnClickListener(view -> {
            try {
                String namaMember = namaUserEdit.getText().toString();

                insertRecordMember(namaMember);
            }
            catch (Exception e) {
                Log.e("ERROR ADD", e.toString());
            }
        });
    }

    public void insertRecordMember (String namaMember)
    {
        String url = "http://" + create_user.this.getResources().getString(R.string.default_ip) + "/library_database/create_member.php";
        RequestQueue queue = Volley.newRequestQueue(create_user.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(create_user.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(create_user.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                // and setting data to edit text as empty
                namaUserEdit.setText("");
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ADD BUKU VOLLEY", error.toString());
                Toast.makeText(create_user.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("nama_member", namaMember);

                return params;
            }
        };
        queue.add(request);
    }
}