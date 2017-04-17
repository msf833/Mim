package ir.mim_app.mim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by agh29 on 17/04/2017.
 */

public class splashActivity extends Activity {

    int Icounter = 0;
    Thread background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 10 seconds
                    sleep(5*1000);
                    //Remove activity
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);

                } catch (Exception e) {

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
