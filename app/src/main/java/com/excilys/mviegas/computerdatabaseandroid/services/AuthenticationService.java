package com.excilys.mviegas.computerdatabaseandroid.services;

import rx.Observable;

/**
 * Service pour l'authentification.
 *
 * @author VIEGAS Mickael
 */
public interface AuthenticationService {
    Observable<Boolean> signin(String login, String password);

    Observable<Void> logout();
}
