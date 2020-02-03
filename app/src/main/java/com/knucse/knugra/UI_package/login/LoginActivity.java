package com.knucse.knugra.UI_package.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.knucse.knugra.DM_package.Database;
import com.knucse.knugra.DM_package.RequestType;
import com.knucse.knugra.DM_package.ServerConnectTask;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_List;
import com.knucse.knugra.PD_package.User_package.Student_package.Student;
import com.knucse.knugra.PD_package.User_package.User;
import com.knucse.knugra.UI_package.MainActivity;
import com.knucse.knugra.R;

public class LoginActivity extends AppCompatActivity {
    public static LoginActivity loginActivity;
    private LoginViewModel loginViewModel;
    private int majorposition;
    private String major;
    private SharedPreferences settings;
    private  SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        loginActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        loginButton.setEnabled(true);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        final Spinner trackSpinner = (Spinner)this.findViewById(R.id.track_spinner);

        final ArrayAdapter trackAdapter = ArrayAdapter.createFromResource(this, R.array.track, android.R.layout.simple_spinner_dropdown_item);

        // autologin feature
        settings = getSharedPreferences("settings", 0);
        editor = settings.edit();
        editor.putBoolean("autoLogin", true);

        if (settings.getBoolean("autoLogin", false)) {
            usernameEditText.setText(settings.getString("ID", ""));
            passwordEditText.setText(settings.getString("PW", ""));
        }

        // setting for poi dependencies
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        // ui independent work
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                Database.load();
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        trackSpinner.setAdapter(trackAdapter);
        trackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorposition = position;

                major = (String)parent.getItemAtPosition(majorposition);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                major = (String)parent.getItemAtPosition(parent.getFirstVisiblePosition());
            }
        });

//        // always sync with network
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(false)
//                .build();
//        db.setFirestoreSettings(settings);
        //init graduation info

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);

                loginButton.setEnabled(true);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                    loginViewModel.getLoginResult().getValue().resetError();
                }
                if (loginResult.getSuccess() != null) {

                    // save auto login information
                    // if (autoLogin button is on ) {
                        editor.putString("ID", User.getInstance().getId());
                        editor.putString("PW", User.getInstance().getPassword());
                        editor.commit();
                    //}
                    // update user data in th beginning of the main activity.
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            ServerConnectTask.ServerConnectTaskPrams params =
                                    new ServerConnectTask.ServerConnectTaskPrams(
                                            User.getInstance().getId(),
                                            User.getInstance().getPassword(),
                                            RequestType.UPDATE,
                                            major);

                            ServerConnectTask serverConnectTask = new ServerConnectTask();

                            serverConnectTask.execute(params);
                            return null;
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                    updateUiWithUser(loginResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    //Complete and destroy login activity once successful
                    finish();
                }

            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                loadingProgressBar.setVisibility(View.VISIBLE);
                loadingProgressBar.setMax(100);

                //update ui
                AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... strings) {
                        login(strings[0], strings[1], major);
                        return null;
                    }
                };
                asyncTask.execute(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        switchToMain();

        // 사용자가 학생밖에 없다는 전제하에
        Student student = (Student)User.getInstance().getUserData();
        student.getStudentCareerList().setCareer_track(major);
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void login(String username, String password, String major) {
        loginViewModel.login(username, password, major);
    }


    public void switchToMain() {
        Intent it = new Intent(LoginActivity.this, MainActivity.class);
        it.putExtra("mposition", majorposition);
        startActivity(it);
    }
}

