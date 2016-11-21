package com.excilys.mviegas.computer_database.android;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.DatePicker;

import com.excilys.mviegas.computer_database.android.activities.LoginActivity;
import com.excilys.mviegas.computer_database.android.applications.ComputerDatabaseApplication;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.excilys.mviegas.computer_database.android.ViewActions.setDateForDatePicker;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
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
    private IdlingResource okHttp3IdlingResource;

    @Before
    public void setUp() throws Exception {
		mOkHttpClient = ((ComputerDatabaseApplication) mActivityRule.getActivity().getApplication()).getComputerDatabaseComponent().getOkHttpClient();
		assertNotNull(mOkHttpClient);

        okHttp3IdlingResource = OkHttp3IdlingResource.create("okhttpclient", mOkHttpClient);
        Espresso.registerIdlingResources(okHttp3IdlingResource);
    }

    @After
    public void tearDown() throws Exception {
        Espresso.unregisterIdlingResources(okHttp3IdlingResource);
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

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.computer_name_input)).perform(typeText("Asus nexus 5"));

        onView(withId(R.id.computer_introduced_date_input)).check(matches(withText(isEmptyString())));
        onView(withId(R.id.computer_introduced_date_button)).perform(click());
        onView(Matchers.<View>instanceOf(DatePicker.class)).perform(setDateForDatePicker(2014, 2, 20));
        onView(withText(android.R.string.ok)).perform(click());
        onView(withId(R.id.computer_introduced_date_input)).check(matches(withText(not(isEmptyString()))));

        onView(withId(R.id.computer_discontinued_date_input)).check(matches(withText(isEmptyString())));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.computer_discontinued_date_button)).perform(click());
        onView(Matchers.<View>instanceOf(DatePicker.class)).perform(setDateForDatePicker(2012, 3, 9));
        onView(withText(android.R.string.ok)).perform(click());
        onView(withId(R.id.computer_discontinued_date_input)).check(matches(withText("2012-03-09")));

        onView(withId(R.id.done_action)).perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.computer_created_label)))
                .check(doesNotExist());

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.computer_discontinued_date_button)).perform(click());
        onView(Matchers.<View>instanceOf(DatePicker.class)).perform(setDateForDatePicker(2016, 3, 9));
        onView(withText(android.R.string.ok)).perform(click());
        onView(withId(R.id.computer_discontinued_date_input)).check(matches(withText("2016-03-09")));

        onView(withId(R.id.done_action)).perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.computer_created_label)))
                .check(matches(isDisplayed()));
    }

	@Test
	public void wrongLogin() throws Exception {
		onView(withId(R.id.login)).perform(typeText("a"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.password)).perform(typeText(""));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.sign_in_button)).perform(click());

        onView(withId(R.id.errors_view)).check(ViewAssertions.matches(allOf(isDisplayed(), withText(R.string.wrong_sign_in))));
    }
}
