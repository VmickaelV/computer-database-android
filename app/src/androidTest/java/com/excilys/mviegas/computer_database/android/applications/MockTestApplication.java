package com.excilys.mviegas.computer_database.android.applications;

import com.excilys.mviegas.computer_database.android.dagger.components.BaseComputerDatabaseComponent;
import com.excilys.mviegas.computer_database.android.dagger.components.DaggerMockComputerDatabaseComponent;
import com.excilys.mviegas.computer_database.android.dagger.modules.ContextModule;

public class MockTestApplication extends ComputerDatabaseApplication {

    @Override
    protected BaseComputerDatabaseComponent createComponent() {
        return DaggerMockComputerDatabaseComponent.builder().contextModule(new ContextModule(this)).build();
    }
}
