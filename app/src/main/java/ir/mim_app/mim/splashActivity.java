package ir.mim_app.mim;

import android.app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import ir.mim_app.mim.assistClasses.studentAttributes;
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
       if (isNetworkAvailable()){
                    background = new Thread() {
                        public void run() {
                            if (isNetworkAvailable()){
                                try {
                                    // Thread will sleep for 10 seconds
                                    AsyncTaskupdate updater = new AsyncTaskupdate();

                                    updater.execute();

                                   // Toast.makeText(splashActivity.this, "slm", Toast.LENGTH_SHORT).show();


                                } catch (Exception e) {

                                }
                            }else {
                                Toast.makeText(getApplicationContext(), " مشکل در اتصال به اینترنت ", Toast.LENGTH_SHORT).show();
                               // android.os.Process.killProcess(android.os.Process.myPid());
                                finish();
                            }

                        }
                    };

                    // start thread
                    background.start();
       }else {
           Toast.makeText(getApplicationContext(), " مشکل در اتصال به اینترنت ", Toast.LENGTH_SHORT).show();
       }


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
    public   int appversion;

    class AsyncTaskupdate  extends AsyncTask<Void, Void, Void> {
        String DownloadupdateLink="http://mim-app.ir/app/mim-app.apk";
        
        public AsyncTaskupdate() {
        }



        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

            if (appversion > studentAttributes.appversion){
                 Toast.makeText(splashActivity.this, "need to update ", Toast.LENGTH_SHORT).show();

                TextView Tv_updateText = (TextView) findViewById(R.id.Tv_updateText);
                Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(DownloadupdateLink));
                startActivity(intent);
                Tv_updateText.setVisibility(View.VISIBLE);

            }else {
                //sleep(2*1000);
                //Remove activity
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            

            String updateLink="http://api.mim-app.ir/app_update.php";

            try {
                URL url = new URL(updateLink);



            try {
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                String data = URLEncoder.encode("queryString", "UTF-8")+"="+ URLEncoder.encode("slm","UTF-8");

                wr.write( data );
                wr.flush();
                wr.close();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response

                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }

                appversion = Integer.parseInt(sb.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            } catch (IOException e) {
                e.printStackTrace();
            }




            return null;
        }
    }
}
