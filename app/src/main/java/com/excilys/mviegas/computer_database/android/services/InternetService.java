package com.excilys.mviegas.computer_database.android.services;

import rx.Observable;

/**
 * @author VIEGAS Mickael
 */
public interface InternetService {
    boolean isConnected();

    Observable<Boolean> subscribeNetworkState();
}
