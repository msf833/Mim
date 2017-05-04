package ir.mim_app.mim;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.concurrent.ExecutionException;

import ir.mim_app.mim.fragment.Prof_listview_Fragment;
import ir.mim_app.mim.fragment.courses_list_fragment;
import ir.mim_app.mim.fragment.notification_fragment;
import ir.mim_app.mim.fragment.search_fragment;
import ir.moslem_deris.apps.zarinpal.PaymentBuilder;
import ir.moslem_deris.apps.zarinpal.ZarinPal;
import ir.moslem_deris.apps.zarinpal.enums.ZarinPalError;
import ir.moslem_deris.apps.zarinpal.listeners.OnPaymentListener;
import ir.moslem_deris.apps.zarinpal.models.Payment;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GetJson getJson;
    FrameLayout framelayout;
    BottomBar bottomBar;
    FragmentManager fragmentManager = getSupportFragmentManager();
    ProgressBar progBar;
    Prof_listview_Fragment fragobj;
    courses_list_fragment clfobj;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    void inint (){
        //bottombar_Activity_main
        bottomBar = (BottomBar) findViewById(R.id.bottombar_Activity_main);
        framelayout= (FrameLayout) findViewById(R.id.frameLayout_main_activity);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inint();


        ImageView appbar_img = (ImageView) findViewById(R.id.appbar_img);
        appbar_img.setImageResource(R.mipmap.ic_mimlogo2);
        appbar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pInfoMenuActivity = new Intent(getApplicationContext(), pInfoMenuActivity.class);
                startActivity(pInfoMenuActivity);
            }
        });

        TextView appbar_userName = (TextView) findViewById(R.id.appbar_userName);
        appbar_userName.setText("میم مثل معلم");

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        progBar = (ProgressBar) findViewById(R.id.progressBar);
      //  progBar.setVisibility(View.INVISIBLE);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//       // final ImageView tev = (ImageView) findViewById(R.id.main_imgView);
//
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // tev.setText(getJson.finalJson);
//
//
//            }
//        });

       // final ImageView imgview = (ImageView) findViewById(R.id.main_imgView);

        if (isNetworkAvailable()){
            //Toast.makeText(MainActivity.this, "network is connected", Toast.LENGTH_SHORT).show();
             fragobj = new Prof_listview_Fragment();
           // fragobj.setProgresbar(progBar);
            //progBar.setVisibility(View.VISIBLE);
             clfobj =  new courses_list_fragment();
        }else {
            Toast.makeText(MainActivity.this, "network isn't connected", Toast.LENGTH_SHORT).show();
        }

        bottomBar.setActiveTabColor(Color.parseColor("#ffffff"));

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                //home fragment has been disabled for 1st version of the application but codes are still here
                //notification fragment has been replaced!

                /*if (tabId == R.id.home_tab){
                     Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();


                    FragmentTransaction frm = fragmentManager.beginTransaction().replace(R.id.frameLayout_main_activity,new home_fragment());
                    frm.commit();

                }*/


                if (tabId == R.id.btn_profList_tab){
                   // Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    progBar.setVisibility(View.VISIBLE);
                    if (isNetworkAvailable()){
                       // Prof_listview_Fragment fragobj = new Prof_listview_Fragment();
                        if(fragobj == null) fragobj = new Prof_listview_Fragment();
                      //  framelayout.setVisibility(View.INVISIBLE);


                        FragmentTransaction frm = fragmentManager.beginTransaction().replace(R.id.frameLayout_main_activity,fragobj);
                        frm.commit();
                        //progBar.setVisibility(View.VISIBLE);
                      progBar.setVisibility(View.GONE);
                        //progBar.setVisibility(View.INVISIBLE);

                    }else {
                        Toast.makeText(MainActivity.this, "Check internet connection !!", Toast.LENGTH_SHORT).show();
                    }


                }
                if (tabId == R.id.courses_list_tab){
                    // Toast.makeText(MainActivity.this, "courses", Toast.LENGTH_SHORT).show();
                    if (isNetworkAvailable()) {
                        if(clfobj == null)  clfobj =  new courses_list_fragment();
                      //  progBar.setVisibility(View.VISIBLE);
                     //   progBar.setVisibility(View.GONE);
                        FragmentTransaction frm = fragmentManager.beginTransaction().replace(R.id.frameLayout_main_activity, clfobj);
                        frm.commit();
                       // progBar.setVisibility(View.GONE);
                      //  progBar.setVisibility(View.INVISIBLE);
                    }    else {
                        Toast.makeText(MainActivity.this, "Check internet connection !!", Toast.LENGTH_SHORT).show();
                    }

                }
                if (tabId == R.id.notification_tab){
                    // Toast.makeText(MainActivity.this, "notifications", Toast.LENGTH_SHORT).show();

                    FragmentTransaction frm = fragmentManager.beginTransaction().replace(R.id.frameLayout_main_activity,new notification_fragment());
                    frm.commit();
                }
                if (tabId == R.id.serach_tab){
                    // Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                //    progBar.setVisibility(View.INVISIBLE);
                    FragmentTransaction frm = fragmentManager.beginTransaction().replace(R.id.frameLayout_main_activity,new search_fragment());
                    frm.commit();
                }



            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Menu_btn_profList) {
            // Handle the camera action
//            String url = "http://api.mim-app.ir/SelectValue_profList.php";
//            getJson= new GetJson(url);
//            getJson.execute("register","محمد","ایمیل","123654","پسورد");
//
//            try {
//                getJson.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            Intent i = new Intent(getApplicationContext() , Activity_proffListView.class);
//            i.putExtra("JSON_string_data",getJson.finalJson);
//            Log.i("in_mainactivity","intent ");
//            startActivity(i);


            Payment payment = new PaymentBuilder()
                    .setMerchantID("5201f796-43f4-4f6b-9015-493e5ee8a9d4")  //  This is an example, put your own merchantID here.
                    .setAmount(10000)                                         //  Amount in Toman
                    .setDescription("اهدا کمک هزینه برای طراحی برنامه ")
                    .create();

            ZarinPal.pay(this, payment, new OnPaymentListener() {
                @Override
                public void onSuccess(String refID) {
                    Log.wtf("TAG", "HOOOORAAAY!!! your refID is: " + refID);
                }

                @Override
                public void onFailure(ZarinPalError error) {
                    String errorMessage = "";
                    switch (error){
                        case INVALID_PAYMENT: errorMessage = "پرداخت تایید نشد"; break;
                        case USER_CANCELED:   errorMessage = "پرداخت توسط کاربر متوقف شد"; break;
                        case NOT_ENOUGH_DATA: errorMessage = "اطلاعات پرداخت کافی نیست"; break;
                        case UNKNOWN:         errorMessage = "خطای ناشناخته"; break;
                    }
                    Log.wtf("TAG", "ERROR: " + errorMessage);
                }
            });
        } else if (id == R.id.nav_courses_list) {

        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}