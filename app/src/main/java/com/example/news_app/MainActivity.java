package com.example.news_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.display);
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


    public class NetworkTask extends AsyncTask<URL, Void, String>{
        String query;

        NetworkTask(String s){
            query = s;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params){
            String result= null;
            URL url = NetworkUtils.makeURL(query);
            try{
                result = NetworkUtils.getResponseFromHttpUrl(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(s == null){
                textView.setText("No text available.");
            }else{
                textView.setText(s);
            }
        }
    }
}
