package com.excilys.mviegas.computer_database.android;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.DatePicker;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * Created by excilys on 17/11/16.
 */

public final class ViewActions {
    /**
     * @param year
     * @param month (1-12)
     * @param day
     * @return
     */
    public static ViewAction setDateForDatePicker(final int year, final int month, final int day) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return Matchers.instanceOf(DatePicker.class);
            }

            @Override
            public String getDescription() {
                return "performing a setting date on Date Picker ...";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((DatePicker) view).updateDate(year, month - 1, day);
            }
        };
    }
}
