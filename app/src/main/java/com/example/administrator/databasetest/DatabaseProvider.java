package com.example.administrator.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseProvider extends ContentProvider {
    private String TAG="-------DatabaseProvider--------";
    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;
    public static final String AUTHORITY = "com.example.administrator.databasetest.DatabaseProvider";
    private static UriMatcher uriMatcher;
    private MyDatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);

    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 2);
        Log.d(TAG, "onCreate: ");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("book", strings, s, strings1, null, null, s1);
                break;
            case BOOK_ITEM:
                String bookID = uri.getPathSegments().get(1);
                cursor = db.query("book", strings, "id=?", new String[]{bookID}, null, null, s1);
                break;
            case CATEGORY_DIR:
                cursor = db.query("category", strings, s, strings1, null, null, s1);
                break;
            case CATEGORY_ITEM:
                String categoryID = uri.getPathSegments().get(1);
                cursor = db.query("category", strings, "id=?", new String[]{categoryID}, null, null, s1);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/" +
                        "com.example.administrator.databasetest.DatabaseProvider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/" +
                        "com.example.administrator.databasetest.DatabaseProvider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/" +
                        "com.example.administrator.databasetest.DatabaseProvider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/" +
                        "com.example.administrator.databasetest.DatabaseProvider.category";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri urireturn = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                Long newBookID = db.insert("book", null, contentValues);
                urireturn = Uri.parse("content://" + AUTHORITY + "/book/" + newBookID);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                Long newCategoryID = db.insert("category", null, contentValues);
                urireturn = Uri.parse("content://" + AUTHORITY + "/category/" + newCategoryID);
                break;
        }
        return urireturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deleteRows = db.delete("book", s, strings);
                break;
            case BOOK_ITEM:
                String bookID = uri.getPathSegments().get(1);
                deleteRows = db.delete("book", "id=?", new String[]{bookID});
                break;
            case CATEGORY_DIR:
                deleteRows = db.delete("category", s, strings);
                break;
            case CATEGORY_ITEM:
                String categoryID = uri.getPathSegments().get(1);
                deleteRows = db.delete("category", "id=?", new String[]{categoryID});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updateRows = db.update("book", contentValues, s, strings);
                break;
            case BOOK_ITEM:
                String bookID = uri.getPathSegments().get(1);
                updateRows = db.update("book", contentValues, "id=?", new String[]{bookID});
                break;
            case CATEGORY_DIR:
                updateRows = db.update("category", contentValues, s, strings);
                break;
            case CATEGORY_ITEM:
                String categoryID = uri.getPathSegments().get(1);
                updateRows = db.update("category", contentValues, "id=?", new String[]{categoryID});
                break;
            default:
                break;
        }
        return updateRows;
    }
}
