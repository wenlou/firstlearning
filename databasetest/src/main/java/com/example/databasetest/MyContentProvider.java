package com.example.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    public static final int BOOK_DIR=0;
     public static final int BOOK_ITEM=1;
    public static final int CATEGORY_DIR=2;
    public static final int CATEGORY_ITEM=3;
    private static UriMatcher sUriMatcher;
    public static  final  String AUTHORITY="com.databasetest.provider";
    private MyDataBaseHelper mMyDataBaseHelper;
    static {
        sUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);
        sUriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
        sUriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIR);
        sUriMatcher.addURI(AUTHORITY,"category",CATEGORY_ITEM);
    }
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=mMyDataBaseHelper.getWritableDatabase();
        int deleteRows=0;
        switch (sUriMatcher.match(uri)){
            case BOOK_DIR:
                deleteRows=db.delete("BOOK",selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookID=uri.getPathSegments().get(1);
                deleteRows=db.delete("BOOk","id=?",new String[]{bookID});
                break;
            case CATEGORY_DIR:
                deleteRows=db.delete("Category",selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryID=uri.getPathSegments().get(1);
                deleteRows=db.delete("Category","id=?",new String[]{categoryID});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case BOOK_DIR:
               return "vnd.android.cursor.dir/vnd.com.databasetest.provider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.dir/vnd.com.databasetest.provider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.databasetest.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.dir/vnd.com.databasetest.provider.category";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=mMyDataBaseHelper.getWritableDatabase();
       Uri uriReturn=null;
        switch (sUriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
               long newBookID=db.insert("BOOK",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/book/"+newBookID);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long categoryID=db.insert("Category",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/category/"+categoryID);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
         mMyDataBaseHelper=new MyDataBaseHelper(getContext(),"BOOkStroe.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=mMyDataBaseHelper.getWritableDatabase();
        Cursor cursor=null;
        switch (sUriMatcher.match(uri)){
            case BOOK_DIR:
                cursor=db.query("BOOK",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case BOOK_ITEM:
                String bookID=uri.getPathSegments().get(1);
                cursor=db.query("BOOk",projection,"id=?",new String[]{bookID},null,null,sortOrder);
                break;
            case CATEGORY_DIR:
                cursor=db.query("Category",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryID=uri.getPathSegments().get(1);
                cursor=db.query("Category",projection,"id=?",new String[]{categoryID},null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db=mMyDataBaseHelper.getWritableDatabase();
        int updateRows=0;
        switch (sUriMatcher.match(uri)){
            case BOOK_DIR:
                updateRows=db.update("BOOK",values,selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookID=uri.getPathSegments().get(1);
                updateRows=db.update("BOOk", values,"id=?",new String[]{bookID});
                break;
            case CATEGORY_DIR:
                updateRows=db.update("Category",values,selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryID=uri.getPathSegments().get(1);
                updateRows=db.update("Category",values,"id=?",new String[]{categoryID});
                break;
            default:
                break;
        }
        return updateRows;
    }
}
