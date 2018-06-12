package com.example.builditbigger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AndroidJokeActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_joke);
    
        TextView textView = findViewById(R.id.tv_joke);
        
        //Check that the intent is not null, if is not null we're getting a string containing a joke from the javaJokes library
        if (getIntent() != null) {
            Log.d("Intent", "The joke is: " + getIntent().getStringExtra("joke"));
            textView.setText(getIntent().getStringExtra("joke"));
        }
    }
}