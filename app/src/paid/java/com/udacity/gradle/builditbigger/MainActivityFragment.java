package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.gradle.jokedisplay.DisplayJokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements
        RetrieveJokeAsyncTask.OnJokeRetrievedListener,
        Button.OnClickListener {
    private ProgressBar mSpinner;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button button = (Button) root.findViewById(R.id.tell_joke);
        button.setOnClickListener(this);

        mSpinner = (ProgressBar) root.findViewById(R.id.spinner);

        return root;
    }

    @Override
    public void onClick(View view){
        new RetrieveJokeAsyncTask(this).execute();
        mSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onJokeRetrieved(String joke) {
        mSpinner.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getContext(), DisplayJokeActivity.class);
        intent.putExtra(DisplayJokeActivity.JOKE, joke);
        startActivity(intent);
    }
}
