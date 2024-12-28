package com.example.suivipatient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteSuivi extends SQLiteOpenHelper {
    public SQLiteSuivi(Context context, String name, SQLiteDatabase.CursorFactory factory,
                       int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String Sql = "create table patient (id INTEGER PRIMARY KEY AUTOINCREMENT,nom text NOT NULL,prenom text NOT NULL,date text NOT  NULL,tension INTEGER,rythme INTEGER);";
        db.execSQL(Sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

