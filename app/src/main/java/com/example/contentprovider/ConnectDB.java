package com.example.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.contentprovider.MyContentProvider.DATABASE_NAME;
import static com.example.contentprovider.MyContentProvider.TABLE_NAME;

public class ConnectDB extends SQLiteOpenHelper {


    private  String CREATE_TABLE = "create table " + TABLE_NAME + "(id integer primary key autoincrement ,name text not null)";

    public ConnectDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
