package com.excilys.mviegas.computerdatabaseandroid.dagger.modules;

import android.content.Context;

import com.excilys.mviegas.computerdatabaseandroid.api.AuthenticationApi;
import com.excilys.mviegas.computerdatabaseandroid.api.ComputerApi;
import com.excilys.mviegas.computerdatabaseandroid.retrofit.interceptors.AuthenticationRequestInterceptor;
import com.excilys.mviegas.computerdatabaseandroid.services.AuthenticationService;
import com.excilys.mviegas.computerdatabaseandroid.services.ComputerService;
import com.excilys.mviegas.computerdatabaseandroid.services.InternetService;
import com.excilys.mviegas.computerdatabaseandroid.services.impl.AuthenticationServiceImpl;
import com.excilys.mviegas.computerdatabaseandroid.services.impl.ComputerServiceImpl;
import com.excilys.mviegas.computerdatabaseandroid.services.impl.InternetHelperImpl;

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
