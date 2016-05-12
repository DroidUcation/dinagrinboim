package com.example.android.myfirstapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dina on 04/05/2016.
 */
public class DBFacts extends SQLiteOpenHelper {

    private static final String SQL_CREATION = "CREATE TABLE " +
            FactDBContract.FiveFactsEntry.TABLE_NAME + "( " +
            FactDBContract.FiveFactsEntry._ID + " INTEGER PRIMARY KEY ," +
            FactDBContract.FiveFactsEntry.COLUMN_FACT_NUMBER + " TEXT ," +
            FactDBContract.FiveFactsEntry.COLUMN_FACT + " TEXT " +
            " ) ";

    public DBFacts(Context context) {
        super(context, FactDBContract.FiveFactsEntry.DATABASE_NAME, null,FactDBContract.FiveFactsEntry.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATION);
        //createInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FactDBContract.FiveFactsEntry.SQL_DROP_FACTS_TABLE);
        onCreate(db);
    }
}