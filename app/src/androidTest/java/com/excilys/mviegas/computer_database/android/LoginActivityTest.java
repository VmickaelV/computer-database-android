package com.excilys.mviegas.computer_database.android;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.excilys.mviegas.computer_database.android.activities.LoginActivity;
import com.excilys.mviegas.computer_database.android.applications.ComputerDatabaseApplication;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by excilys on 08/11/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class, true);

	@Inject
    OkHttpClient mOkHttpClient;

	@Before
	public void setUp() throws Exception {
		mOkHttpClient = ((ComputerDatabaseApplication) mActivityRule.getActivity().getApplication()).getComputerDatabaseComponent().getOkHttpClient();
		assertNotNull(mOkHttpClient);

		IdlingResource idlingResource = OkHttp3IdlingResource.create("okhttpclient", mOkHttpClient);
		Espresso.registerIdlingResources(idlingResource);
	}

	@Test
	public void goodLogin() throws Exception {
		onView(withId(R.id.computer_list)).check(ViewAssertions.doesNotExist());
		onView(withId(R.id.login)).perform(typeText("admin"), pressImeActionButton());
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.password)).perform(typeText("admin"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.sign_in_button)).perform(click());

		onView(withId(R.id.computer_list)).check(ViewAssertions.matches(isCompletelyDisplayed()));

		onView(withId(R.id.computer_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

		TestUtils.loop();
	}

	@Test
	public void wrongLogin() throws Exception {
		onView(withId(R.id.login)).perform(typeText("a"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.password)).perform(typeText(""));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.sign_in_button)).perform(click());

		onView(withId(R.id.errors_view)).check(ViewAssertions.matches(Matchers.allOf(isDisplayed(), withText(R.string.wrong_sign_in))));
	}
}
