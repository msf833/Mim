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
 * A login screen that offers login via email/password.
 */
public class loginActivity extends AppCompatActivity {

    // UI references.
    private EditText usernameLoginTxt;
    private EditText PasswordLoginTxt;;
    private View mProgressView;

    //data holders for query answ
    String std_ID;
    String name;
    String family;
    String schoolname;
    String sfield;
    String sex;

    SharedPreferences sharedPreferences;

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

        // Set up the login form.
        usernameLoginTxt = (EditText) findViewById(R.id.Tbox_username_login);
        PasswordLoginTxt = (EditText) findViewById(R.id.Tbox_password_login);


        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordValid(PasswordLoginTxt.getText().toString()) == true && isPhoneNumValid(usernameLoginTxt.getText().toString().trim()) == true){
                    mProgressView.setVisibility(View.VISIBLE);

                    boolean userExist = true;

                    final String user = usernameLoginTxt.getText().toString().trim();
                    final String pass = PasswordLoginTxt.getText().toString();

                    url = "http://api.mim-app.ir/SelectValue_LoginActivity.php";
                    getJson = new GetJson(url);
                    getJson.execute("loginRequest",user,pass);


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

                    try {
                        jsonobject = new JSONObject(JsonString);

                        jsonArray = jsonobject.getJSONArray("search_resualt");

                        int count = 0;
                        if (0 < jsonArray.length()) {
                            JSONObject jo = jsonArray.getJSONObject(count);
                            String checker = jo.getString("flag");
                            std_ID= jo.getString("studentID");
                            name = jo.getString("name");
                            family= jo.getString("family");
                            schoolname= jo.getString("schoolname");
                            sfield= jo.getString("Sfield");
                            sex= jo.getString("Sex");

                            if (checker.equals("false") ){
                                userExist = false;
                            }
                            Toast.makeText(getApplicationContext(), "answ: " + checker, Toast.LENGTH_LONG).show();

                            count++;
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    if (userExist){
                        Toast.makeText(getApplicationContext(), "std: " + std_ID, Toast.LENGTH_SHORT).show();

                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                //to check if this is the first time user is opening the app
                                //if so, user need to signup first!

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("Registered", true);
                                editor.putString("Username", user);
                                editor.putString("Password", pass);
                                editor.putString("name", name);
                                editor.putString("family", family);
                                editor.putString("schoolName", schoolname);
                                editor.putString("field", sfield);
                                editor.putString("sex", sex);
                                editor.putString("stdID", std_ID);
                                editor.apply();

                                finish();
                            }
                        };

                        Handler h = new Handler();
                        h.postDelayed(r, 5000); // <-- the "1000" is the delay time in miliseconds.

                    }else {
                        Toast.makeText(getApplicationContext(), "این شماره تلفن قبلا ثبت نشده است!", Toast.LENGTH_SHORT).show();
                        mProgressView.setVisibility(View.GONE);
                    }
                }
            }
        });

        mProgressView = findViewById(R.id.logini_progress);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        if (password.length() > 0){
            return true;
        }else {
            Toast.makeText(getApplicationContext(), "پسورد خود را وارد کنید", Toast.LENGTH_SHORT).show();
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
}

