package com.excilys.mviegas.computerdatabaseandroid.dagger.modules;

import com.excilys.mviegas.computerdatabaseandroid.api.AuthenticationApi;
import com.excilys.mviegas.computerdatabaseandroid.api.ComputerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module Dagger gérant les instances de Dao Api Rest.
 *
 * @author VIEGAS Mickael
 */
@Module(includes = {RetrofitModule.class})
public class ApiModule {
    @Provides
    @Singleton
    public ComputerApi provideComputerApi(Retrofit retrofit) {
        return retrofit.create(ComputerApi.class);
    }

    @Provides
    @Singleton
    public AuthenticationApi provideAuthenticationApi(Retrofit retrofit) {
        return retrofit.create(AuthenticationApi.class);
    }
}
