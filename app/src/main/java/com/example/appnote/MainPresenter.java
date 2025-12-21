package com.example.appnote;

import android.content.Context;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private NotesRepository repository;

    public MainPresenter(Context context) {
        repository = new NotesRepository(context);
    }

    @Override
    public void attach(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void loadNotes() {
        List<Note> notes = repository.loadNotes();
        if (view != null) {
            view.showNotes(notes);
        }
    }

    @Override
    public void onAddNoteClicked() {
        if (view != null) {
            view.openAddNote();
        }
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        if (view != null) {
            view.openEditNote(note, position);
        }
    }

    @Override
    public void onNoteAdded(Note note) {
        repository.addNote(note);
        loadNotes();
        if (view != null) {
            view.showNotification(note.getTitle());
        }
    }

    @Override
    public void onNoteUpdated(Note note, int position) {
        repository.updateNote(note, position);
        loadNotes();
    }

    @Override
    public void onNoteDeleted(int position) {
        repository.deleteNote(position);
        loadNotes();
    }
}
