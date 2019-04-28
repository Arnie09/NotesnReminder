package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserNotes.db";
    public static final String TABLE_NAME = "Notes";
    public static final String COLUMN_1 = "SERIAL";
    public static final String COLUMN_2 = "MATTER";
    public static final String COLUMN_3 = "DATE";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (SERIAL INTEGER PRIMARY KEY AUTOINCREMENT,MATTER TEXT,DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String matter,String Date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2,matter);
        contentValues.put(COLUMN_3,Date);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
           return false;
        else
            return true;
    }

    public Cursor getallData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"SERIAL = ?",new String[]{id});
    }
    
}

