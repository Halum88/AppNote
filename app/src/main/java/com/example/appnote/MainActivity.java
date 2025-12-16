package com.example.appnote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> noteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        noteList = new ArrayList<>();
        noteList.add(new Note("One List", "One"));
        noteList.add(new Note("Two List", "Two"));
        noteList.add(new Note("Three List", "Three"));

        adapter = new NoteAdapter(noteList, note -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            intent.putExtra(AddEditNoteActivity.EXTRA_NOTE, note);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);


        recyclerView.addItemDecoration(new VerticalSpaceItemDecorator(16));
    }
}
