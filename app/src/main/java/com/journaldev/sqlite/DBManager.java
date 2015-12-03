package com.journaldev.sqlite;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {

        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String item1, String item2, String date) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ITEM1, item1);
        contentValue.put(DatabaseHelper.ITEM2, item2);
        contentValue.put(DatabaseHelper.DATE, date);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.ITEM1, DatabaseHelper.ITEM2, DatabaseHelper.DATE };

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public ArrayList<Information> getAll() {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null, null);
        ArrayList<Information> result = new ArrayList<Information>();

        Information information;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                information = new Information();
                information.iconId= cursor.getInt(0);
                information.item1 = cursor.getString(1);
                information.item2 = cursor.getString(2);
                information.date = cursor.getString(3);

                result.add(information);
            }
        }
        cursor.close();
        database.close();

        return result;
    }

    public int update(long _id, String item1, String item2, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ITEM1, item1);
        contentValues.put(DatabaseHelper.ITEM2, item2);
        contentValues.put(DatabaseHelper.DATE, date);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
