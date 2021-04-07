package com.niamh.notesapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.niamh.notesapplication.NoteCRUD.CreateNote.Notes;
import com.niamh.notesapplication.Util.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io


public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
    }

    //Inserting into the notes table
    public long insertNote(Notes notes){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        long id =  -1;;

        contentValues.put(Config.COLUMN_NOTES_TITLE, notes.getTitle());
        contentValues.put(Config.COLUMN_NOTES_SUBTITLE, notes.getSubtitle());
        contentValues.put(Config.COLUMN_NOTES_NOTE, notes.getNote());
        contentValues.put(Config.COLUMN_NOTES_DATE, notes.getDateTime());

        //Try catch statement for if the content is blank and fails a message should post
        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_NOTES, null, contentValues);
        } catch (SQLiteException e){
            Log.d("***NIAMH_FYP_DBQ1***", "Exception: "+ e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    //Getting all the values of the Notes table
    public List<Notes> getAllNotes(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_NOTES, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Notes> notesList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_NOTES_ID));
                        String title = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_TITLE));
                        String subtitle = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_SUBTITLE));
                        String note = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_NOTE));
                        String datTime = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_DATE));

                        notesList.add(new Notes(id, title, subtitle, note, datTime ));
                    }   while (cursor.moveToNext());

                    return notesList;
                }
            //Try catch statement for if the contet is blank and fails a message should post
        } catch (Exception e){
            Log.d("***NIAMH_FYP_DBQ2***", "Exception: "+ e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    //Grtting each attribute by its ID number
    public Notes getNoteById(long notesId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Notes notes = null;

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_NOTES, null,
                    Config.COLUMN_NOTES_ID + " = ? ", new String[] {String.valueOf(notesId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                String title = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_TITLE));
                String subtitle = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_SUBTITLE));
                String note = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_NOTE));
                String dateTime = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_DATE));

                notes = new Notes((int) notesId, title, subtitle, note, dateTime);
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return notes;
    }

    public Notes getNoteByTitle(String notestitle) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Notes notes = null;

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(Config.TABLE_NOTES, null,
                    Config.COLUMN_NOTES_TITLE + " = ? ", new String[]{"%" + notestitle + "%"},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
            }
            int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_NOTES_ID));
            String subtitle = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_SUBTITLE));
            String note = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_NOTE));
            String dateTime = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTES_DATE));

            notes = new Notes(id, notestitle, subtitle, note, dateTime);
        } catch (SQLiteException e){
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
    } finally {
        if(cursor!=null)
            cursor.close();
        sqLiteDatabase.close();
    }
        return notes;

    }

    //Updating the info in the notes table
    public long updateNote(Notes notes){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_NOTES_TITLE, notes.getTitle());
        contentValues.put(Config.COLUMN_NOTES_SUBTITLE, notes.getSubtitle());
        contentValues.put(Config.COLUMN_NOTES_NOTE, notes.getNote());
        contentValues.put(Config.COLUMN_NOTES_DATE, notes.getDateTime());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_NOTES, contentValues,
                    Config.COLUMN_NOTES_ID + " = ? ",
                    new String[] {String.valueOf(notes.getId())});
        } catch (SQLiteException e){
            Log.d("NIAMH_DBQ_4", "Exception: "+ e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    //Delete each note attribute individulaly by its id
    public boolean deleteNoteById(long subjectId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_NOTES,
                Config.COLUMN_NOTES_ID + " = ? ", new String[]{String.valueOf(subjectId)});

        return row > 0;
    }


    //Delete everything in note at once
    public boolean deleteAllNotes(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            sqLiteDatabase.delete(Config.TABLE_NOTES, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_NOTES);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Log.d("***NIAMH_IS4447_DBQ3***", "Exception: "+ e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return deleteStatus;
    }
}