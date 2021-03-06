package app.pickage.com.pickage.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import app.pickage.com.pickage.DBHelpers.DBContract;
import app.pickage.com.pickage.DBHelpers.DBHelper;

/**
 * Created by User on 30/05/2016.
 */
public class UserContentProvider extends ContentProvider{

    private static final String PROVIDER_NAME = "app.pickage.com.pickage.ContentProvider.UserContentProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/user");
    private static final int USER = 1;
    private static final int USER_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "user", USER);
        uriMatcher.addURI(PROVIDER_NAME, "user/#", USER_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        //dbHelper.onUpgrade(sqLiteDatabase, 1, 2); // call on upgrade for recreating DB
        return (sqLiteDatabase == null) ? false : true;
    }

    /**
     * Select user/s according to URI
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();//mafeila db
        qb.setTables(DBContract.USER_TBL);
        switch (uriMatcher.match(uri)) {
            case USER:
                break;
            case USER_ID:
                qb.appendWhere(DBContract.USER_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
//        if(!TextUtils.isEmpty(sortOrder)){
//            sortOrder = SharingInfoContract.ProductsEntry.CREATED_AT + " DESC"; //Sort by modified date as default
//        }
        Cursor c = qb.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case USER:
                return "vnd.android.cursor.dir/vnd.app.pickage.com.pickage.ContentProvider.UserContentProvider.user";
            case USER_ID:
                return "vnd.android.cursor.item/vnd.app.pickage.com.pickage.ContentProvider.UserContentProvider.user";
        }
        return "";
    }

    /**
     * Add a new user record
     * @return URI of the new inserted row
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = sqLiteDatabase.insert(DBContract.USER_TBL, "", values);
        //If record is added successfully
        if (rowID > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(CONTENT_URI, rowID);
        }
        throw new SQLException("Failed to add a record into " + uri);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case USER_ID:
                delCount = sqLiteDatabase.delete(
                        DBContract.USER_TBL,
                        getUriIdCondition(uri, selection), //Get where condition
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        return delCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updateCount = 0;
        switch (uriMatcher.match(uri)) {
            case USER_ID:
                updateCount = sqLiteDatabase.update(
                        DBContract.USER_TBL,
                        values,
                        getUriIdCondition(uri, selection), //Get where condition
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);

        }
        return updateCount;
    }

    /**
     * Build condition according to URI ID
     * @param uri
     * @param selection
     * @return where condition for specific ID
     */
    public String getUriIdCondition(Uri uri,  String selection){
        String idStr = uri.getLastPathSegment();
        String where = DBContract.USER_ID + " = " + idStr;
        if (!TextUtils.isEmpty(selection)) {
            where += " AND " + selection;
        }
        return where;
    }
}

//    static final String PROVIDER_NAME = "app.pickage.com.pickage.UserContentProvider";
//    public static final Uri CONTENT_USER_URI =
//            Uri.parse("content://"+ PROVIDER_NAME + "/userURI");
//
//    static final int USER = 1;
//    static final int USER_ID = 2;
//
//    private SQLiteDatabase db;
//
//    private static final UriMatcher uriMatcher;
//    static {
//        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(PROVIDER_NAME, "userURI", USER);
//        uriMatcher.addURI(PROVIDER_NAME, "userURI/#", USER_ID);
//    }
//
//    @Override
//    public boolean onCreate() {
//        Context context = getContext();
//        DBHelper dbHelper = new DBHelper(context);
//        /** * Create a write able database which will trigger its * creation if it doesn't already exist. */
//        db = dbHelper.getWritableDatabase();
//
//        return (db == null) ? false : true;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//        qb.setTables(DBContract.USER_TBL);
//        switch (uriMatcher.match(uri)) {
//            case USER:
//                break;
//            case USER_ID:
//                //qb.appendWhere(DBContract.USER_ID + "=" + uri.getPathSegments().get(Integer.parseInt(DBContract.USER_ID)));
//                qb.appendWhere(DBContract.USER_ID + "=" + uri.getPathSegments().get(1));
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
//        return c;
//    }
//
//    @Nullable
//    @Override
//    public String getType(Uri uri){
//        switch (uriMatcher.match(uri)) {
//            case USER:
//                return "vnd.android.cursor.dir/vnd.app.pickage.com.pickage.provider.images";
//            case USER_ID:
//                return "vnd.android.cursor.item/vnd.app.pickage.com.pickage..provider.images";
//
//        }
//        return "";
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        int uriType = uriMatcher.match(uri);
//        db = DBHelper.getWritableDatabase();
//        long id = 0;
//
//        switch(uriType){
//            case Fact :
//                id = db.insert(FactDBContract.FiveFactsEntry.TABLE_NAME,null,values);
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
//        return ContentUris.withAppendedId(uri, id);
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        return 0;
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        return 0;
//    }
//}
