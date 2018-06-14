package com.udacity.gradle.builditbigger.paid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.javajokes.Joker;
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
        setProgressBarAsVisible();
        
        //Get a joke from the Joker class
        String joke = Joker.getJoke();
        //Pass the mProgress bar, since we use that view as the context and also since we need to set its visibility to ge gone when the joke is loaded
        new GetJokeFromEndpointAsyncTask(mProgressBar).execute(joke);
    }
    
    private void setProgressBarAsVisible() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
}