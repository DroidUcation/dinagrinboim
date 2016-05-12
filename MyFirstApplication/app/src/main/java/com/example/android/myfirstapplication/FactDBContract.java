package com.example.android.myfirstapplication;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dina on 04/05/2016.
 */
public class FactDBContract {

    public static final String AUTHORITY = "com.example.android.myfirstapplication.FactsProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + FiveFactsEntry.TABLE_NAME);

    public static class FiveFactsEntry implements BaseColumns{

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "fiveFacts.db";
        public static  final String TABLE_NAME = "fiveFacts";
        public static final String COLUMN_FACT_NUMBER = "factNumber";
        public static final String COLUMN_FACT = "fact";
        public static final String SQL_DROP_FACTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}