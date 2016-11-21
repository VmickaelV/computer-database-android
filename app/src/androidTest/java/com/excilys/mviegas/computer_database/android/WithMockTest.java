package com.excilys.mviegas.computer_database.android;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.excilys.mviegas.computer_database.android.activities.LoginActivity;
import com.excilys.mviegas.computer_database.android.applications.ComputerDatabaseApplication;
import com.excilys.mviegas.computer_database.android.dagger.components.DaggerMockComputerDatabaseComponent;
import com.excilys.mviegas.computer_database.android.dagger.modules.ContextModule;
import com.excilys.mviegas.computer_database.android.services.InternetService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class WithMockTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class, true, false);

    @Inject
    OkHttpClient mOkHttpClient;

    @Before
    public void setUp() throws Exception {
        ((ComputerDatabaseApplication) InstrumentationRegistry.getTargetContext().getApplicationContext()).setComputerDatabaseComponent(
                DaggerMockComputerDatabaseComponent
                        .builder()
                        .contextModule(new ContextModule(InstrumentationRegistry.getTargetContext()))
                        .build());

        mActivityRule.launchActivity(new Intent());

    }

    @Test
    public void noInternet() throws Exception {
        InternetService internetService = ((ComputerDatabaseApplication) InstrumentationRegistry.getTargetContext().getApplicationContext()).getComputerDatabaseComponent().getInternetService();

        Mockito.when(internetService.isConnected()).thenReturn(false);
        onView(withId(R.id.computer_list)).check(ViewAssertions.doesNotExist());
        onView(withId(R.id.login)).perform(typeText("admin"), pressImeActionButton());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("admin"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.sign_in_button)).perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.no_internet_label)))
                .check(matches(isDisplayed()));
    }
}
