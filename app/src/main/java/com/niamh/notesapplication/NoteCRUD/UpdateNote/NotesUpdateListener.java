package com.niamh.notesapplication.NoteCRUD.UpdateNote;

import com.niamh.notesapplication.NoteCRUD.CreateNote.Notes;

//Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io


//Listening for when update is actually clicked so it can implement code
public interface NotesUpdateListener {

    void onSafetyInfoUpdate(Notes notes, int position);
}
