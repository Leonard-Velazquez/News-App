package com.example.news_app.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import static com.example.news_app.data.Contract.TABLE_ARTICLES.*;

/**
 * Created by Leonard on 7/28/2017.
 */

public class DatabseUtils {
    static final String TAG = "DB Utils";

    //get the infor from the Database
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_AT
        );
        Log.d(TAG, cursor.toString());

        return cursor;
    }

    public static void bulkInsert(SQLiteDatabase db, ArrayList<Article> articles){

        //inserting the views that will be used from the database for later use when we call the
        //recycler and adding the new ones from the background.
        db.beginTransaction();
        Log.d(TAG, "Made it to bulkInsert");
        try{
            for(Article a : articles){
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE,a.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION,a.getDescription());
                cv.put(COLUMN_NAME_AUTHOR,a.getAuthor());
                cv.put(COLUMN_NAME_URL,a.getUrl());
                cv.put(COLUMN_NAME_THUMBURL,a.getUrlToImage());
                cv.put(COLUMN_NAME_PUBLISHED_AT,a.getPublishedAt());
                db.insert(TABLE_NAME,null,cv);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db){
        db.delete(TABLE_NAME,null,null);
    }
}
