package com.niamh.notesapplication.NoteCRUD.ShowNoteList;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.niamh.notesapplication.Database.DatabaseQueryClass;
import com.niamh.notesapplication.NoteCRUD.CreateNote.Notes;
import com.niamh.notesapplication.NoteCRUD.UpdateNote.NotesUpdateDialogFragment;
import com.niamh.notesapplication.NoteCRUD.UpdateNote.NotesUpdateListener;
import com.niamh.notesapplication.R;
import com.niamh.notesapplication.Util.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io

public class NotesListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    //declaring and assigning values
    private Context context;
    private List<Notes> notesList;
    private DatabaseQueryClass databaseQueryClass;

    public NotesListRecyclerViewAdapter(Context context, List<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
        databaseQueryClass = new DatabaseQueryClass(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Notes notes = notesList.get(position);

        holder.titleTextView.setText(notes.getTitle());
        holder.subtitleTextView.setText(notes.getSubtitle());
        holder.noteTextView.setText(notes.getNote());
        holder.dateTextView.setText(formatDate(notes.getDateTime()));


        //When the bin image is clicked it delets the attibute
        holder.binButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this safety equipment?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteSafety(itemPosition);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });



        //clicking the image of the pencil will bring you to the update tab
        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotesUpdateDialogFragment notesUpdateDialogFragment = NotesUpdateDialogFragment.newInstance(notes.getId(),
                        itemPosition, new NotesUpdateListener() {
                    @Override
                    public void onSafetyInfoUpdate(Notes safety, int position) {
                        notesList.set(position, safety);
                        notifyDataSetChanged();
                    }
                });
                notesUpdateDialogFragment.show(((NotesListActivity) context).getSupportFragmentManager(), Config.UPDATE_NOTES);
            }
        });

    }

    //Deletes them based on ID no
    private void deleteSafety(int position) {
        Notes notes = notesList.get(position);
        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(context);
        boolean isDeleted = databaseQueryClass.deleteNoteById(notes.getId());

        if(isDeleted) {
            notesList.remove(notes);
            notifyDataSetChanged();
            ((NotesListActivity) context).viewVisibility();
        } else
            Toast.makeText(context, "Cannot delete!", Toast.LENGTH_SHORT).show();
    }

    //count how man
    @Override
    public int getItemCount() {
        return notesList.size();
    }


    //taken from michale glearsons SQL notes app IS447
    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-10-10 20:15:42
     * Output: Oct 10 = MMM dd
     *         10/10/2018 = MM/dd/yyyy
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MM/dd/yyyy");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
