package com.example.perpusku;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    private EditText editTextUname, editTextPW;
    private RequestQueue requestQueue;
    private static final String LOGIN_PHP = "/library_database/login.php"; // adjust path accordingly

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUname = findViewById(R.id.editTextUsername);
        editTextPW = findViewById(R.id.editTextPassword);
        button = findViewById(R.id.btnLogin);

        // Access the IP from strings.xml
        String defaultIP = getString(R.string.default_ip);
        String loginURL = "http://" + defaultIP + LOGIN_PHP;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editTextUname.getText().toString().trim();
                final String password = editTextPW.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Isi Username dan Password", Toast.LENGTH_SHORT).show();
                } else {
                    // Make a network request to your login.php API
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Handle the response from the server
                                    if (response.trim().equals("Login berhasil")) {
                                        // If login successful, start HomeMenu activity
                                        Intent intent = new Intent(LoginActivity.this, HomeMenu.class);
                                        startActivity(intent);
                                    } else {
                                        // If login failed, display an error message
                                        Toast.makeText(LoginActivity.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle errors
                                    Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Send parameters to the server (username and password)
                            Map<String, String> params = new HashMap<>();
                            params.put("username", username);
                            params.put("password", password);
                            return params;
                        }
                    };

                    // Add the request to the RequestQueue
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}
