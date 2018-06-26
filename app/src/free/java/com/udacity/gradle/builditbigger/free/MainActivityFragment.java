package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.builditbigger.AndroidJokeActivity;
import com.example.javajokes.Joker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;

import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    
    private String mJokeFromEndpoint;
    private ProgressBar mProgressBar;
    private Boolean mIsInterstitialAdLoaded;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_activity, container, false);
        
        //Get the progress bar
        mProgressBar = root.findViewById(R.id.progress_bar);
        
        //Initialize the interstitial ad
        final InterstitialAd mInterstitialAd = new InterstitialAd(getContext());
        //Set the ad unit id to the interstitial ad
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //If mJokeFromEndpoint is not null tell the joke
                if (mJokeFromEndpoint != null) openAndroidJokeActivity(mJokeFromEndpoint);
            }
        });
        
        root.findViewById(R.id.btn_tell_joke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get if the interstitial ad is loaded
                mIsInterstitialAdLoaded = mInterstitialAd.isLoaded();
                
                //Tell the joke
                tellJoke();
                
                //Show the ad it's loaded, else show the progress bar
                if (mIsInterstitialAdLoaded) mInterstitialAd.show();
                else mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        
        //Request the ad
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
        
        return root;
    }
    
    private void openAndroidJokeActivity(String joke) {
        //Pass the joke to the androidJokes library and launch the AndroidJokeActivity class
        Intent intent = new Intent(getContext(), AndroidJokeActivity.class);
        intent.putExtra("joke", joke);
        getContext().startActivity(intent);
    }
    
    /*
     * Explanation of the ad flow:
     * If the ad is loaded then wait after the user has closed the ad to open the AndroidJokeActivity,
     * if it's not loaded then use the onPostExecute() from the GetJokeFromEndpointAsyncTask to open the
     * AndroidJokeActivity. That way we ensure proper flow from ad and app content
     * */
    
    private void tellJoke() {
        //Get a joke from the Joker class
        String joke = Joker.getJoke();
        //Pass the mProgress bar, since we use that view as the context and also since we need to set its visibility to ge gone when the joke is loaded
        new GetJokeFromEndpointAsyncTask(mIsInterstitialAdLoaded, mProgressBar).execute(joke);
    }
}