package com.pc1.store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "store.db";

    public Db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE book(" +
                "id integer not null primary key AUTOINCREMENT," +
                "name varchar(250),autor varchar(100),price decimal(11,2)," +
                "stock integer,has_igv integer default 0,type integer,description text,created_at datetime default current_timestamp)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("delete from books");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public SQLiteDatabase getWrite(){
        return getWritableDatabase();
    }
    public SQLiteDatabase getRead(){
        return getReadableDatabase();
    }
}