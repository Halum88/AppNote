package com.example.appnote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnote.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGo.setOnClickListener(view -> {
            String name = binding.editName.getText().toString();
            String age = binding.editAge.getText().toString();

            Intent intent = new Intent(MainActivity.this, GreetingActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("age", age);
            startActivity(intent);
        });
    }
}