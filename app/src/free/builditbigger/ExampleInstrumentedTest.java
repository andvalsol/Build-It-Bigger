package com.example.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    
    @Test
    public void getJokesFromAsyncTask() {
        //Get a joke from the Joker class
        GetJokeFromEndpointAsyncTask getJokeFromEndpointAsyncTask = new GetJokeFromEndpointAsyncTask();
        
        String joke = "";
        try {
            joke = getJokeFromEndpointAsyncTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            joke = "";
        }
    
        //Test that we're passing to the AsyncTask not empty strings
        assertNotEquals("", joke);
    }
}