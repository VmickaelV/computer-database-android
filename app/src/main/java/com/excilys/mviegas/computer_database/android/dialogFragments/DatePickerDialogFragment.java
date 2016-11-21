package com.excilys.mviegas.computer_database.android.dialogFragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneOffset;

/**
 * Created by excilys on 16/11/16.
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "DatePickerDialogFragmen";

    public static final class Keys {
        public static final String KEY_DATE_VALUE = "KEY_DATE_VALUE";
        public static final String KEY_DATE_MIN = "KEY_DATE_MIN";
        public static final String KEY_DATE_MAX = "KEY_DATE_MAX";
    }

    public OnDateSetListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof OnDateSetListener)) {
            throw new IllegalArgumentException("To use DatePickerDialogFragment, the activity must implements OnDateSetListener");
        }
        listener = (OnDateSetListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LocalDate valueDate = (LocalDate) getArguments().getSerializable(Keys.KEY_DATE_VALUE);
        LocalDate minDate = (LocalDate) getArguments().getSerializable(Keys.KEY_DATE_MIN);
        LocalDate maxDate = (LocalDate) getArguments().getSerializable(Keys.KEY_DATE_MAX);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, valueDate.getYear(), valueDate.getMonthValue() - 1, valueDate.getDayOfMonth());

        if (minDate != null) {
            datePickerDialog.getDatePicker().setMinDate(minDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
        }
        if (maxDate != null) {
            datePickerDialog.getDatePicker().setMaxDate(maxDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
        }

        return datePickerDialog;
    }

    /**
     * @param value Value choosen
     * @param min   min date non included
     * @param max   max date non included
     * @return
     */
    public static DatePickerDialogFragment make(LocalDate value, LocalDate min, LocalDate max) {
        Bundle bundle = new Bundle(3);
        bundle.putSerializable(Keys.KEY_DATE_VALUE, value);
        bundle.putSerializable(Keys.KEY_DATE_MIN, min);
        bundle.putSerializable(Keys.KEY_DATE_MAX, max);
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setArguments(bundle);
        return datePickerDialogFragment;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        listener.onDateSet(this, year, month + 1, dayOfMonth);
    }

    public interface OnDateSetListener {
        void onDateSet(DatePickerDialogFragment fragment, int year, int month, int dayOfMonth);
    }
}
