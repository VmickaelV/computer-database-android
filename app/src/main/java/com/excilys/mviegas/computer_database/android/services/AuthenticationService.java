package com.excilys.mviegas.computer_database.android.services;

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
