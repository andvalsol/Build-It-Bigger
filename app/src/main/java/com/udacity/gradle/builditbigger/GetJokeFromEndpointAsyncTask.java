package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class GetJokeFromEndpointAsyncTask extends AsyncTask<Void, Void, String> {
    
    private static MyApi mMyApiService = null;
    
    @Override
    protected String doInBackground(Void... voids) {
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
    
        //Get the joke from the API
        String joke;
    
        try {
            joke = mMyApiService.getJoke().execute().getData();
        } catch (IOException e) {
            joke = "Sorry, joke not loaded";
        }
        
        return joke;
    }
}