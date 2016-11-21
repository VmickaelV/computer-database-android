package com.excilys.mviegas.computer_database.android;

import android.support.test.espresso.IdlingResource;

/**
 * @author Mickael
 */

public abstract class BaseIdlingResource implements IdlingResource {

    private ResourceCallback callback;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public final boolean isIdleNow() {
        boolean idle = getIdle();
        if (idle) {
            onTransitionToIdle();
        }
        return idle;
    }

    /**
     * Redéfinition de {@link #isIdleNow()}.
     *
     * @return true si c'est 'idle'
     */
    protected abstract boolean getIdle();

    /**
     * Callback appelé quand devient idle.
     */
    public final void onTransitionToIdle() {
        if (callback != null) {
            callback.onTransitionToIdle();
        }
    }

    @Override
    public final void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
        this.callback = callback;
    }
}

