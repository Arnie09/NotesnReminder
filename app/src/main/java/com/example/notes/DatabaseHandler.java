package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "User_Notes_remainders.db";
    public static final String TABLE_NAME = "Notes";
    public static final String COLUMN_1 = "SERIAL";
    public static final String COLUMN_2 = "MATTER";
    public static final String COLUMN_3 = "DATE";
    public static final String COLUMN_4 = "TYPE";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (SERIAL INTEGER PRIMARY KEY AUTOINCREMENT,MATTER TEXT,DATE TEXT,TYPE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long insertData(String matter,String Date,String Type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2,matter);
        contentValues.put(COLUMN_3,Date);
        contentValues.put(COLUMN_4,Type);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
           return -1;
        else
            return result;
    }

    public Cursor getallData_Notes(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE TYPE LIKE '%"+"NOTES"+"%'",null);
        return res;
    }

    public Cursor getallData_Reminders(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE TYPE LIKE '%"+"REMINDERS"+"%'",null);
        return res;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"SERIAL = ?",new String[]{id});
    }

    public Cursor getOneData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE SERIAL="+id,null);
        return res;
    }
    public boolean updateData(String id, String matter,String date,String Type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1,id);
        contentValues.put(COLUMN_2,matter);
        contentValues.put(COLUMN_3,date);
        contentValues.put(COLUMN_4,Type);
        db.update(TABLE_NAME,contentValues,"SERIAL = ?",new String[]{id});
        return true;
    }
}

