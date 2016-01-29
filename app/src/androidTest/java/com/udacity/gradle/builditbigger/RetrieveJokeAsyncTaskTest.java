package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//http://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
public class RetrieveJokeAsyncTaskTest extends AndroidTestCase implements
        RetrieveJokeAsyncTask.OnJokeRetrievedListener {
    final CountDownLatch signal = new CountDownLatch(1);
    final RetrieveJokeAsyncTask myTask = new RetrieveJokeAsyncTask(this);

    String joke;

    public void testVerifyJokeResponse() throws InterruptedException {
        myTask.execute();

        signal.await(25, TimeUnit.SECONDS);

        assertNotNull(joke);
    }

    @Override
    public void onJokeRetrieved(String joke) {
        this.joke = joke;
        signal.countDown();
    }
}
