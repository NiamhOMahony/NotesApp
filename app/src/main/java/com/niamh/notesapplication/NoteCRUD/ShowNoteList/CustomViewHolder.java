package com.niamh.notesapplication.NoteCRUD.ShowNoteList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.niamh.notesapplication.R;

//Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io

public class CustomViewHolder extends RecyclerView.ViewHolder {

    //Declaring Variables
    TextView titleTextView;
    TextView subtitleTextView;
    TextView noteTextView;
    TextView dateTextView;

    ImageView binButtonImageView;
    ImageView editButtonImageView;

    ConstraintLayout noteHolder;

    public CustomViewHolder(View itemView) {
        super(itemView);

        //Declaring where to place the values
        titleTextView = itemView.findViewById(R.id.titleTextView);
        subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
        noteTextView = itemView.findViewById(R.id.noteTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);

        binButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);

        noteHolder = itemView.findViewById(R.id.noteHolder);
    }
}