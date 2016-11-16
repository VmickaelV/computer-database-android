package com.excilys.mviegas.computer_database.android.dagger.components;

import com.excilys.mviegas.computer_database.android.LoginActivity;
import com.excilys.mviegas.computer_database.android.dagger.modules.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Racine de l'arbre de composetns Dagger.
 *
 * @author VIEGAS Mickael
 */
@Singleton
@Component(modules = {ServiceModule.class})
public interface ComputerDatabaseComponent {
    void inject(LoginActivity loginActivity);
}
