package ir.mim_app.mim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static ir.mim_app.mim.R.id.progressBar;
import static java.security.AccessController.getContext;

/**
 * A login screen that offers login via email/password.
 */
public class infoEditActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    //counter to exit the app
    int eCounter = 0;

    EditText mPhoneNum;
    EditText mPassword;
    EditText name;
    EditText fname;
    EditText schoolName;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_edit);

        //spinner_sex
        final Spinner spinner_sex = (Spinner) findViewById(R.id.spinner_sex);

        final String[] sex = new String[] {"زن", "مرد"};
        String[] paye_items  = new String[] {"ریاضی-فیریک", "تجربی", "علوم انسانی", "فنی و حرفه ای"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, sex);

        spinner_sex.setAdapter(adapter);

        final Spinner paye = (Spinner) findViewById(R.id.spinner_field);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, paye_items);

        paye.setAdapter(adapter1);

        progressBar = (ProgressBar) findViewById(R.id.edit_progress);

        // Set up the login form.
        mPhoneNum = (EditText) findViewById(R.id.info_phoneNum);
        mPassword = (EditText) findViewById(R.id.info_password);
        name = (EditText) findViewById(R.id.name);
        fname = (EditText) findViewById(R.id.fname);
        schoolName = (EditText) findViewById(R.id.schoolName);

        //retriving data to feed the page
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mPhoneNum.setText(sharedPreferences.getString("Username",""));
        mPassword.setText(sharedPreferences.getString("Password",""));
        name.setText(sharedPreferences.getString("name",""));
        fname.setText(sharedPreferences.getString("family",""));
        schoolName.setText(sharedPreferences.getString("schoolName",""));
        int y = sharedPreferences.getInt("field", 0);
        paye.setSelection(y-1);
        int x = sharedPreferences.getInt("sex", 0);
        spinner_sex.setSelection(x-1);


        Button mPhoneNumSignInButton = (Button) findViewById(R.id.info_phoneNum_sign_in_button);
        Button cancelBtn = (Button) findViewById(R.id.info_phoneNum_cancelEdit_button);

        mPhoneNumSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordValid(mPassword.getText().toString()) == true && isPhoneNumValid(mPhoneNum.getText().toString().trim()) == true ){
                    progressBar.setVisibility(View.VISIBLE);

                    Runnable r = new Runnable() {
                        @Override
                        public void run(){
                            //to check if this is the first time user is opening the app
                            //if so, user need to signup first!
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Password", mPassword.getText().toString());
                            editor.putString("name", name.getText().toString());
                            editor.putString("family", fname.getText().toString());
                            editor.putString("schoolName", schoolName.getText().toString());
                            editor.putInt("field", paye.getSelectedItemPosition()+1);
                            editor.putInt("sex", spinner_sex.getSelectedItemPosition()+1);
                            editor.putBoolean("Registered", true);
                            editor.apply();
                            finish();
                        }
                    };

                    Handler h = new Handler();
                    h.postDelayed(r, 3000); // <-- the "1000" is the delay time in miliseconds.
                }
            }
        });

        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

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

