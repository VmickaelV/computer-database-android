package com.excilys.mviegas.computerdatabaseandroid.services.impl;

import com.excilys.mviegas.computerdatabaseandroid.api.ComputerApi;
import com.excilys.mviegas.computerdatabaseandroid.services.ComputerService;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persistence.Paginator;

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
