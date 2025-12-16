package com.example.appnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE = "extra_note";

    private EditText etTitle;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        Button btnSave = findViewById(R.id.btnSave);

        if (getIntent().hasExtra(EXTRA_NOTE)) {
            Note note = (Note) getIntent().getSerializableExtra(EXTRA_NOTE);
            if (note != null) {
                etTitle.setText(note.getTitle());
                etContent.setText(note.getContent());
            }
        }

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            if (!title.isEmpty() || !content.isEmpty()) {
                Note note = new Note(title, content);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_NOTE, note);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
