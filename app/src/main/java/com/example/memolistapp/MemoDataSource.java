package com.example.memolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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

    public boolean insertMemo(Memo r) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();


            initialValues.put("subject", r.getSubject());
            initialValues.put("note", r.getNote());
            initialValues.put("priority", r.getPriority());
            initialValues.put("date", String.valueOf(r.getDate().getTimeInMillis()));

            didSucceed = database.insert("memo", null, initialValues) > 0;
        } catch (Exception e) {

        }
        return didSucceed;
    }

    public boolean updateMemo(Memo r) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) r.getMemoID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("subject", r.getSubject());
            updateValues.put("note", r.getNote());
            updateValues.put("priority", r.getPriority());
            updateValues.put("date", String.valueOf(r.getDate().getTimeInMillis()));

            didSucceed = database.update
                    ("memo", updateValues, "_id=" + rowId, null) > 0;
        } catch (Exception e) {

        }
        return didSucceed;
    }


    public int getLastMemoID() {
        int lastId;
        try {
            String query = "SELECT MAX(_id) FROM memo";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<Memo> getMemos(String sortBy, String sortOrder){
        ArrayList<Memo> memos = new ArrayList<>();
        try {
            String query = "SELECT * FROM memo ORDER BY "+ sortBy + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setSubject(cursor.getString(1));
                newMemo.setNote(cursor.getString(2));
                newMemo.setPriority(cursor.getString(3));

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(cursor.getString(4)));
                newMemo.setDate(calendar);

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            memos = new ArrayList<>();
        }
        return memos;
    }

    public Memo getMemo(int memoID) {
        Memo memo = null;
        try {
            String query = "SELECT * FROM memo WHERE _id = ?";
            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(memoID)});

            if (cursor.moveToFirst()) {
                memo = new Memo();
                memo.setMemoID(cursor.getInt(0));
                memo.setSubject(cursor.getString(1));
                memo.setNote(cursor.getString(2));
                memo.setPriority(cursor.getString(3));

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(cursor.getString(4)));
                memo.setDate(calendar);
            }
            cursor.close();
        } catch (Exception e) {
            memo = null;
        }
        return memo;
    }

    public boolean deleteMemo(int memoID) {
        boolean didSucceed = false;
        try {
            didSucceed = database.delete("memo", "_id = ?", new String[]{String.valueOf(memoID)}) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return didSucceed;
    }

}