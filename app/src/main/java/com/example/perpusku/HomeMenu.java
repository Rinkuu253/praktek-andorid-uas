package com.example.perpusku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeMenu extends AppCompatActivity {

    private Button btnToMember, btnToHistory, btnToBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        btnToMember = findViewById(R.id.btnToMember);
        btnToHistory = findViewById(R.id.btnToHistory);
        btnToBook = findViewById(R.id.btnToBook);

        btnToMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MemberActivity
                Intent intent = new Intent(HomeMenu.this, list_user.class);
                startActivity(intent);
            }
        });

        btnToHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start HistoryActivity
                Intent intent = new Intent(HomeMenu.this, list_history.class);
                startActivity(intent);
            }
        });

        btnToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start BookActivity
                Intent intent = new Intent(HomeMenu.this, list_book.class);
                startActivity(intent);
            }
        });
    }
}
