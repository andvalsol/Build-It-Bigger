package com.udacity.gradle.builditbigger.paid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.builditbigger.AndroidJokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class GetJokeFromEndpointAsyncTask extends AsyncTask<String, Void, String> {

    private static MyApi mMyApiService = null;

    //Use a weak reference to achieve a cleaner memory management
    private WeakReference<ProgressBar> mProgressBarWeakReference;

    GetJokeFromEndpointAsyncTask(ProgressBar progressBar) {
        mProgressBarWeakReference = new WeakReference<>(progressBar);
    }

    @Override
    protected String doInBackground(String... strings) {
        //Check if mMyApiService instance is null
        if (mMyApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/") //10.0.2.2 is localhost's IP address in Android emulator
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) {
                            request.setDisableGZipContent(true);
                        }
                    });

            mMyApiService = builder.build();
        }

        //Get the joke
        String joke = strings[0];

        try {
            return mMyApiService.getJoke(joke).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        //Remove the progress bar visibility
        removeProgressBar(mProgressBarWeakReference.get());

        //Open the AndroidJokeActivity
        openAndroidJokeActivity(mProgressBarWeakReference.get().getContext(), joke);
    }

    private void removeProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }

    private void openAndroidJokeActivity(Context context, String joke) {
        //Pass the joke to the androidJokes library and launch the AndroidJokeActivity class
        Intent intent = new Intent(context, AndroidJokeActivity.class);
        intent.putExtra("joke", joke);
        context.startActivity(intent);
    }

}