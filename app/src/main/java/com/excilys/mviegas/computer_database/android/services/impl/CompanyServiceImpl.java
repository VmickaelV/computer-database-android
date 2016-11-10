package com.excilys.mviegas.computer_database.android.services.impl;

import com.excilys.mviegas.computer_database.android.api.CompanyApi;
import com.excilys.mviegas.computer_database.android.api.ComputerApi;
import com.excilys.mviegas.computer_database.android.data.Company;
import com.excilys.mviegas.computer_database.android.services.CompanyService;
import retrofit2.http.Path;
import rx.Observable;

import javax.inject.Inject;

/**
 * @author Mickael
 */

public class CompanyServiceImpl implements CompanyService {
	private CompanyApi mCompanyApi;

	@Inject
	public CompanyServiceImpl(CompanyApi companyApi) {
		this.mCompanyApi = companyApi;
	}

	@Override
	public Observable<Company> get(@Path("id") long id) {
		return mCompanyApi.get(id);
	}
}
