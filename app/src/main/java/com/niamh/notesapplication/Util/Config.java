package com.niamh.notesapplication.Util;

//Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io

public class Config {

    public static final String DATABASE_NAME = "notesTakingDB";

    //column names of notes table
    public static final String TABLE_NOTES= "notes";
    public static final String COLUMN_NOTES_ID = "_id";
    public static final String COLUMN_NOTES_TITLE = "title";
    public static final String COLUMN_NOTES_SUBTITLE = "subtitle";
    public static final String COLUMN_NOTES_NOTE = "note";
    public static final String COLUMN_NOTES_DATE = "dateTime";

    //others for general purpose key-value pair data
    public static final String TITLE = "notes";
    public static final String CREATE_NOTES= "create_notes";
    public static final String UPDATE_NOTES = "update_notes";

}
