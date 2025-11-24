package com.example.appnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        Button btnGo = findViewById(R.id.btnGo);

        btnGo.setOnClickListener(view -> {
            String name = editName.getText().toString();
            String age = editAge.getText().toString();

            Intent intent = new Intent(MainActivity.this, GreetingActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("age", age);
            startActivity(intent);
        });
    }
}