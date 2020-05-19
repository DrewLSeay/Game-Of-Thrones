package com.seay.game_of_thrones.activity;


import android.util.Log;
import android.widget.TextView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.seay.game_of_thrones.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MockWebServer webServer;

    @Before
    public void setup() throws Exception {
        webServer = new MockWebServer();

        webServer.enqueue(new MockResponse().setBody("Test"));

//        HttpUrl url = HttpUrl.parse("https://mysafeinfo.com/api/data");
//        HttpUrl baseUrl = webServer.url("");

        webServer.start();

        String baseUrl = webServer.url("/").toString();

        Log.d("tag", baseUrl);


    }

    @Test
    public void notAsSimpleNetworkCallTest() {

        // The TextView we want to check
        ViewInteraction textView = onView(withId(R.id.networkCallTextView));

        // Click the Button, triggering a TextView change
        onView(withId(R.id.networkCallButton))
                .perform(click());

        // Assert
        String expected = "GOT!";
        textView.check(matches(withText(expected)));
    }

    @Test
    public void simpleButtonPressTest() {

        // The TextView we want to check
        ViewInteraction textView = onView(withId(R.id.helloText));

        // Click the Button, triggering a TextView change
        onView(withId(R.id.button))
                .perform(click());

        // Assert
        String expected = "http://localhost:8080/";
        textView.check(matches(withText(expected)));
    }


    @After
    public void tearDown() throws Exception {
        webServer.shutdown();
    }
}
