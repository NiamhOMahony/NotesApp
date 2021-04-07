package com.niamh.notesapplication.NoteCRUD.UpdateNote;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.niamh.notesapplication.Database.DatabaseQueryClass;
import com.niamh.notesapplication.NoteCRUD.CreateNote.Notes;
import com.niamh.notesapplication.R;
import com.niamh.notesapplication.Util.Config;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

public class NotesUpdateDialogFragment extends DialogFragment {
    //Updating existing Notes

    //Decalring variables
    private static NotesUpdateListener notesUpdateListener;
    private static long noteId;
    private static int position;

    private Notes mNotes;

    private EditText titleEditText;
    private EditText subtitleEditText;
    private EditText noteEditText;
    private TextView dateTextView;

    private ImageView updateImageView;
    private ImageView backImageView;

    //givig variables values
    private String titleString = "";
    private String subtitlleString = "";
    private String noteString = "";
    private String dateString ="";


    private DatabaseQueryClass databaseQueryClass;

    public NotesUpdateDialogFragment() {
        // Required empty public constructor
    }

    //when a new instance of the create fragment is opened it processes the below
    public static NotesUpdateDialogFragment newInstance(long subId, int pos, NotesUpdateListener listener){
        //added difference of declaring 3 new variables
        noteId = subId;
        position = pos;
        notesUpdateListener = listener;

        NotesUpdateDialogFragment notesUpdateDialogFragment = new NotesUpdateDialogFragment();
        Bundle args = new Bundle();
        notesUpdateDialogFragment.setArguments(args);
        return notesUpdateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_update_dialog, container, false);

        /*when the update button is clicked whatever has been written into the edit text boxes is gotten and declared
     as a new row in the database*/

        titleEditText = view.findViewById(R.id.titleEditText);
        subtitleEditText = view.findViewById(R.id.subtitleEditText);
        noteEditText = view.findViewById(R.id.noteEditText);
        dateTextView = view.findViewById(R.id.dateTextView);

        updateImageView = view.findViewById(R.id.updateImageView);
        backImageView = view.findViewById(R.id.backImageView);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mNotes = databaseQueryClass.getNoteById(noteId);

        //instead of getting the values like in create were setting the values to our updated
        titleEditText.setText(mNotes.getTitle());
        subtitleEditText.setText(mNotes.getSubtitle());
        noteEditText.setText(mNotes.getNote());
        dateTextView.setText(String.valueOf(mNotes.getDateTime()));



        updateImageView.setOnClickListener(new View.OnClickListener() {
            //when we return to view we will get our new values
            @Override
            public void onClick(View view) {
                titleString  = titleEditText.getText().toString();
                subtitlleString = subtitleEditText.getText().toString();
                noteString  = noteEditText.getText().toString();
                dateString = dateTextView.getText().toString();

                mNotes.setTitle(titleString);
                mNotes.setSubtitle(subtitlleString);
                mNotes.setNote(noteString);
                mNotes.setDateTime(dateString);

                long id = databaseQueryClass.updateNote(mNotes);

                if(id>0){
                    notesUpdateListener.onSafetyInfoUpdate(mNotes, position);
                    getDialog().dismiss();
                }
            }
        });
        //if the cancel button is pressed we return to the view
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }
    //On start up of the Update fragment section of the application these ruels are followed
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}


