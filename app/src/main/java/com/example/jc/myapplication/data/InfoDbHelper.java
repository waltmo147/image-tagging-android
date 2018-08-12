package com.example.jc.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JC on 3/2/18.
 */

public class InfoDbHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "info.db";

    private static final int DATABASE_VERSION = 1;

    public InfoDbHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + InfoContract.InfoEntry.TABLE_NAME + " (" +


                InfoContract.InfoEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                InfoContract.InfoEntry.COLUMN_ID         + " INTEGER NOT NULL,"+

                InfoContract.InfoEntry.COLUMN_TYPE       + " TEXT NOT NULL, "                 +

                InfoContract.InfoEntry.COLUMN_JSON       + " TEXT NOT NULL);";

                /*
                 * To ensure this table can only contain one weather entry per date, we declare
                 * the date column to be unique. We also specify "ON CONFLICT REPLACE". This tells
                 * SQLite that if we have a weather entry for a certain date and we attempt to
                 * insert another weather entry with that date, we replace the old weather entry.
                 */


                db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ InfoContract.InfoEntry.TABLE_NAME);
        onCreate(db);

    }
}
