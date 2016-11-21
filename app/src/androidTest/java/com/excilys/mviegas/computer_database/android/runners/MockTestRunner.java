package com.excilys.mviegas.computer_database.android.runners;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import com.excilys.mviegas.computer_database.android.applications.MockTestApplication;

public class MockTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockTestApplication.class.getName(), context);
    }
}