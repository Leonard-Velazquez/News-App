package com.example.news_app;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.news_app.Model.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycling;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (EditText)findViewById(R.id.editText);
        recycling = (RecyclerView)findViewById(R.id.recycler);

        recycling.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemNum = item.getItemId();
        if(itemNum == R.id.search){
            String s = search.getText().toString();
            NetworkTask task = new NetworkTask(s);
            task.execute();
        }
        return true;
    }


    public class NetworkTask extends AsyncTask<URL, Void, ArrayList<NewsItem>>{
        String query;

        NetworkTask(String s){
            query = s;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params){
            ArrayList<NewsItem> result =null;
            URL url = NetworkUtils.makeURL(query);
            try{
                String json =NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);
            }catch (IOException e){
                e.printStackTrace();
            }catch(JSONException e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data){
            super.onPostExecute(data);
            if(data != null){
                NewsAdapter adapter_news = new NewsAdapter(data, new NewsAdapter.ItemClickListener(){
                    @Override
                    public void onItemClick(int clickedItemIndex){
                        String url = data.get(clickedItemIndex).getUrl();
                        openWebPage(url);
                    }
                });
                recycling.setAdapter(adapter_news);
            }
        }

        public void openWebPage (String url){
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
    }
}
