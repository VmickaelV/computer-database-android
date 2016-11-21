package com.excilys.mviegas.computer_database.android.dagger.modules;

import android.content.Context;

import com.excilys.mviegas.computer_database.android.api.AuthenticationApi;
import com.excilys.mviegas.computer_database.android.interceptors.AuthenticationRequestInterceptor;
import com.excilys.mviegas.computer_database.android.services.AuthenticationService;
import com.excilys.mviegas.computer_database.android.services.CompanyService;
import com.excilys.mviegas.computer_database.android.services.ComputerService;
import com.excilys.mviegas.computer_database.android.services.InternetService;
import com.excilys.mviegas.computer_database.android.services.impl.AuthenticationServiceImpl;

import org.mockito.Mockito;

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
public class MockServiceModule {

    @Provides
    @Singleton
    public ComputerService provideComputerService(Retrofit retrofit) {
        return Mockito.mock(ComputerService.class);
    }

    @Provides
    @Singleton
    public CompanyService provideCompanyService(Retrofit retrofit) {
        return Mockito.mock(CompanyService.class);
    }

    @Provides
    @Singleton
    public InternetService provideInternetService(Context context) {
        return Mockito.mock(InternetService.class);
    }

    @Provides
    @Singleton
    public AuthenticationService provideAuthenticationService(AuthenticationRequestInterceptor authenticationRequestInterceptor, AuthenticationApi authenticationApi) {
        return new AuthenticationServiceImpl(authenticationRequestInterceptor, authenticationApi);
    }
}
