package com.excilys.mviegas.computer_database.android.dagger.components;

import com.excilys.mviegas.computer_database.android.dagger.modules.MockServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Racine de l'arbre de composants Dagger.
 *
 * @author VIEGAS Mickael
 */
@Singleton
@Component(modules = {MockServiceModule.class})
public interface MockComputerDatabaseComponent extends BaseComputerDatabaseComponent {

}
