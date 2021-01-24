package com.example.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "com.example.provider";
    private static String URL = "content://" + PROVIDER_NAME + "/users";
    public static Uri CONTENT_URI =Uri.parse(URL);

    static  final String id ="id";
    static  final String name ="name";
    static  final int uricode = 1;
    static  final UriMatcher uriMatcher;
    static  final HashMap<String , String> values = new HashMap<>();


    static final String TABLE_NAME = "users";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //connect to users table
        uriMatcher.addURI(PROVIDER_NAME , "users" ,uricode );
       //connect to a single user
        uriMatcher.addURI(PROVIDER_NAME , "users" ,uricode );
    }

    static  final String DATABASE_NAME = "Store" ;

    private SQLiteDatabase db;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int c = 0;
        if (uriMatcher.match(uri) == uricode){
            c=  db.delete(TABLE_NAME  , selection , selectionArgs);
        }else{
            throw new IllegalArgumentException("Unknown URi" + uri);
        }

        getContext().getContentResolver().notifyChange(uri , null);
        return  c;
    }

    @Override
    public String getType(Uri uri) {

        if (uriMatcher.match(uri) == uricode){
            return  "vnd.android.cursor.dir/users";
        }
        throw  new IllegalArgumentException("Unsupported Uri" + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_NAME , "" , values);
        if (rowID > 0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI , rowID);

            getContext().getContentResolver().notifyChange(_uri , null);
            return _uri;
        }
        throw new SQLException("Filed to add user into" + uri);
    }

    @Override
    public boolean onCreate() {

        Context ctx = getContext();
        ConnectDB connectDB = new ConnectDB(ctx);

        db = connectDB.getWritableDatabase();
        return db != null;


    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(TABLE_NAME);

        if (uriMatcher.match(uri) == uricode) {
            qb.setProjectionMap(values);
        }else {
            throw new IllegalArgumentException("Unknown URI" + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            sortOrder = id;
        }
        Cursor c = qb.query(db , projection , selection ,selectionArgs , null , null ,sortOrder );
        c.setNotificationUri(getContext().getContentResolver() , uri);
    return  c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
         int c = 0;
        if (uriMatcher.match(uri) == uricode){
           c=  db.update(TABLE_NAME , values , selection , selectionArgs);
        }else{
            throw new IllegalArgumentException("Unknown URi" + uri);
        }

        getContext().getContentResolver().notifyChange(uri , null);
        return  c;

    }
}