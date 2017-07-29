package com.example.news_app;

//adding the necessary imports needed to work fine.

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.news_app.data.Contract;
import com.example.news_app.data.DBHelper;
import com.example.news_app.data.DatabseUtils;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener{

    //creating private variables for later use in calls.
    static final String TAG = "Main Activity";
    private RecyclerView recycle;
    private NewsAdapter adapter;
    private ProgressBar progress;
    private Cursor curse;
    private SQLiteDatabase db;

    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "App is created");
        progress = (ProgressBar) findViewById(R.id.progressBar);

        //finding the recyclerView from the main activity layout and setting it with a linear layout
        recycle = (RecyclerView) findViewById(R.id.recycle);
        recycle.setLayoutManager(new LinearLayoutManager(this));

        //Creating the shared prefences to insert to constructor.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = preferences.getBoolean("isFirst", true);

        if (isFirst) {
            //using the shared preferences editor to change the boolean and committing it.
            Log.d(TAG, "Reached to Load");
            load();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(this);
    }

    //what to do when the app starts up and what to show on start
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "App is starting up");
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        curse = DatabseUtils.getAll(db);
        //entering the NewsAdapter
        Log.d(TAG, "Made it to the adapter call.--------------------");
        adapter = new NewsAdapter(curse,this);
        Log.d(TAG, "App is about to set to adapter");
        recycle.setAdapter(adapter);
        Log.d(TAG, "Finishing on Start");
    }

    //What to do in the event that the app stops or if user exits but leaves in the background.
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "app will stop for some reason");
        db.close();
        curse.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "create options");
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "item selected");
        int itemNum = item.getItemId();
        if (itemNum == R.id.search) {
            //caling the load to load info to the recycler view and the necessary views
            //if refresh is clicked on
            load();
        }
        return true;
    }

    //Creating the Async task loader
    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                Log.d(TAG, "loader loading");
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                RefreshTasks.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    //what to show or needs to show when its finished loading and setting the new DB entries
    //that was downloaded in the background
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data){
        Log.d(TAG, "loader finish");
        progress.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        curse = DatabseUtils.getAll(db);

        adapter = new NewsAdapter(curse,this);
        recycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader){
    }
    /*Get the position of the item that was clicked and do the necessary get requests
    //before opening the url in the web.
     */
    @Override
    public void onItemClick(Cursor curse, int clickedItemIndex){
        Log.d(TAG, "item clicked on");
        curse.moveToPosition(clickedItemIndex);
        String url = curse.getString(curse.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void load() {
        Log.d(TAG, "load");
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null,this).forceLoad();

    }
}
