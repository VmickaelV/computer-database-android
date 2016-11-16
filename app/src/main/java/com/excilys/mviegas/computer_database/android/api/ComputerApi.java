package com.excilys.mviegas.computer_database.android.api;

import com.excilys.mviegas.computer_database.data.Computer;
import com.excilys.mviegas.computer_database.persistence.Paginator;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * API REST de {@link Computer}.
 *
 * @author VIEGAS Mickael
 */
public interface ComputerApi {

    @GET("computers")
    Observable<Paginator<Computer>> getAll(@Query("size") int size, @Query("start") int start);

//    @GET("computers")
//    public Call<List<Computer>> getAll(@Query("size") int size, @Query("start") int start, @Query("order") );

    @GET("computers")
    Observable<Paginator<Computer>> getAll(@Query("search") String search, @Query("size") int size, @Query("start") int start);

//    @GET("computers")
//    public Call<List<Computer>> getAll(@Query("size") int size, @Query("start") int start, @Query("order") );


    @GET("computers/{id}")
    Observable<Paginator<Computer>> get(@Path("id") int id);
}
