package com.excilys.mviegas.computer_database.android.dagger.modules;

import android.content.Context;

import com.excilys.mviegas.computer_database.android.api.AuthenticationApi;
import com.excilys.mviegas.computer_database.android.api.ComputerApi;
import com.excilys.mviegas.computer_database.android.interceptors.AuthenticationRequestInterceptor;
import com.excilys.mviegas.computer_database.android.services.AuthenticationService;
import com.excilys.mviegas.computer_database.android.services.ComputerService;
import com.excilys.mviegas.computer_database.android.services.InternetService;
import com.excilys.mviegas.computer_database.android.services.impl.AuthenticationServiceImpl;
import com.excilys.mviegas.computer_database.android.services.impl.ComputerServiceImpl;
import com.excilys.mviegas.computer_database.android.services.impl.InternetHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module gérant les services de l'application.
 *
 * @author VIEGAS Mickael
 */
@Module(includes = {ContextModule.class, ApiModule.class})
public class ServiceModule {

    @Provides
    @Singleton
    public ComputerService provideComputerService(Retrofit retrofit) {
        return new ComputerServiceImpl(retrofit.create(ComputerApi.class));
    }

    @Provides
    @Singleton
    public InternetService provideInternetService(Context context) {
        return new InternetHelperImpl(context);
    }

    @Provides
    @Singleton
    public AuthenticationService provideAuthenticationService(AuthenticationRequestInterceptor authenticationRequestInterceptor, AuthenticationApi authenticationApi) {
        return new AuthenticationServiceImpl(authenticationRequestInterceptor, authenticationApi);
    }
}
