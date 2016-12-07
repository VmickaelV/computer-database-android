package com.excilys.mviegas.computer_database.android.services.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.excilys.mviegas.computer_database.android.services.InternetService;
import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import rx.Observable;
import rx.functions.Func1;

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

        // Pour se rappeler de la façon de faire sans Rx
//        IntentFilter intentFilterConnectivity = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
//        context.registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
//
//                if (intent.getExtras() != null) {
//                    // On récupère à chaque fois le réseau actif car il change si on change de réseau
//                    networkInfo = connectivityManager.getActiveNetworkInfo();
//                    if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
//                        Log.i(TAG, "Network " + networkInfo.getTypeName() + " connected");
//                        for (Subscriber<? super Boolean> subscriber : subscribers) {
//                            subscriber.onNext(true);
//                        }
//                    } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.TRUE)) {
//                        Log.d(TAG, "There's no network connectivity");
//                        for (Subscriber<? super Boolean> subscriber : subscribers) {
//                            subscriber.onNext(false);
//                        }
//                    }
//                }
//            }
//        }, intentFilterConnectivity);
    }

    @Override
    public boolean isConnected() {
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Observable<Boolean> subscribeNetworkState() {
        return ReactiveNetwork.observeNetworkConnectivity(context).map(new Func1<Connectivity, Boolean>() {
            @Override
            public Boolean call(Connectivity connectivity) {
                return connectivity.getState() == NetworkInfo.State.CONNECTED;
            }
        });
    }
}
