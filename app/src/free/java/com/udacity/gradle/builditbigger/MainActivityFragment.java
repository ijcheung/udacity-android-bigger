package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.jokedisplay.DisplayJokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements
        RetrieveJokeAsyncTask.OnJokeRetrievedListener,
        Button.OnClickListener {
    private InterstitialAd mInterstitialAd;
    private ProgressBar mSpinner;

    private String mJoke;
    private boolean mAdShown = false;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mAdShown = false;
                if (mJoke != null) {
                    showJoke();
                }
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

        Button button = (Button) root.findViewById(R.id.tell_joke);
        button.setOnClickListener(this);

        mSpinner = (ProgressBar) root.findViewById(R.id.spinner);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void showJoke(){
        Intent intent = new Intent(getContext(), DisplayJokeActivity.class);
        intent.putExtra(DisplayJokeActivity.JOKE, mJoke);
        startActivity(intent);
    }

    @Override
    public void onClick(View view){
        new RetrieveJokeAsyncTask(this).execute();
        mSpinner.setVisibility(View.VISIBLE);
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mAdShown = true;
        }
    }

    @Override
    public void onJokeRetrieved(String joke) {
        mJoke = joke;
        mSpinner.setVisibility(View.INVISIBLE);
        if(!mAdShown){
            showJoke();
        }
    }
}
