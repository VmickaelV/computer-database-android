package com.excilys.mviegas.computerdatabaseandroid.dagger.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module gérant l'instance {@link Application}.
 *
 * @author VIEGAS Mickael
 */

@Module
public class ApplcationModule {

    private final Application application;

    public ApplcationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

}
