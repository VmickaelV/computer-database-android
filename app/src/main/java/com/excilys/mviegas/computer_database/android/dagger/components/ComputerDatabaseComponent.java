package com.excilys.mviegas.computer_database.android.dagger.components;

import com.excilys.mviegas.computer_database.android.dagger.modules.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ServiceModule.class})
public interface ComputerDatabaseComponent extends BaseComputerDatabaseComponent {
}
