package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sxj52 on 2017/1/21.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    public static final String CREAT_BOOK="create table BOOK(id integer primary key autoincrement,author text, price real,pages integer,name text)";
    public static final String CRET_CATEGORY="create table Category(id integer primary key autoincrement,category_name text,category_code integer ) ";
    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_BOOK);
        db.execSQL(CRET_CATEGORY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
