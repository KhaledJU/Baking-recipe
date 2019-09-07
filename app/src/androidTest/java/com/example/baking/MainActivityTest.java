package com.example.baking;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.regex.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void onRecycleViewItemClickedTest() {

        onView(ViewMatchers.withId(R.id.recipe_list))
                .inRoot(RootMatchers.withDecorView(Matchers.is(mActivityRule.getActivity()
                        .getWindow().getDecorView())))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    }

    @Test
    public void onRecycleerScrollTest() {

        onView(ViewMatchers.withId(R.id.recipe_list))
                .inRoot(RootMatchers.withDecorView(Matchers.is(mActivityRule.getActivity()
                        .getWindow().getDecorView())))
                .perform(RecyclerViewActions.scrollToPosition(0));

    }


    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}