package com.example.appnote;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GreetingActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        TextView txtGreeting = findViewById(R.id.txtGreeting);
        TextView txtAge = findViewById(R.id.txtAge);

        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");

        txtGreeting.setText("Happy birthday, " + name + "!");
        txtAge.setText("Your old " + age + " age 🎉");
    }
}