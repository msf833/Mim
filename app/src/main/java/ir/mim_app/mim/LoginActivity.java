package ir.mim_app.mim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via phoneNum/password.
 */
public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    //counter to exit the app
    int eCounter = 0;

    // UI references.
    private EditText mPhoneNum;
    private EditText mPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        // Set up the login form.
        mPhoneNum = (EditText) findViewById(R.id.phoneNum);

        mPassword = (EditText) findViewById(R.id.password);

        Button mPhoneNumSignInButton = (Button) findViewById(R.id.phoneNum_sign_in_button);

        mPhoneNumSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordValid(mPassword.getText().toString()) == true && isPhoneNumValid(mPhoneNum.getText().toString().trim()) == true){
                    progressBar.setVisibility(View.VISIBLE);

                    Runnable r = new Runnable() {
                        @Override
                        public void run(){
                            //to check if this is the first time user is opening the app
                            //if so, user need to signup first!
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Username", mPhoneNum.getText().toString().trim());
                            editor.putString("Password", mPassword.getText().toString());
                            editor.putBoolean("Registered", true);
                            editor.apply();
                            setResult(1);
                            finish();
                        }
                    };

                    Handler h = new Handler();
                    h.postDelayed(r, 5000); // <-- the "1000" is the delay time in miliseconds.
                }
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid phoneNum, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        if (password.length() > 4){
            return true;
        }else {
            Toast.makeText(getApplicationContext(), "پسورد قویتری وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isPhoneNumValid(String phoneNum) {
        //TODO: Replace this with your own logic
        if (phoneNum.length() == 13 & phoneNum.startsWith("+989")){
            return true;
        }else {
            if (!(phoneNum.startsWith("+989"))){
                Toast.makeText(getApplicationContext(), "شماره تلفن باید با فرمت +989xxxxxxx وارد شود", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "شماره تلفن وارد شده نامعتبر است", Toast.LENGTH_SHORT).show();
            }
            return false;
        }

    }

    @Override
    public void onBackPressed() {
        //do nothing :)
        if (eCounter == 1){
            this.finishAffinity();

        }else {
            eCounter ++;
            Toast.makeText(getApplicationContext(), "برای خارج شدن از برنامه مجددا کلیک کنید", Toast.LENGTH_SHORT).show();
        }

    }
}

