package com.niamh.notesapplication.NoteCRUD.ShowNoteList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niamh.notesapplication.Database.DatabaseQueryClass;
import com.niamh.notesapplication.FeaturePaint.PaintActivity;
import com.niamh.notesapplication.NoteCRUD.CreateNote.NotesCreateDialogFragment;
import com.niamh.notesapplication.NoteCRUD.CreateNote.NotesCreateListener;
import com.niamh.notesapplication.NoteCRUD.CreateNote.Notes;
import com.niamh.notesapplication.R;
import com.niamh.notesapplication.Util.Config;

import java.util.ArrayList;
import java.util.List;

//Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io

public class NotesListActivity extends AppCompatActivity implements NotesCreateListener {

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Notes> notesList = new ArrayList<>();

    //Passing iformation to the main content activty
    private TextView notesListEmptyTextView;
    private RecyclerView recyclerView;
    private NotesListRecyclerViewAdapter notesListRecyclerViewAdapter;


    //When class is created the folowing attributes get their values passed to them
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        // making the recycler slpit in two rows
        //adapted from https://www.youtube.com/watch?v=BrLnsDkoba0

        recyclerView = findViewById(R.id.recyclerView);
        notesListEmptyTextView = findViewById(R.id.emptyListTextView);

        notesList.addAll(databaseQueryClass.getAllNotes() );

        notesListRecyclerViewAdapter = new NotesListRecyclerViewAdapter(this, notesList);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );
        recyclerView.setAdapter(notesListRecyclerViewAdapter);

        viewVisibility();

        // when bin view selected were are given the option to delete all
        ImageView deleteImageView = findViewById(R.id.deleteImageView);
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteAllSelected();
            }
        });

        //when paint image view selected were brought to paint activity by passing the intent
        ImageView paintImageView = findViewById(R.id.paintImageView);
        paintImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        PaintActivity.class);
                startActivity(i);
            }
        });

        //float action button brings us to the create fragment
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNoteCreateDialog();
            }
        });

    }

    //delete all fragment
    private void onDeleteAllSelected() {
        //When delete menu action is clicked the program asks if youre sure
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to delete all Notes?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    //If you select yes after the warning the table clears
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        boolean isAllDeleted = databaseQueryClass.deleteAllNotes();
                        if(isAllDeleted){
                            notesList.clear();
                            notesListRecyclerViewAdapter.notifyDataSetChanged();
                            viewVisibility();
                        }
                    }
                });

        //If you select no you're brought back to the "Homepage"
        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void viewVisibility() {
        if(notesList.isEmpty())
            notesListEmptyTextView.setVisibility(View.VISIBLE);
        else
            notesListEmptyTextView.setVisibility(View.GONE);
    }

    private void openNoteCreateDialog() {
        NotesCreateDialogFragment notesCreateDialogFragment = NotesCreateDialogFragment.newInstance("Create Note", this);
        notesCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_NOTES);
    }

    //when new data is added update main recycler view
    @Override
    public void onSafetyCreated(Notes notes) {
        notesList.add(notes);
        notesListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Log.d("***NIAMH_IS4447***","Update RecyclerView: "+ notes.getTitle());
    }

}