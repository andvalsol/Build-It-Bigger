package com.udacity.gradle.builditbigger.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.builditbigger.AndroidJokeActivity;
import com.udacity.gradle.builditbigger.GetJokeFromEndpointAsyncTask;
import com.udacity.gradle.builditbigger.R;

public class MainActivityFragment extends Fragment {

    private ProgressBar mProgressBar;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        view.findViewById(R.id.btn_tell_joke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });

        mProgressBar = view.findViewById(R.id.progress_bar);

        return view;
    }

    private void tellJoke() {
        setProgressBarVisibility(View.VISIBLE);

        //Pass the mProgress bar, since we use that view as the context and also since we need to set its visibility to ge gone when the joke is loaded
        new GetJokeFromEndpointAsyncTask() {
            @Override
            protected void onPostExecute(String joke) {
                //Remove the progress bar
                setProgressBarVisibility(View.GONE);
                
                //Open the AndroidJokeActivity
                openAndroidJokeActivity(joke);
            }
        }.execute();
    }
    
    private void openAndroidJokeActivity(String joke) {
        Intent intent = new Intent(getActivity(), AndroidJokeActivity.class);
        intent.putExtra(getString(R.string.joke), joke);
        
        startActivity(intent);
    }

    private void setProgressBarVisibility(int visibility) {
        mProgressBar.setVisibility(visibility);
    }
}