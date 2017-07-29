package com.example.news_app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by Leonard on 7/28/2017.
 */

/*This controls the refresh rate of finding new articles that are changed in the json.
//I will control the time that the array will update when new articles are found.
 */

public class ScheduleUtilities {
    private static final int SCHEDULE_INTERVAL_MINUTES = 30;
    private static final int SYNC_FLEXING_SECONDS = 30;
    private static final String NEWS_JOB_TAG = "news_job_tag";

    private static boolean sIntialiazed;

    synchronized public static void scheduleRefresh(@NonNull final Context context){
        if(sIntialiazed) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);


        //this makes the service using the NewsJob class and a constraint of having internet,
        //it will go forever or until the service is manually terminated, and will refresh after the
        // time in the trigger.
        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class)
                .setTag(NEWS_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,
                        SCHEDULE_INTERVAL_MINUTES + SYNC_FLEXING_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constraintRefreshJob);
        sIntialiazed = true;
    }
}
