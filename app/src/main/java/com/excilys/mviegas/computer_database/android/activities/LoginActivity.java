package com.excilys.mviegas.computer_database.android.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.excilys.mviegas.computer_database.android.R;
import com.excilys.mviegas.computer_database.android.applications.ComputerDatabaseApplication;
import com.excilys.mviegas.computer_database.android.helpers.PreferencesHelper;
import com.excilys.mviegas.computer_database.android.services.AuthenticationService;
import com.excilys.mviegas.computer_database.android.services.InternetService;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Activity pour s'identifier sur le service Computer Database.
 *
 * @author VIEGAS Mickael
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Snackbar mSnackBar;

    public static final class Tags {
        public static final String KEY_SHARED_PREFERENCES = "sharedPreferencies";
        public static final String KEY_LOGINS_USED = "loginsUsed";
    }

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Inject
    InternetService internetService;

    @Inject
    AuthenticationService authenticationService;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private Subscription subscription = null;

    // UI references.
    private AutoCompleteTextView mLoginEdit;
    private EditText mPasswordEdit;
    private View mProgressView;
    private View mLoginFormView;
    private View mErrorBlocksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((ComputerDatabaseApplication) getApplication()).getComputerDatabaseComponent().inject(this);

        //        mPreferencesHelper = getSharedPreferences(Tags.KEY_SHARED_PREFERENCES, MODE_PRIVATE);

        // Set up the login form.
        mLoginEdit = (AutoCompleteTextView) findViewById(R.id.login);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(LoginActivity.this,
//                android.R.layout.simple_dropdown_item_1line, new ArrayList<>(mPreferencesHelper.getStringSet(Tags.KEY_SHARED_PREFERENCES, new HashSet<String>())));
//        mLoginEdit.setAdapter(adapter);

        mPasswordEdit = (EditText) findViewById(R.id.password);
        mPasswordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        final Button mLoginButton = (Button) findViewById(R.id.sign_in_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mErrorBlocksView = findViewById(R.id.errors_view);

        internetService.subscribeNetworkState()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    if (mSnackBar != null) {
                        mSnackBar.dismiss();
                    }
                } else {
                    mSnackBar = Snackbar.make(LoginActivity.this.mLoginFormView, "Vous n'êtes pas connecté au web.", Snackbar.LENGTH_INDEFINITE);
                    mSnackBar.show();
                }
                mLoginButton.setEnabled(aBoolean);
            }
        });
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mLoginEdit, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (!internetService.isConnected()) {
            Snackbar.make(mProgressView, R.string.no_internet_label, Snackbar.LENGTH_LONG).show();
            return;
        }
        if (subscription != null) {
            return;
        }

        // Reset errors.
        mLoginEdit.setError(null);
        mPasswordEdit.setError(null);

        // Store values at the time of the login attempt.
        String email = mLoginEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordEdit.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordEdit;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mLoginEdit.setError(getString(R.string.error_field_required));
            focusView = mLoginEdit;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mLoginEdit.setError(getString(R.string.error_invalid_email));
            focusView = mLoginEdit;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true, null);
            subscription = authenticationService.signin(email, password)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted() called");
                            subscription = null;
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError() called with: e = [" + e + "]", e);
                            if (e instanceof HttpException) {
                                HttpException httpException = (HttpException) e;
                                if (httpException.code() == 401) {
                                    showProgress(false, getString(R.string.wrong_sign_in));
                                } else {
                                    showProgress(false, "Exception : " + e.getMessage());
                                }
                            } else {
                                showProgress(false, "Exception : " + e.getMessage());
                            }
                            subscription = null;
                        }

                        @Override
                        public void onNext(Boolean o) {
                            showProgress(false, null);
                            subscription = null;
                            startActivity(new Intent(LoginActivity.this, ComputerListActivity.class));
                        }


                    });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, final String messageError) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alphaBy(show ? 0 : 1).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            if (!show && !TextUtils.isEmpty(messageError)) {
                mErrorBlocksView.setVisibility(View.VISIBLE);
                ((TextView) mErrorBlocksView).setText(messageError);
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down_fade_in);
                animation.setDuration(shortAnimTime);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mErrorBlocksView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mErrorBlocksView.startAnimation(animation);
            } else {
                mErrorBlocksView.setVisibility(show ? View.GONE : View.VISIBLE);
                mErrorBlocksView.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mErrorBlocksView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });
            }
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);

            ((TextView) mErrorBlocksView).setText(messageError);
            mErrorBlocksView.setVisibility(show && !TextUtils.isEmpty(messageError) ? View.VISIBLE : View.GONE);
        }
    }

//            mAuthTask = null;
//            showProgress(false, !success);
//
//            if (success) {
//                finish();
//            } else {
//                //                mPasswordEdit.setError(getString(R.string.error_incorrect_password));
//                mPasswordEdit.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false, true);
//        }
//    }
}

