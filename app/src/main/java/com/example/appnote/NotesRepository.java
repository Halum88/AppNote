package com.example.appnote;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotesRepository {

    private static final String PREFS_NAME = "app_note_prefs";
    private static final String NOTES_KEY = "notes";

    private SharedPreferences prefs;
    private Gson gson;

    public NotesRepository(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public List<Note> loadNotes() {
        String json = prefs.getString(NOTES_KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<Note>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }

    public void addNote(Note note) {
        List<Note> notes = loadNotes();
        notes.add(note);
        saveNotes(notes);
    }

    public void updateNote(Note note, int position) {
        List<Note> notes = loadNotes();
        if (position >= 0 && position < notes.size()) {
            notes.set(position, note);
            saveNotes(notes);
        }
    }

    public void deleteNote(int position) {
        List<Note> notes = loadNotes();
        if (position >= 0 && position < notes.size()) {
            notes.remove(position);
            saveNotes(notes);
        }
    }

    private void saveNotes(List<Note> notes) {
        String json = gson.toJson(notes);
        prefs.edit().putString(NOTES_KEY, json).apply();
    }
}
