package com.example.news_app.data;

import android.provider.BaseColumns;

/**
 * Created by Leonard on 7/28/2017.
 */

//Creating the contract for the database and the variables for the columns.
public class Contract {
    public static class TABLE_ARTICLES implements BaseColumns{
        public static final String TABLE_NAME = "articlez";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_PUBLISHED_AT = "publishedAt";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_THUMBURL = "urlToImage";
        public static final String COLUMN_NAME_URL = "url";
    }
}
