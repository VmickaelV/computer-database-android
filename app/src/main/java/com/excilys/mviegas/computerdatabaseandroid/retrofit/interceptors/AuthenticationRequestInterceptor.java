package com.excilys.mviegas.computerdatabaseandroid.retrofit.interceptors;

import android.text.TextUtils;
import android.util.Log;

import com.excilys.mviegas.computerdatabaseandroid.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * {@link Interceptor} pour rajouter les données d'identification.
 *
 * @author VIEGAS Mickael
 */
public final class AuthenticationRequestInterceptor implements Interceptor {
    private static final String TAG = "AuthenticationRequestIn";

    private String mAuthorizationHeader = "";

    public void setAuthorizationHeader(String mAuthorizationHeader) {
        Log.d(TAG, "setAuthorizationHeader() called with: mAuthorizationHeader = [" + mAuthorizationHeader + "]");
        this.mAuthorizationHeader = mAuthorizationHeader;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Log.d(TAG, "intercept() called with: chain = [" + chain + "]");
        Log.d(TAG, "intercept: " + mAuthorizationHeader);
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header("User-Agent", "Client Android : " + BuildConfig.APPLICATION_ID);
        if (!TextUtils.isEmpty(mAuthorizationHeader)) {
            Log.d(TAG, "intercept: " + mAuthorizationHeader);
            requestBuilder.header("Authorization", mAuthorizationHeader);
        }
        requestBuilder.method(original.method(), original.body());

        return chain.proceed(requestBuilder.build());
    }
}
