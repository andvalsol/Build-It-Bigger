package com.example.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import com.example.javajokes.Joker;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    
    @Test
    public void getJokes() {
        //Get a joke from the Joker class
        String joke = Joker.getJoke();
        
        //Test that we're passing to the AsyncTask not empty strings
        assertNotEquals("", joke);
    }
}