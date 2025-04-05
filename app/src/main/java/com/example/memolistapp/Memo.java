package com.example.memolistapp;

import java.util.Calendar;

public class Memo {
    private int MemoID;
    private String subject;
    private String priority; // High Medium Low
    private String note;
    private Calendar date;
    public Memo() {
        MemoID = -1;
        date = Calendar.getInstance();
    }
    public int getMemoID() {
        return MemoID;
    }
    public void setMemoID(int i) {
        MemoID = i;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String s) {
        subject = s;
    }
    public String getPriority(){return priority;}
    public void setPriority(String s){this.priority = s;}
    public String getNote() {
        return note;
    }
    public void setNote(String s) {
        note = s;
    }
    public Calendar getDate() {
        return date;
    }
    public void setDate(Calendar c) {
        date = c;
    }

}
