package com.example.news_app;

import android.os.AsyncTask;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Leonard on 7/28/2017.
 */

public class NewsJob extends JobService{
    AsyncTask mBGTask;

    @Override
    public boolean onStartJob(final JobParameters params) {
        mBGTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                Toast.makeText(NewsJob.this, "New articles found.", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                RefreshTasks.refreshArticles(NewsJob.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o){
                jobFinished(params,false);
                super.onPostExecute(o);
            }
        };
        mBGTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        if (mBGTask != null){
            mBGTask.cancel(false);
        }
        return true;
    }
}
