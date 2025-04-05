package com.example.memolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class MemoDataSource {
    //pushTest
    private SQLiteDatabase database;
    private MemoDBHelper dbHelper;

    public MemoDataSource(Context context) {
        dbHelper = new MemoDBHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public boolean insertReminder(Memo r) {
        boolean didSucceed = false;
        try{
            ContentValues initialValues = new ContentValues();


            initialValues.put("subject", r.getSubject());
            initialValues.put("note", r.getNote());
            initialValues.put("date", String.valueOf(r.getDate().getTimeInMillis()));

            didSucceed = database.insert("reminder",null, initialValues) > 0;
        } catch (Exception e) {

        }
        return didSucceed;
    }

    public boolean updateReminder(Memo r) {
        boolean didSucceed = false;
        try{
            Long rowId = (long) r.getMemoID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("subject", r.getSubject());
            updateValues.put("note", r.getNote());
            updateValues.put("date", String.valueOf(r.getDate().getTimeInMillis()));

            didSucceed = database.update
                    ("reminder", updateValues, "_id=" + rowId, null)>0;
        } catch (Exception e) {

        }
        return didSucceed;
    }

}
//    public int getLastMemoID() {
//        int lastId;
//        try {
//            String query = "SELECT MAX(_id) FROM memo";
//            Cursor cursor = database.rawQuery(query, null);
//
//            cursor.moveToFirst();
//            lastId = cursor.getInt(0);
//            cursor.close();
//        } catch (Exception e) {
//            lastId = -1;
//        }
//        return lastId;
//    }

