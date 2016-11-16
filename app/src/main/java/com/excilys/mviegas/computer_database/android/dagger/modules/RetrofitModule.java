package com.excilys.mviegas.computer_database.android.dagger.modules;

import com.excilys.mviegas.computer_database.android.BuildConfig;
import com.excilys.mviegas.computer_database.android.interceptors.AuthenticationRequestInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Module gérant des instances liés à Retrofit.
 *
 * @author VIEGAS Mickael
 */
@Module
public class RetrofitModule {

    private static final String TAG = "RetrofitModule";

    @Provides
    @Singleton
    public Retrofit providesRetrofit(AuthenticationRequestInterceptor authenticationRequestInterceptor) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(authenticationRequestInterceptor);

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(httpClient.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public AuthenticationRequestInterceptor providesAuthenticationRequestInterceptor() {
        return new AuthenticationRequestInterceptor();
    }
}
