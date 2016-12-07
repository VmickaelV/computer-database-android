package com.excilys.mviegas.computer_database.android.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import javax.inject.Inject;

/**
 * Helper pour gérer les préférences de l'application.
 *
 * @author VIEGAS Mickael
 */

public final class PreferencesHelperForm {
    protected final String SHARED_PREFERENCES = "StorageModule";

    public static final class Keys {
        public static final String API_KEY = "PREFERENCES_API_KEY";
        public static final String AUTHORIZATION_HEADER = "PREFERENCES_AUTHORIZATION_HEADER";
        public static final String LOGINS_SAVED = "PREFERENCES_LOGINS_SAVED";
    }

    private SharedPreferences sharedPreferences;

    @Inject
    PreferencesHelperForm(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getApiKey() {
        return this.sharedPreferences.getString(Keys.API_KEY, null);
    }

    public String getAuthorizationHeader() {
        return this.sharedPreferences.getString(Keys.AUTHORIZATION_HEADER, null);
    }

    public Set<String> getLoginsSaved() {
        return this.sharedPreferences.getStringSet(Keys.AUTHORIZATION_HEADER, null);
    }

    public void setApiKey(String apiKey) {
        this.sharedPreferences.edit().putString(Keys.API_KEY, apiKey).apply();
    }

    public void setAuthorizationHeader(String authorizationHeader) {
        this.sharedPreferences.edit().putString(Keys.AUTHORIZATION_HEADER, authorizationHeader).apply();
    }

    public void setLoginsSaved(Set<String> loginsSaved) {
        this.sharedPreferences.edit().putStringSet(Keys.AUTHORIZATION_HEADER, loginsSaved).apply();
    }
}
