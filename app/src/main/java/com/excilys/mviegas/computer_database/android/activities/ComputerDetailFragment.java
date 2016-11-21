package com.excilys.mviegas.computer_database.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.excilys.mviegas.computer_database.android.R;
import com.excilys.mviegas.computer_database.android.applications.ComputerDatabaseApplication;
import com.excilys.mviegas.computer_database.android.data.Company;
import com.excilys.mviegas.computer_database.android.dto.ComputerDto;
import com.excilys.mviegas.computer_database.android.services.CompanyService;
import com.excilys.mviegas.computer_database.android.services.ComputerService;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A fragment representing a single Computer detail screen.
 * This fragment is either contained in a {@link ComputerListActivity}
 * in two-pane mode (on tablets) or a {@link ComputerDetailActivity}
 * on handsets.
 */
public class ComputerDetailFragment extends Fragment {

    private static final String TAG = "ComputerDetailFragment";

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ComputerDto mItem;

    private Subscription subscriber;
    private ComputerService computerService;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ComputerDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        computerService = ((ComputerDatabaseApplication) getActivity().getApplication()).getComputerDatabaseComponent().getComputerService();
        final CompanyService companyService = ((ComputerDatabaseApplication) getActivity().getApplication()).getComputerDatabaseComponent().getCompanyService();

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Activity activity = this.getActivity();
            final CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            subscriber = computerService.get(getArguments().getLong(ARG_ITEM_ID))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ComputerDto>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "#onCompleted() called with " + "");
                            subscriber = null;
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError() called with: e = [" + e + "]");
                        }

                        @Override
                        public void onNext(final ComputerDto computerDto) {
                            companyService.get(computerDto.getId())
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<Company>() {
                                        @Override
                                        public void call(Company company) {
                                            computerDto.setCompany(company);
                                            Log.d(TAG, "#onNext() called with " + "computerDtoPaginator = [" + String.valueOf(computerDto) + "]");
                                            if (appBarLayout != null) {
                                                appBarLayout.setTitle(computerDto.getName());
                                            }

                                            ((TextView) getView().findViewById(R.id.computer_name_value)).setText(computerDto.toString());
                                        }
                                    });
                        }
                    });

            if (appBarLayout != null) {
                appBarLayout.setTitle("Chargement en cours...");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.computer_detail, container, false);

        return rootView;
    }

    public static ComputerDetailFragment make(long idComputer) {
        Bundle bundle = new Bundle(1);
        bundle.putLong(ARG_ITEM_ID, idComputer);
        ComputerDetailFragment computerDetailFragment = new ComputerDetailFragment();
        computerDetailFragment.setArguments(bundle);
        return computerDetailFragment;
    }
}
