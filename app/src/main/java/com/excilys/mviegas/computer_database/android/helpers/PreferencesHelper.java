package com.excilys.mviegas.computer_database.android.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import javax.inject.Inject;

/**
 * Helper pour gérer les préférences de l'application.
 *
 * @author VIEGAS Mickael
 */
@SuppressLint("CommitPrefEdits")
public final class PreferencesHelper {
    private final String SHARED_PREFERENCES = "StorageModule";
    private SharedPreferences.Editor mEditor;

    public static final class Keys {
        public static final String API_KEY = "PREFERENCES_API_KEY";
        public static final String AUTHORIZATION_HEADER = "PREFERENCES_AUTHORIZATION_HEADER";
        public static final String LOGINS_SAVED = "PREFERENCES_LOGINS_SAVED";
    }

    private SharedPreferences mSharedPreferences;

    @Inject
    PreferencesHelper(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String apiKey() {
        return this.mSharedPreferences.getString(Keys.API_KEY, null);
    }

    public String authorizationHeader() {
        return this.mSharedPreferences.getString(Keys.AUTHORIZATION_HEADER, null);
    }

    public Set<String> loginsSaved() {
        return this.mSharedPreferences.getStringSet(Keys.AUTHORIZATION_HEADER, null);
    }

    public PreferencesHelper apiKey(String apiKey) {
        this.mSharedPreferences.edit().putString(Keys.API_KEY, apiKey);
        return this;
    }

    public PreferencesHelper authorizationHeader(String authorizationHeader) {
        this.mEditor.putString(Keys.AUTHORIZATION_HEADER, authorizationHeader);
        return this;
    }

    public PreferencesHelper loginsSaved(Set<String> loginsSaved) {
        this.mEditor.putStringSet(Keys.AUTHORIZATION_HEADER, loginsSaved);
        return this;
    }

    public void apply() {
        mEditor.apply();
        mEditor = null;
    }

    public PreferencesHelper edit() {
        mEditor = this.mSharedPreferences.edit();
        return this;
    }

}
