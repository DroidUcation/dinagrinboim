package app.pickage.com.pickage.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;

/**
 * Created by User on 26/05/2016.
 */
public class DBHelper  extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TABLE_USER = " CREATE TABLE " +
                    DBContract.USER_TBL +  " ( " +
                    DBContract.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBContract.USER_MAIL + " TEXT, " +
                    DBContract.USER_PASSSWORD + " TEXT, " +
                    DBContract.USER_PHONE + " TEXT, " +
                    DBContract.USER_NAME + " TEXT, " +
                    DBContract.USER_IMG + " TEXT, " +
                    DBContract.USER_CREDIT_CARD_NUMBER + " TEXT, " +
                    DBContract.USER_CREDIT_CARD_NAME + " TEXT, " +
                    DBContract.USER_CREDIT_CARD_DATE + " TEXT, " +
                    DBContract.USER_CREDIT_CARD_CSV + " INTEGER, " +
                    DBContract.USER_FEEDBACK + " TEXT " +
            ");";
                    //DBContract.USER_PACKAGE_ID + " INTEGER ," +
                   //" FOREIGN KEY (" + DBContract.USER_PACKAGE_ID + ") REFERENCES " +
                   //  DBContract.PACKAGE_TBL + "(" + DBContract.PACKAGE_ID + "));";


    private static final String SQL_CREATE_TABLE_MESSENGER = " CREATE TABLE " +
                    DBContract.MESSENGER_TBL +  " ( " +
                    DBContract.MESSENGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBContract.MESSENGER_MAIL + " TEXT, " +
                    DBContract.MESSENGER_PASSSWORD + " TEXT, " +
                    DBContract.MESSENGER_PHONE + " TEXT, " +
                    DBContract.MESSENGER_NAME + " TEXT, " +
                    DBContract.MESSENGER_IMG + " TEXT, " + //TEXT IS URL OF IMG
                    DBContract.MESSENGER_CAR_TYPE + " TEXT " +
            ");";
                   // DBContract.MESSENGER_PACKAGE_ID + " INTEGER ," +
                   // " FOREIGN KEY (" + DBContract.MESSENGER_PACKAGE_ID + ") REFERENCES " +
                   //DBContract.PACKAGE_TBL + "(" + DBContract.PACKAGE_ID + "));";


    private static final String SQL_CREATE_TABLE_PACKAGE = " CREATE TABLE " +
                    DBContract.PACKAGE_TBL +  " ( " +
                    DBContract.PACKAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBContract.PKG_ORIGIN__LONG + " DOUBLE, " +
                    DBContract.PKG_ORIGIN_LAT + " DOUBLE, " +
                    DBContract.ORIGIN_PACKAGE + " TEXT, " +
                    DBContract.DESTINATION_PACKAGE + " TEXT, " +
                    DBContract.P_MESSENGER_ID + " INTEGER, " +

                    " FOREIGN KEY (" + DBContract.P_MESSENGER_ID + ") REFERENCES " +
                    DBContract.MESSENGER_TBL + "(" + DBContract.MESSENGER_ID + ")); ";

//                    DBContract.P_USER_ID + " INTEGER, " +
//                    " FOREIGN KEY (" + DBContract.P_USER_ID + ") REFERENCES " +
//                    DBContract.USER_TBL + "(" + DBContract.USER_ID + "));";


    public DBHelper(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create db tables
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_MESSENGER);
        db.execSQL(SQL_CREATE_TABLE_PACKAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop and recreate DB
        db.execSQL(DBContract.SQL_DROP_DATABASE + DBContract.USER_TBL);
        db.execSQL(DBContract.SQL_DROP_DATABASE + DBContract.MESSENGER_TBL);
        db.execSQL(DBContract.SQL_DROP_DATABASE + DBContract.PACKAGE_TBL);
        onCreate(db);
    }

//    //check it
//    public long insertUserContact(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        // insert row
//        long id = db.insert(DBContract.USER_TBL, null, values);
//        return id;
//    }
//
//    // closing database
//    public void closeDB() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        if (db != null && db.isOpen())
//            db.close();
//    }
}