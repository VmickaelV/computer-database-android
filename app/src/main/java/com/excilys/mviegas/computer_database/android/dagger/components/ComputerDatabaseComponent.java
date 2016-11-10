package com.excilys.mviegas.computer_database.android.dagger.components;

import com.excilys.mviegas.computer_database.android.activities.ComputerDetailActivity;
import com.excilys.mviegas.computer_database.android.activities.ComputerListActivity;
import com.excilys.mviegas.computer_database.android.activities.LoginActivity;
import com.excilys.mviegas.computer_database.android.dagger.modules.ServiceModule;
import com.excilys.mviegas.computer_database.android.services.CompanyService;
import com.excilys.mviegas.computer_database.android.services.ComputerService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Racine de l'arbre de composetns Dagger.
 *
 * @author VIEGAS Mickael
 */
@Singleton
@Component(modules = {ServiceModule.class})
public interface ComputerDatabaseComponent {
    void inject(LoginActivity loginActivity);

    void inject(ComputerListActivity computerListActivity);

    void inject(ComputerDetailActivity computerDetailActivity);

    OkHttpClient getOkHttpClient();

    ComputerService getComputerService();

    CompanyService getCompanyService();
}
