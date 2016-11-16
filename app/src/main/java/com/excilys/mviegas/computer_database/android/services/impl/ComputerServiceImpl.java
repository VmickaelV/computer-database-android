package com.excilys.mviegas.computer_database.android.services.impl;

import com.excilys.mviegas.computer_database.android.api.ComputerApi;
import com.excilys.mviegas.computer_database.android.services.ComputerService;
import com.excilys.mviegas.computer_database.data.Computer;
import com.excilys.mviegas.computer_database.persistence.Paginator;

import javax.inject.Inject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Implémentation de {@link ComputerService}.
 *
 * @author VIEGAS Mickael
 */

public class ComputerServiceImpl implements ComputerService {
    private ComputerApi computerDatabaseApi;

    @Inject
    public ComputerServiceImpl(ComputerApi computerDatabaseApi) {
        this.computerDatabaseApi = computerDatabaseApi;
    }

    @Override
    @GET("computers")
    public Observable<Paginator<Computer>> getAll(@Query("size") int size, @Query("start") int start) {
        return computerDatabaseApi.getAll(size, start);
    }

    @Override
    @GET("computers")
    public Observable<Paginator<Computer>> getAll(@Query("search") String search, @Query("size") int size, @Query("start") int start) {
        return computerDatabaseApi.getAll(search, size, start);
    }

    @Override
    @GET("computers/{id}")
    public Observable<Paginator<Computer>> get(@Path("id") int id) {
        return computerDatabaseApi.get(id);
    }
}
