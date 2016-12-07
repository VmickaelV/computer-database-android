package com.excilys.mviegas.computer_database.android.services.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.excilys.mviegas.computer_database.android.services.InternetService;

/**
 * Implémentation de {@link InternetService}.
 *
 * @author VIEGAS Mickael
 */
public class InternetHelperImpl implements InternetService {
    private static final String TAG = "InternetHelperImpl";

    private Context context;
    private NetworkInfo networkInfo;

    public InternetHelperImpl(Context context) {
        this.context = context;

        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.networkInfo = connectivityManager.getActiveNetworkInfo();

        IntentFilter intentFilterConnectivity = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");

                if (intent.getExtras() != null) {
                    // On récupère à chaque fois le réseau actif car il change si on change de réseau
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        Log.i(TAG, "Network " + networkInfo.getTypeName() + " connected");
                    } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.TRUE)) {
                        Log.d(TAG, "There's no network connectivity");
                    }
                }
            }
        }, intentFilterConnectivity);
    }

    @Override
    public boolean isConnected() {
        return networkInfo != null && networkInfo.isConnected();
    }
}
