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
import com.example.javajokes.Joker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    
    private ProgressBar mProgressBar;
    public static String mJoke;
    public static Boolean mIsInterstitialAdLoaded = false; //Set initial value as false by default
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
                //Open the Android with the respective joke
                if (mJoke != null) {
                    //Open the AndroidJokeActivity with the proper joke
                    openAndroidJokeActivity(mJoke);
                }
            }
        });
        
        root.findViewById(R.id.btn_tell_joke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get if the interstitial ad is loaded
                mIsInterstitialAdLoaded = mInterstitialAd.isLoaded();
                
                //Tell the joke
                tellJoke();
                
                //Show the ad it's loaded
                if (mIsInterstitialAdLoaded) mInterstitialAd.show();
                
                //Show the progress bar
                mProgressBar.setVisibility(View.VISIBLE);
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
        intent.putExtra(getString(R.string.joke), joke);
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
        new GetJokeFromEndpointAsyncTask(mProgressBar).execute(joke);
    }
}