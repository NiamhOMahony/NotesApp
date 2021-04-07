package com.niamh.notesapplication.NoteCRUD.CreateNote;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.niamh.notesapplication.Database.DatabaseQueryClass;
import com.niamh.notesapplication.R;
import com.niamh.notesapplication.Util.Config;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

public class NotesCreateDialogFragment extends DialogFragment {
    //Creating a new Note

    //adding in external listener
    private static NotesCreateListener notesCreateListener;

    //declaring variables
    private Spinner titleSpinner;
    private EditText subtitleEditText;
    private EditText noteEditText;
    private TextView dateTextView;

    private ImageView backImageView;
    private ImageView createImageView;

    //giving variables values
    private String titleString = "";
    private String subtitlleString = "";
    private String noteString = "";
    private String dateString ="";

    public NotesCreateDialogFragment() {
        // Required empty public constructor
    }

    //when a new instance of the create fragment is opened it processes the below
    public static NotesCreateDialogFragment newInstance(String title, NotesCreateListener listener){
        notesCreateListener = listener;
        NotesCreateDialogFragment notesCreateDialogFragment = new NotesCreateDialogFragment();
        Bundle args = new Bundle();
        notesCreateDialogFragment.setArguments(args);

        return notesCreateDialogFragment;
    }


    @Override
    public View onCreateView(@org.jetbrains.annotations.NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    /*when the create button is clicked whatever has been written into the edit text boxes is gotten and declared
     as a new row in the database*/
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_note_create_dialog, container, false);

        titleSpinner = view.findViewById(R.id.titleSpinner);
        subtitleEditText = view.findViewById(R.id.subtitleEditText);
        noteEditText = view.findViewById(R.id.noteEditText);
        dateTextView = view.findViewById(R.id.dateTextView);

        createImageView = view.findViewById(R.id.updateImageView);
        backImageView = view.findViewById(R.id.backImageView);


        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        //when the create image is pressed it creates a new row in the database
        createImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view12) {

                titleString = titleSpinner.getSelectedItem().toString();
                subtitlleString = subtitleEditText.getText().toString();
                noteString = noteEditText.getText().toString();
                dateString = dateTextView.getText().toString();

                Notes notes = new Notes(-1, titleString, subtitlleString, noteString, dateString);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(NotesCreateDialogFragment.this.getContext());

                long id = databaseQueryClass.insertNote(notes);

                if (id > 0) {
                    notes.setId(id);
                    notesCreateListener.onSafetyCreated(notes);
                    NotesCreateDialogFragment.this.getDialog().dismiss();

                }
            }
        });

        //when the back button is pressed moves out of the fragmet back
        backImageView.setOnClickListener(view1 -> getDialog().dismiss());
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


}