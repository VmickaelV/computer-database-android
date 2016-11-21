package com.excilys.mviegas.computer_database.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.excilys.mviegas.computer_database.android.R;
import com.excilys.mviegas.computer_database.android.applications.ComputerDatabaseApplication;
import com.excilys.mviegas.computer_database.android.dto.ComputerDto;
import com.excilys.mviegas.computer_database.android.services.ComputerService;
import com.excilys.mviegas.computer_database.persistence.Paginator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * An activity representing subscription list of Computers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents subscription list of items, which when touched,
 * lead to subscription {@link ComputerDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ComputerListActivity extends AppCompatActivity {
    private static final String TAG = "ComputerListActivity";

    public static final int REQUEST_CREATE = 1;

    @Inject
    ComputerService computerService;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on subscription tablet
     * device.
     */
    private boolean mTwoPane;
    private Subscription subscription;

    private View progressView;
    private RecyclerView recyclerView;
    private SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_list);

        ((ComputerDatabaseApplication) getApplication()).getComputerDatabaseComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ComputerListActivity.this, EditActivity.class), REQUEST_CREATE);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.computer_list);
        simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(simpleItemRecyclerViewAdapter);
        assert recyclerView != null;

        if (findViewById(R.id.computer_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        subscription = computerService.getAll(20, 0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Paginator<ComputerDto>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Paginator<ComputerDto> computerPaginator) {
                        Log.d(TAG, "onNext() called with: computerPaginator = [" + computerPaginator + "]");

                        simpleItemRecyclerViewAdapter.addComputers(computerPaginator.getValues());
                        progressView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });

        progressView = findViewById(R.id.loading_view);
        progressView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CREATE:
                if (resultCode == RESULT_OK) {
                    Snackbar.make(recyclerView, "Ordinateur bien crée", Snackbar.LENGTH_LONG).show();
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<ComputerDto> mValues;

        public SimpleItemRecyclerViewAdapter() {
            mValues = new ArrayList<>();
        }

        public SimpleItemRecyclerViewAdapter(List<ComputerDto> computerDtos) {
            mValues = computerDtos;
        }

        public void addComputers(List<ComputerDto> computerDtos) {
            int from = mValues.size();
            mValues.addAll(computerDtos);
            notifyItemRangeInserted(from, computerDtos.size());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mTitleView.setText(mValues.get(position).getName());
            // holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        ComputerDetailFragment fragment = ComputerDetailFragment.make(holder.mItem.getId());
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.computer_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ComputerDetailActivity.class);
                        intent.putExtra(ComputerDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitleView;
            public final TextView mContentView;
            public ComputerDto mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTitleView = (TextView) view.findViewById(android.R.id.text1);
                mContentView = (TextView) view.findViewById(android.R.id.text2);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
