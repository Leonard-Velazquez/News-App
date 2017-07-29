package com.example.news_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.news_app.data.Article;
import com.example.news_app.data.DBHelper;
import com.example.news_app.data.DatabseUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Leonard on 7/28/2017.
 */

public class RefreshTasks {
    public static final String ACTION_REFRESH = "refresh";

    public static void refreshArticles(Context context){
        //create a new arraylist to hold new articles.
        ArrayList<Article> result = null;

        URL url = NetworkUtils.makeURL();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            //delete the old articles
            DatabseUtils.deleteAll(db);

            //get the new articles json, parse and insert into the database
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DatabseUtils.bulkInsert(db,result);
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        db.close();
    }
}