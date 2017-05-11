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
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via phoneNum/password.
 */
public class SignupActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    //counter to exit the app
    int eCounter = 0;

    // UI references.
    private EditText mPhoneNum;
    private EditText mPassword;
    private EditText mName;
    private EditText mFamily;
    ProgressBar progressBar;

    String queryString;
    GetJson getJson;
    String url;
    String JsonString="";
    JSONObject jsonobject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        // Set up the login form.
        mPhoneNum = (EditText) findViewById(R.id.phoneNum);

        mPassword = (EditText) findViewById(R.id.password);

        mName = (EditText) findViewById(R.id.nameTxt);

        mFamily = (EditText) findViewById(R.id.fnameTxt);


        Button mPhoneNumSignInButton = (Button) findViewById(R.id.phoneNum_sign_in_button);

        mPhoneNumSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordValid(mPassword.getText().toString()) == true && isPhoneNumValid(mPhoneNum.getText().toString().trim()) == true){
                    progressBar.setVisibility(View.VISIBLE);

                    boolean userExist = true;

                    final String user = mPhoneNum.getText().toString().trim();
                    final String pass = mPassword.getText().toString();
                    final String usern = mName.getText().toString();
                    final String u_f = mFamily.getText().toString();

                    url = "http://api.mim-app.ir/InsertValue_SignupActivity.php";
                    getJson = new GetJson(url);
                    getJson.execute("signupRequest",user,pass,usern,u_f);


                    Log.i("MSF"," " + mPhoneNum.getText().toString().trim() + " " +
                            mPassword.getText().toString() + " " + mName.getText().toString() + " "+
                            mFamily.getText().toString());
                    try {
                        getJson.get();

                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    } catch(ExecutionException e) {
                        e.printStackTrace();
                    }

                    JsonString = getJson.finalJson;
                   /// Toast.makeText(getApplicationContext(), "q: " + getJson.finalJson, Toast.LENGTH_SHORT).show();
                    Log.i("MSF","string json is : "+ JsonString);
                    String std_ID = "";

                    try {
                        jsonobject = new JSONObject(JsonString);

                        jsonArray = jsonobject.getJSONArray("search_resualt");

                        int count = 0;
                        if (0 < jsonArray.length()) {
                            JSONObject jo = jsonArray.getJSONObject(count);
                            String checker = jo.getString("flag");
                            std_ID = jo.getString("studentID");
                            if (checker.equals("done") ){
                                userExist = false;
                            }
                            Toast.makeText(getApplicationContext(), "answ: " + checker, Toast.LENGTH_LONG).show();

                            count++;
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    if (userExist){
                        Toast.makeText(getApplicationContext(), "این شماره تلفن قبلا ثبت شده است!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(getApplicationContext(), "std: " + std_ID, Toast.LENGTH_SHORT).show();
                        final String finalStd_ID = std_ID;
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                //to check if this is the first time user is opening the app
                                //if so, user need to signup first!
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Username", user);
                                editor.putString("Password", pass);
                                editor.putBoolean("Registered", true);
                                editor.putString("name", usern);
                                editor.putString("family", u_f);
                                editor.putString("schoolName", "");
                                editor.putInt("field", 1);
                                editor.putInt("sex", 1);
                                editor.putString("stdID", finalStd_ID);
                                editor.apply();

                                setResult(1);
                                finish();
                            }
                        };

                        Handler h = new Handler();
                        h.postDelayed(r, 5000); // <-- the "1000" is the delay time in miliseconds.

                    }
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
        if (phoneNum.length() == 10 & phoneNum.startsWith("9")){
            return true;
        }else {
            if (!(phoneNum.startsWith("9"))){
                Toast.makeText(getApplicationContext(), "شماره تلفن باید با فرمت 9xxxxxxx وارد شود", Toast.LENGTH_SHORT).show();
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

