package com.example.news_app;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.JarException;

import com.example.news_app.Model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Leonard on 6/20/2017.
 */

public class NetworkUtils {

    public static final String Base_Url= "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=";

    public static final String Query_Param = "q";

    public static URL makeURL (String searchQuery){
        Uri uri = Uri.parse(Base_Url).buildUpon()
                .appendQueryParameter(Query_Param,searchQuery).build();
        URL url = null;
        try {
            String urlString = uri.toString();
            url = new URL(uri.toString());

        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    //Passes a url string and returns a Json string
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return  scanner.next();
            }else{
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<NewsItem> parseJSON(String json)throws JSONException{
        ArrayList<NewsItem> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("articles");

        for(int i=0; i < items.length(); i++){
            JSONObject item = items.getJSONObject(i);

            String author =item.getString("author");
            String title =item.getString("title");
            String description =item.getString("description");
            String url =item.getString("url");
            String urlToImage =item.getString("urlToImage");
            String publishDate =item.getString("publishedAt");

            NewsItem news = new NewsItem(author,title,description,url,urlToImage,publishDate);
            result.add(news);
        }
        return result;
    }
}
