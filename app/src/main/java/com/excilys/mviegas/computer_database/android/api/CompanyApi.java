package com.excilys.mviegas.computer_database.android.api;

import com.excilys.mviegas.computer_database.android.data.Company;
import com.excilys.mviegas.computer_database.android.dto.ComputerDto;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Mickael
 */

public interface CompanyApi {
	@GET("companies/{id}")
	Observable<Company> get(@Path("id") long id);
}
