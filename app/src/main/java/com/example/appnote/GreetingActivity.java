package com.example.appnote;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnote.databinding.ActivityGreetingBinding;

public class GreetingActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityGreetingBinding binding = ActivityGreetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");

        binding.txtGreeting.setText("Happy birthday, " + name + "!");
        binding.txtAge.setText("Your age: " + age + " 🎉");
    }
}
