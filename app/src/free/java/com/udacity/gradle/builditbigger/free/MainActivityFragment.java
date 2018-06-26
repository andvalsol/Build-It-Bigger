package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.builditbigger.AndroidJokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.GetJokeFromEndpointAsyncTask;
import com.udacity.gradle.builditbigger.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    
    private ProgressBar mProgressBar;
    public static String mJoke;
    public static Boolean mIsInterstitialAdLoaded = false; //Set initial value as false by default
    private InterstitialAd mInterstitialAd;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_activity, container, false);
        
        //Get the progress bar
        mProgressBar = root.findViewById(R.id.progress_bar);
        
        //Initialize the interstitial ad
        mInterstitialAd = new InterstitialAd(getContext());
        //Set the ad unit id to the interstitial ad
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //Open the Android with the respective joke
                if (mJoke != null) {
                    //Request a new add
                    requestAd();
                    
                    //Tell the joke
                    tellJoke();
                }
            }
        });
        
        root.findViewById(R.id.btn_tell_joke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get if the interstitial ad is loaded
                mIsInterstitialAdLoaded = mInterstitialAd.isLoaded();
                
                //Show the ad it's loaded
                if (mIsInterstitialAdLoaded) mInterstitialAd.show();
                else tellJoke();
                
                //Show the progress bar
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        
        //Request the ad
        requestAd();
        
        return root;
    }

    private void tellJoke() {
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

    private void setProgressBarVisibility(int visibility) {
        mProgressBar.setVisibility(visibility);
    }
    
    private void openAndroidJokeActivity(String joke) {
        Intent intent = new Intent(getActivity(), AndroidJokeActivity.class);
        intent.putExtra(getString(R.string.joke), joke);
        
        startActivity(intent);
    }
    
    private void requestAd() {
        //Request the ad
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}