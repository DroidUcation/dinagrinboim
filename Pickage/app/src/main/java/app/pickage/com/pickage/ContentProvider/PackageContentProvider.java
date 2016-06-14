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
public class PackageContentProvider extends ContentProvider {
    private static final String PROVIDER_NAME = "app.pickage.com.pickage.ContentProvider.PackageContentProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/package");
    private static final int PACKAGE = 1;
    private static final int PACKAGE_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "package", PACKAGE);
        uriMatcher.addURI(PROVIDER_NAME, "package/#", PACKAGE_ID);
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
     * Select package/s according to URI
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();//mafeila db
        qb.setTables(DBContract.PACKAGE_TBL);
        switch (uriMatcher.match(uri)) {
            case PACKAGE:
                break;
            case PACKAGE_ID:
                qb.appendWhere(DBContract.PACKAGE_ID + "=" + uri.getLastPathSegment());
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
            case PACKAGE:
                return "vnd.android.cursor.dir/vnd.app.pickage.com.pickage.ContentProvider.PackageContentProvider.package";
            case PACKAGE_ID:
                return "vnd.android.cursor.item/vnd.app.pickage.com.pickage.ContentProvider.PackageContentProvider.package";
        }
        return "";
    }

    /**
     * Add a new package record
     *
     * @return URI of the new inserted row
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = sqLiteDatabase.insert(DBContract.PACKAGE_TBL, "", values);
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
            case PACKAGE_ID:
                delCount = sqLiteDatabase.delete(
                        DBContract.PACKAGE_TBL,
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
            case PACKAGE_ID:
                updateCount = sqLiteDatabase.update(
                        DBContract.PACKAGE_TBL,
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
        String where = DBContract.PACKAGE_ID + " = " + idStr;
        if (!TextUtils.isEmpty(selection)) {
            where += " AND " + selection;
        }
        return where;
    }
}
//    private static final String PROVIDER_NAME = "app.pickage.com.pickage.ContentProvider.PackageContentProvider";
//
//    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/package");
//    private static final int PACKAGE = 1;
//    private static final int PACKAGE_ID = 2;
//    private static final UriMatcher uriMatcher = getUriMatcher();
//
//    private SQLiteDatabase sqLiteDatabase;
//    private DBHelper dbHelper;
//
//    private static UriMatcher getUriMatcher() {
//        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(PROVIDER_NAME, "package", PACKAGE);
//        uriMatcher.addURI(PROVIDER_NAME, "package/#", PACKAGE_ID);
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
//        sqLiteQueryBuilder.setTables(DBContract.PACKAGE_TBL);
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
//            case PACKAGE:
//                return "vnd.android.cursor.dir/vnd.app.pickage.com.pickage.ContentProvider.PackageContentProvider.package";
//            case PACKAGE_ID:
//                return "vnd.android.cursor.item/vnd.app.pickage.com.pickage.ContentProvider.PackageContentProvider.package";
//
//        }
//        return "";    }
//
//    @Nullable
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        long rowID = sqLiteDatabase.insert(DBContract.PACKAGE_TBL, "", values);
//        Uri _uri = ContentUris.withAppendedId(uri, rowID);
//        getContext().getContentResolver().notifyChange(_uri, null);
//        return _uri;
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        return sqLiteDatabase.delete(DBContract.PACKAGE_TBL,selection,selectionArgs);
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        return 0;
//    }