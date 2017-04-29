package ir.mim_app.mim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import ir.mim_app.mim.fragment.Prof_listview_Fragment;
import ir.mim_app.mim.fragment.courses_list_fragment;

/**
 * Created by agh29 on 17/04/2017.
 */

public class splashActivity extends Activity {

    int Icounter = 0;
    Thread background;


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        background = new Thread() {
            public void run() {
                if (isNetworkAvailable()){
                    try {
                        // Thread will sleep for 10 seconds
                        sleep(2*1000);
                        //Remove activity
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {

                    }
                }else {
                    Toast.makeText(getApplicationContext(), " مشکل در اتصال به اینترنت ", Toast.LENGTH_SHORT).show();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

            }
        };

        // start thread
        background.start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    public void onBackPressed() {

        if (Icounter == 1){
            android.os.Process.killProcess(android.os.Process.myPid());
        }else {
            Icounter++;
            Toast.makeText(getApplicationContext(), "بودی حالا :)", Toast.LENGTH_LONG).show();
        }
    }
}
