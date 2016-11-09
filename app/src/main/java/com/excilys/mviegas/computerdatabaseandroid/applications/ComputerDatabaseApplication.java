package com.excilys.mviegas.computerdatabaseandroid.applications;

import android.app.Application;

import com.excilys.mviegas.computerdatabaseandroid.dagger.components.ComputerDatabaseComponent;
import com.excilys.mviegas.computerdatabaseandroid.dagger.components.DaggerComputerDatabaseComponent;
import com.excilys.mviegas.computerdatabaseandroid.dagger.modules.ContextModule;
import com.facebook.stetho.Stetho;

import net.danlew.android.joda.JodaTimeAndroid;


/**
 * Class {@link Application}.
 *
 * @author VIEGAS Mickael
 */
public class ComputerDatabaseApplication extends Application {

    /**
     * Arbre de dépendances Dagger.
     */
    private ComputerDatabaseComponent computerDatabaseComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        JodaTimeAndroid.init(this);

        computerDatabaseComponent = DaggerComputerDatabaseComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public ComputerDatabaseComponent getComputerDatabaseComponent() {
        return computerDatabaseComponent;
    }
}

