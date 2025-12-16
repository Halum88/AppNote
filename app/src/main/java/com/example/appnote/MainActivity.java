package com.example.appnote;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> noteList;


    private ActivityResultLauncher<Intent> addEditNoteLauncher;

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

        addEditNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Note note = (Note) data.getSerializableExtra(AddEditNoteActivity.EXTRA_NOTE);
                        int position = data.getIntExtra(AddEditNoteActivity.EXTRA_POSITION, -1);

                        if (position == -1) {
                            noteList.add(note);
                            adapter.notifyItemInserted(noteList.size() - 1);
                        } else {
                            noteList.set(position, note);
                            adapter.notifyItemChanged(position);
                        }
                    }
                }
        );


        adapter = new NoteAdapter(noteList, new NoteAdapter.NoteClickListener() {
            @Override
            public void onNoteClick(Note note, int position) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE, note);
                intent.putExtra(AddEditNoteActivity.EXTRA_POSITION, position);
                addEditNoteLauncher.launch(intent);
            }

            @Override
            public void onNoteLongClick(View view, Note note, int position) {
                showNoteContextMenu(view, note, position);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecorator(16));

        FloatingActionButton fab = findViewById(R.id.fabAddNote);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            addEditNoteLauncher.launch(intent);
        });
    }

    private void showNoteContextMenu(View view, Note note, int position) {
        androidx.appcompat.widget.PopupMenu popupMenu = new androidx.appcompat.widget.PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.note_context_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.action_edit) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE, note);
                intent.putExtra(AddEditNoteActivity.EXTRA_POSITION, position);
                addEditNoteLauncher.launch(intent);
                return true;

            } else if (id == R.id.action_delete) {
                noteList.remove(position);
                adapter.notifyItemRemoved(position);
                return true;

            } else {
                return false;
            }
        });

        popupMenu.show();
    }
}
