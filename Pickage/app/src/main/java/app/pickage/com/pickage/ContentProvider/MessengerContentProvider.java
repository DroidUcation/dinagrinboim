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
public class MessengerContentProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "app.pickage.com.pickage.ContentProvider.MessengerContentProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/messenger");
    private static final int MESSENGER = 1;
    private static final int MESSENGER_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "messenger", MESSENGER);
        uriMatcher.addURI(PROVIDER_NAME, "messenger/#", MESSENGER_ID);
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
        qb.setTables(DBContract.MESSENGER_TBL);
        switch (uriMatcher.match(uri)) {
            case MESSENGER:
                break;
            case MESSENGER_ID:
                qb.appendWhere(DBContract.MESSENGER_ID + "=" + uri.getLastPathSegment());
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
            case MESSENGER:
                return "vnd.android.cursor.dir/vnd.app.pickage.com.pickage.ContentProvider.MessengerContentProvider.messenger";
            case MESSENGER_ID:
                return "vnd.android.cursor.item/vnd.app.pickage.com.pickage.ContentProvider.MessengerContentProvider.messenger";
        }
        return "";
    }

    /**
     * Add a new messenger record
     *
     * @return URI of the new inserted row
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = sqLiteDatabase.insert(DBContract.MESSENGER_TBL, "", values);
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
            case MESSENGER_ID:
                delCount = sqLiteDatabase.delete(
                        DBContract.MESSENGER_TBL,
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
            case MESSENGER_ID:
                updateCount = sqLiteDatabase.update(
                        DBContract.MESSENGER_TBL,
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
     *
     * @param uri
     * @param selection
     * @return where condition for specific ID
     */
    public String getUriIdCondition(Uri uri, String selection) {
        String idStr = uri.getLastPathSegment();
        String where = DBContract.MESSENGER_ID + " = " + idStr;
        if (!TextUtils.isEmpty(selection)) {
            where += " AND " + selection;
        }
        return where;
    }
}

//    @Override
//    public boolean onCreate() {
//        return false;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public String getType(Uri uri) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        return null;
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

//    private static final String PROVIDER_NAME = "app.pickage.com.pickage.ContentProvider.MessengerContentProvider";
//
//    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/messenger");
//    private static final int MESSENGER = 1;
//    private static final int MESSNGER_ID = 2;
//    private static final UriMatcher uriMatcher = getUriMatcher();
//
//    private SQLiteDatabase sqLiteDatabase;
//    private DBHelper dbHelper;
//
//    private static UriMatcher getUriMatcher() {
//        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(PROVIDER_NAME, "messenger", MESSENGER);
//        uriMatcher.addURI(PROVIDER_NAME, "messenger/#", MESSNGER_ID);
//        return uriMatcher;
//    }
//
//
//    @Override
//    public boolean onCreate() {
//        Context context = getContext();
//        dbHelper = new DBHelper(context);
//        sqLiteDatabase = dbHelper.getWritableDatabase();
//        //dbHelper.onUpgrade(sqLiteDatabase, 1, 2); // call on upgrade for recreating DB
//        return (sqLiteDatabase != null);
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
//        sqLiteQueryBuilder.setTables(DBContract.MESSENGER_TBL);
//        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, projection, selection,
//                selectionArgs, null, null, sortOrder);
//        cursor.setNotificationUri(getContext().getContentResolver(), uri);
//
//        return cursor;
//    }
//
//    @Nullable
//    @Override
//    public String getType(Uri uri) {
//        switch (uriMatcher.match(uri)) {
//            case MESSENGER:
//                return "vnd.android.cursor.dir/vnd.app.pickage.com.pickage.ContentProvider.MessengerContentProvider.messenger";
//            case MESSNGER_ID:
//                return "vnd.android.cursor.item/vnd.app.pickage.com.pickage.ContentProvider.MessengerContentProvider.messenger";
//
//        }
//        return "";    }
//
//    @Nullable
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        long rowID = sqLiteDatabase.insert(DBContract.MESSENGER_TBL, "", values);
//        Uri _uri = ContentUris.withAppendedId(uri, rowID);
//        getContext().getContentResolver().notifyChange(_uri, null);
//        return _uri;
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        return sqLiteDatabase.delete(DBContract.MESSENGER_TBL,selection,selectionArgs);
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        return 0;
//    }