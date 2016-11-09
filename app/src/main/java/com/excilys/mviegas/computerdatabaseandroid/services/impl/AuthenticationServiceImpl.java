package com.excilys.mviegas.computerdatabaseandroid.services.impl;

import android.util.Base64;

import com.excilys.mviegas.computerdatabaseandroid.api.AuthenticationApi;
import com.excilys.mviegas.computerdatabaseandroid.retrofit.interceptors.AuthenticationRequestInterceptor;
import com.excilys.mviegas.computerdatabaseandroid.services.AuthenticationService;

import javax.inject.Inject;

import rx.Observable;

/**
 * Implémentation {@link AuthenticationService}.
 *
 * @author VIEGAS Mickael
 */

public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationRequestInterceptor authenticationRequestInterceptor;
    private AuthenticationApi authenticationApi;

    @Inject
    public AuthenticationServiceImpl(AuthenticationRequestInterceptor authenticationRequestInterceptor, AuthenticationApi authenticationApi) {
        this.authenticationRequestInterceptor = authenticationRequestInterceptor;
        this.authenticationApi = authenticationApi;
    }

    @Override
    public Observable<Void> logout() {
        return null;
    }

    // TODO mettre un char[] ou un byte[] pour le mot de passe
    @Override
    public Observable<Boolean> signin(String login, String password) {
        String credentials = login + ":" + password;
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        authenticationRequestInterceptor.setAuthorizationHeader(basic);

        return authenticationApi.login();
    }
}
