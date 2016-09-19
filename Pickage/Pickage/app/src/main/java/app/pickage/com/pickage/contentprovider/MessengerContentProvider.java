package app.pickage.com.pickage.contentprovider;

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

import app.pickage.com.pickage.dbhelpers.DBContract;
import app.pickage.com.pickage.dbhelpers.DBHelper;

/**
 * Created by Din&Yeudit on 30/05/2016.
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
        return (sqLiteDatabase == null) ? false : true;
    }

    /**
     * Select user/s according to URI
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
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
                        getUriIdCondition(uri, selection),
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
                        getUriIdCondition(uri, selection),
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