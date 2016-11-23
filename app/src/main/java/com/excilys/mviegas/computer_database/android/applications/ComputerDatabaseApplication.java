package com.excilys.mviegas.computer_database.android.applications;

import android.app.Application;

import com.excilys.mviegas.computer_database.android.dagger.components.BaseComputerDatabaseComponent;
import com.excilys.mviegas.computer_database.android.dagger.components.DaggerComputerDatabaseComponent;
import com.excilys.mviegas.computer_database.android.dagger.modules.ContextModule;
import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;


/**
 * Class {@link Application}.
 *
 * @author VIEGAS Mickael
 */
public class ComputerDatabaseApplication extends Application {

    /**
     * Arbre de dépendances Dagger.
     */
    private BaseComputerDatabaseComponent baseComputerDatabaseComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        AndroidThreeTen.init(this);

        baseComputerDatabaseComponent = createComponent();
    }

    public BaseComputerDatabaseComponent getComputerDatabaseComponent() {
        return baseComputerDatabaseComponent;
    }

    public void setComputerDatabaseComponent(BaseComputerDatabaseComponent baseComputerDatabaseComponent) {
        this.baseComputerDatabaseComponent = baseComputerDatabaseComponent;
    }

    protected BaseComputerDatabaseComponent createComponent() {
        return DaggerComputerDatabaseComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
    }
}

