package com.excilys.mviegas.computerdatabaseandroid.applications;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by excilys on 07/11/16.
 */

public class ComputerDatabaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
