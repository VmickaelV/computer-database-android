package com.excilys.mviegas.computer_database.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.excilys.mviegas.computer_database.android.R;
import com.excilys.mviegas.computer_database.android.applications.ComputerDatabaseApplication;
import com.excilys.mviegas.computer_database.android.dialogFragments.DatePickerDialogFragment;
import com.excilys.mviegas.computer_database.android.dto.ComputerDto;
import com.excilys.mviegas.computer_database.android.services.ComputerService;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.format.DateTimeFormatter;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class EditActivity extends AppCompatActivity implements DatePickerDialogFragment.OnDateSetListener {

    private static final String TAG = "EditActivity";

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

    private EditText mComputerName;
    private EditText mComputerIntroducedDateInput;
    private EditText mComputerDiscontinuedDateInput;
    private Spinner mComputerCompany;

    public static final class Tags {
        public static final String INTRODUCED_DATE_CALENDAR_DIALOG = "INTRODUCED_DATE_CALENDAR_DIALOG";
        public static final String DISCONTINUED_DATE_CALENDAR_DIALOG = "DISCONTINUED_DATE_CALENDAR_DIALOG";
    }

    private LocalDate mIntroducedDate;

    private LocalDate mDiscontinuedDate;

    private ComputerService computerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        computerService = ((ComputerDatabaseApplication) getApplication()).getComputerDatabaseComponent().getComputerService();

        mComputerName = (EditText) findViewById(R.id.computer_name_input);
        mComputerIntroducedDateInput = (EditText) findViewById(R.id.computer_introduced_date_input);
        mComputerDiscontinuedDateInput = (EditText) findViewById(R.id.computer_discontinued_date_input);
        mComputerCompany = (Spinner) findViewById(R.id.computer_company_input);

        ImageButton button = (ImageButton) findViewById(R.id.computer_introduced_date_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate value;
                if (mIntroducedDate == null) {
                    value = LocalDate.now();
                } else {
                    value = mIntroducedDate;
                }
                LocalDate min = LocalDate.of(1970, Month.JANUARY, 1);
                LocalDate max;
                if (mDiscontinuedDate != null) {
                    max = mDiscontinuedDate.plusDays(1);
                } else {
                    max = LocalDate.now();
                }
                DatePickerDialogFragment.make(value, min, max).show(getSupportFragmentManager(), Tags.INTRODUCED_DATE_CALENDAR_DIALOG);
            }
        });

        ImageButton computerIntroducedDateButton = (ImageButton) findViewById(R.id.computer_discontinued_date_button);
        computerIntroducedDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate value;
                if (mDiscontinuedDate == null) {
                    value = LocalDate.now();
                } else {
                    value = mDiscontinuedDate;
                }
                LocalDate min;
                LocalDate max = LocalDate.now();
                if (mIntroducedDate != null) {
                    min = mIntroducedDate.plusDays(1);
                } else {
                    min = LocalDate.of(1970, Month.JANUARY, 1);
                }
                DatePickerDialogFragment.make(value, min, max).show(getSupportFragmentManager(), Tags.DISCONTINUED_DATE_CALENDAR_DIALOG);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_action:
                computerService.create(new ComputerDto(0, mComputerName.getText().toString(), mIntroducedDate, mDiscontinuedDate, 0))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void computerDto) {
                                setResult(RESULT_OK);
                                finish();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "call: ", throwable);
                            }
                        });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialogFragment fragment, int year, int month, int dayOfMonth) {
        switch (fragment.getTag()) {
            case Tags.INTRODUCED_DATE_CALENDAR_DIALOG:
                mIntroducedDate = LocalDate.of(year, month, dayOfMonth);
                mComputerIntroducedDateInput.setText(dateTimeFormatter.format(mIntroducedDate));
                break;
            case Tags.DISCONTINUED_DATE_CALENDAR_DIALOG:
                mDiscontinuedDate = LocalDate.of(year, month, dayOfMonth);
                mComputerDiscontinuedDateInput.setText(dateTimeFormatter.format(mDiscontinuedDate));
                break;
        }
    }
}
