package com.udacity.gradle.builditbigger.free;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    
    private InterstitialAd mInterstitialAd;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_activity, container, false);
        
        root.findViewById(R.id.btn_tell_joke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });
        
        //Initialize the interstitial ad
        mInterstitialAd = new InterstitialAd(getContext());
        //Set the ad unit id to the interstitial ad
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                Log.d("Interstitial", "Ad not loaded with error code: " + i);
            }
        });
        
        //Request the ad
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
        
        return root;
    }
    
    private void tellJoke() {
        Log.d("Interstitial", "The interstitial ad is loaded: " + mInterstitialAd.isLoaded());
        
        if (mInterstitialAd.isLoaded()) {
            //If the interstitial add is loaded show it
            mInterstitialAd.show();
        }
        
//        //Get a joke from the Joker class
//        String joke = Joker.getJoke();
//        //Pass the mProgress bar, since we use that view as the context and also since we need to set its visibility to ge gone when the joke is loaded
//        new GetJokeFromEndpointsAsyncTask(mProgressBar).execute(joke);
    }
}