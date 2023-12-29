package com.example.perpusku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ToggleButton;

public class mainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                // Handle when the toggle button is checked
            } else {
                // Handle when the toggle button is unchecked
            }
        });
    }
}