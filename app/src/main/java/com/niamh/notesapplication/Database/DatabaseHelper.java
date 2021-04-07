package com.niamh.notesapplication.Database;


//Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.niamh.notesapplication.Util.Config;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if(databaseHelper==null){
            synchronized (DatabaseHelper.class) {
                if(databaseHelper==null)
                    databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_NOTES_TABLE = "CREATE TABLE " + Config.TABLE_NOTES + "("
                + Config.COLUMN_NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_NOTES_TITLE + " TEXT NOT NULL, "
                + Config.COLUMN_NOTES_SUBTITLE + " TEXT, "
                + Config.COLUMN_NOTES_NOTE + " TEXT, " //nullable
                + Config.COLUMN_NOTES_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP "
                + ")";

        db.execSQL(CREATE_NOTES_TABLE);

        //Tag so i can find execution in my log code
        Log.d("***NIAMH_IS4447_DBH***","DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_NOTES);

        // Create tables again
        onCreate(db);
    }

    //opening the database
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
