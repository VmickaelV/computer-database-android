package com.excilys.mviegas.computerdatabaseandroid;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.excilys.mviegas.computerdatabaseandroid.TestUtils.loop;

/**
 * Created by excilys on 08/11/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class, true);

    @Test
    public void name() throws Exception {
        onView(withId(R.id.login)).perform(typeText("admin"));
        onView(withId(R.id.password)).perform(typeText("admin"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.sign_in_button)).perform(click());
        loop();
    }
}
