package com.excilys.mviegas.computer_database.android.api;

import retrofit2.http.GET;
import rx.Observable;

/**
 * API REST d'authentiication.
 *
 * @author VIGEAS Mickael
 */

public interface AuthenticationApi {

    @GET("login")
    Observable<Boolean> login();
}
