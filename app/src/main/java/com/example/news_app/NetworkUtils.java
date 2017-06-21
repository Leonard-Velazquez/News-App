package com.example.news_app;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Leonard on 6/20/2017.
 */

public class NetworkUtils {

    public static final String Base_Url= "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=3294ba5ab5314f35a0fb107604ebf5ab";

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
}
