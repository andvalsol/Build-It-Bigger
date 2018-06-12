package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;

import com.example.builditbigger.AndroidJokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class GetJokeFromEndpointsAsyncTask extends AsyncTask<Pair<Context,String>, Void, String> {
    
    private static MyApi mMyApiService = null;
    private Context mContext;
    
    @Override
    protected String doInBackground(Pair<Context, String>... pairs) {
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
    
        //Get the context
        mContext = pairs[0].first;
        
        //Get the joke
        String joke = pairs[0].second;
    
        try {
            return mMyApiService.getJoke(joke).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    
    @Override
    protected void onPostExecute(String joke) {
        //Pass the joke to the androidJokes library and launch the AndroidJokeActivity class
        Intent intent = new Intent(mContext, AndroidJokeActivity.class);
        intent.putExtra("joke", joke);
        mContext.startActivity(intent);
    }
    
}
