package com.udacity.gradle.jokedisplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {
    public static final String JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        String jokeText = getIntent().getStringExtra(JOKE);

        if(jokeText != null){
            TextView joke = (TextView) findViewById(R.id.joke);
            joke.setText(jokeText);
        }
    }
}