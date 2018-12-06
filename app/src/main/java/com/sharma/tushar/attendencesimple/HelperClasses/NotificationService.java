package com.sharma.tushar.attendencesimple.HelperClasses;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

public class NotificationService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters params) {

        @SuppressLint("StaticFieldLeak") AsyncTask backgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                NotificationUtil.createNotification();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(params, false);
            }
        };

        backgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
