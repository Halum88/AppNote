package com.example.appnote;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private MainContract.Presenter presenter;

    private ActivityResultLauncher<Intent> addEditNoteLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter( this);
        presenter.attach(this);

        setupToolbar();
        setupRecycler();
        setupFab();
        setupResultLauncher();

        presenter.loadNotes();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
    }

    private void setupRecycler() {
        recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(null, new NoteAdapter.NoteClickListener() {
            @Override
            public void onNoteClick(Note note, int position) {
                presenter.onNoteClicked(note, position);
            }

            @Override
            public void onNoteLongClick(View view, Note note, int position) {
                showNoteContextMenu(view, note, position);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecorator(16));
    }

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fabAddNote);
        fab.setOnClickListener(v -> presenter.onAddNoteClicked());
    }

    private void setupResultLauncher() {
        addEditNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Note note = (Note) data.getSerializableExtra(AddEditNoteActivity.EXTRA_NOTE);
                        int position = data.getIntExtra(AddEditNoteActivity.EXTRA_POSITION, -1);

                        if (position == -1) {
                            presenter.onNoteAdded(note);
                        } else {
                            presenter.onNoteUpdated(note, position);
                        }
                    }
                }
        );
    }


    @Override
    public void showNotes(List<Note> notes) {
        adapter.setNotes(notes);
    }

    @Override
    public void openAddNote() {
        addEditNoteLauncher.launch(new Intent(this, AddEditNoteActivity.class));
    }

    @Override
    public void openEditNote(Note note, int position) {
        Intent intent = new Intent(this, AddEditNoteActivity.class);
        intent.putExtra(AddEditNoteActivity.EXTRA_NOTE, note);
        intent.putExtra(AddEditNoteActivity.EXTRA_POSITION, position);
        addEditNoteLauncher.launch(intent);
    }

    @Override
    public void showNotification(String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "note_channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New note")
                .setContentText("Note '" + title + "' added")
                .setAutoCancel(true);

        NotificationManagerCompat.from(this)
                .notify((int) System.currentTimeMillis(), builder.build());
    }


    private void showNoteContextMenu(View view, Note note, int position) {
        androidx.appcompat.widget.PopupMenu menu =
                new androidx.appcompat.widget.PopupMenu(this, view);
        menu.inflate(R.menu.note_context_menu);

        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_edit) {
                presenter.onNoteClicked(note, position);
                return true;
            } else if (item.getItemId() == R.id.action_delete) {
                presenter.onNoteDeleted(position);
                return true;
            }
            return false;
        });

        menu.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}

