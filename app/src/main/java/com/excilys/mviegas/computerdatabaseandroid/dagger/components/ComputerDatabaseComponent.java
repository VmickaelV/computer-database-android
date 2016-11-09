package com.excilys.mviegas.computerdatabaseandroid.dagger.components;

import com.excilys.mviegas.computerdatabaseandroid.LoginActivity;
import com.excilys.mviegas.computerdatabaseandroid.dagger.modules.ServiceModule;

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
