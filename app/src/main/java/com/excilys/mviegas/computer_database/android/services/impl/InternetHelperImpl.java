package com.excilys.mviegas.computer_database.android.services.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.excilys.mviegas.computer_database.android.services.InternetService;

/**
 * Implémentation de {@link InternetService}.
 *
 * @author VIEGAS Mickael
 */
public class InternetHelperImpl implements InternetService {
    private Context context;
    private NetworkInfo networkInfo;

    public InternetHelperImpl(Context context) {
        this.context = context;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.networkInfo = connectivityManager.getActiveNetworkInfo();
    }

    @Override
    public boolean isConnected() {
        return networkInfo != null && networkInfo.isConnected();
    }
}
