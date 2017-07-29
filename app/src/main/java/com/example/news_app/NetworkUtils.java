package com.example.news_app;

import android.net.Uri;
import android.util.Log;

import com.example.news_app.data.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Leonard on 6/20/2017.
 */

public class NetworkUtils {
    public static final String TAG = "NetworkUtils";

    public static final String Base_Url= "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=3294ba5ab5314f35a0fb107604ebf5ab";

    //public static final String Query_Param = "q";

    //left it the same but just erased the param line.
    public static URL makeURL (){
        Uri uri = Uri.parse(Base_Url).buildUpon()
                .build();
        URL url = null;
        try {
            String urlString = uri.toString();
            Log.d(TAG, "URL: " +urlString);
            url = new URL(uri.toString());

        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            //made this part with less lines but
            String result = (scanner.hasNext()) ? scanner.next() : null;
            return result;
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    //method to parse the json string.
    public static ArrayList<Article> parseJSON(String json) throws JSONException{
        ArrayList<Article> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray news = main.getJSONArray("articles");

        for(int i = 0; i < news.length(); i++){
            JSONObject piece = news.getJSONObject(i);
            String title = piece.getString("title");
            String author = piece.getString("author");
            String description = piece.getString("description");
            String url = piece.getString("url");
            String publishedAt = piece.getString("publishedAt");
            String urlToImage = piece.getString("urlToImage");

            result.add(new Article(title,author ,description,url,urlToImage,publishedAt));
        }
        //determines if the right amount of articles entered the array.
        Log.d(TAG, "final articles size: " + result.size());

        return result;
    }
}
