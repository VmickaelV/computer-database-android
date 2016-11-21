package com.excilys.mviegas.computer_database.android.services.impl;

import com.excilys.mviegas.computer_database.android.api.ComputerApi;
import com.excilys.mviegas.computer_database.android.dto.ComputerDto;
import com.excilys.mviegas.computer_database.android.services.ComputerService;
import com.excilys.mviegas.computer_database.persistence.Paginator;

import javax.inject.Inject;

import retrofit2.http.Body;
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
    public Observable<Paginator<ComputerDto>> getAll(@Query("size") int size, @Query("start") int start) {
        return computerDatabaseApi.getAll(size, start);
    }

    @Override
    @GET("computers")
    public Observable<Paginator<ComputerDto>> getAll(@Query("search") String search, @Query("size") int size, @Query("start") int start) {
        return computerDatabaseApi.getAll(search, size, start);
    }

    @Override
    @GET("computers/{id}")
    public Observable<ComputerDto> get(@Path("id") long id) {
        return computerDatabaseApi.get(id);
    }

    @Override
    public Observable<Void> create(@Body ComputerDto computerDto) {
        return computerDatabaseApi.create(computerDto);
    }
}
