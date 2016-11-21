package com.excilys.mviegas.computer_database.android.dagger.modules;

import com.excilys.mviegas.computer_database.android.BuildConfig;
import com.excilys.mviegas.computer_database.android.interceptors.AuthenticationRequestInterceptor;
import com.excilys.mviegas.computer_database.android.jackson.MyJacksonModule;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
	public Retrofit providesRetrofit(ObjectMapper objectMapper, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
				.baseUrl(BuildConfig.SERVER_URL)
				.client(okHttpClient)
				.addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public AuthenticationRequestInterceptor providesAuthenticationRequestInterceptor() {
        return new AuthenticationRequestInterceptor();
    }

	@Provides
	@Singleton
	public OkHttpClient providesOkHttpClient(AuthenticationRequestInterceptor authenticationRequestInterceptor) {
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(authenticationRequestInterceptor);
        httpClient.networkInterceptors().add(new StethoInterceptor());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

		return httpClient.build();
	}

	@Provides
	@Singleton
	public ObjectMapper providesObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new MyJacksonModule());

		return objectMapper;
	}
}
