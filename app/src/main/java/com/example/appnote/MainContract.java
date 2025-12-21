package com.example.appnote;

import java.util.List;


public interface MainContract {

    interface View {
        void showNotes(List<Note> notes);
        void openAddNote();
        void openEditNote(Note note, int position);
        void showNotification(String title);
    }

    interface Presenter {
        void attach(View view);
        void detach();

        void loadNotes();
        void onAddNoteClicked();
        void onNoteClicked(Note note, int position);
        void onNoteAdded(Note note);
        void onNoteUpdated(Note note, int position);
        void onNoteDeleted(int position);
    }
}
