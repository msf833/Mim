package ir.mim_app.mim;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.mim_app.mim.assistClasses.TimePickerFragment;
import ir.mim_app.mim.assistClasses.studentAttributes;

import ir.mim_app.mim.assistClasses.studentAttributes;

import static java.security.AccessController.getContext;

public class SetTimeActivity extends AppCompatActivity {

    List<String> courseNameList = new ArrayList<>();
    List<String> courseIDList = new ArrayList<>();

    boolean falseBashe;
    Spinner lv;
    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;

    EditText iTexT_Comments;
    TextView tv_selectedCours_setTimeactivity;

    PersianDatePickerDialog picker;
    TextView itv_timeset;
    static String temp ="";

    int mHour = 0;
    int mMinute =0 ;
    String profid;


    String classtime="";
    String classDate="";
    ProgressBar progBar;
    String icoment="";

    String courseName;
    String courseID="";
    String profPic;
    String profRate;



    boolean readytosendReq = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        progBar = (ProgressBar) findViewById(R.id.progressBar3);
        progBar.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        profid = extras.getString("ProfessorsID");

         iTexT_Comments = (EditText) findViewById( R.id.TexT_Comments);


        String url = "http://api.mim-app.ir/SelectValue_coursesList.php";
        getJson= new GetJson(url);
        getJson.execute("courseList","محمد","ایمیل","123654","پسورد");

        lv= (Spinner) findViewById(R.id.LV_Settime_courseListview);

        try {
            getJson.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JsonString = getJson.finalJson;
        //gv.setAdapter(clvad);

        final TextView itv_date_settimeactivity = (TextView) findViewById(R.id.tv_date_settimeactivity);
        itv_timeset = (TextView) findViewById(R.id.tv_timeset);

        picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setMinYear(1396)
                .setMaxYear(1396)
                .setTodayButtonVisible(true)
               .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        //Toast.makeText(getApplicationContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                        itv_date_settimeactivity.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                        classDate = persianCalendar.getPersianYear() + "_" + persianCalendar.getPersianMonth() + "_" + persianCalendar.getPersianDay();

                    }

                    @Override
                    public void onDisimised() {

                    }
                });


        DateFormat fmtDateAndTime=DateFormat.getDateTimeInstance();
        final Calendar dateAndTime=Calendar.getInstance();
        final TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateAndTime.set(Calendar.MINUTE, minute);

            }
        };

        //I replaced button click with textView click so the button can be cahanged to another textView

        itv_date_settimeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show();
            }
        });

        /*
        ibtn_setdate_settimeactivvity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show();
            }
        });
        */



        //I replaced button click with textView click so the button can be cahanged to another textView
        itv_timeset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "this is me ", Toast.LENGTH_SHORT).show();

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog( SetTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                itv_timeset.setText(hourOfDay + ":" + minute);
                                classtime = hourOfDay + "_" + minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();



            }
        });

        /*
        ibtn_settime_settimeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "this is me ", Toast.LENGTH_SHORT).show();

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog( SetTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                               itv_timeset.setText(hourOfDay + ":" + minute);
                                classtime = hourOfDay + "_" + minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });
        */

        Button btn_register = (Button) findViewById(R.id.btn_register_settimeactivity);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progBar.setVisibility(View.VISIBLE);
             //   String
              icoment = iTexT_Comments.getText().toString();




              if (courseID.equals("")){
                  Toast.makeText(getApplicationContext(), "یک درس انتخاب کنید ", Toast.LENGTH_SHORT).show();
                  readytosendReq = false;
              }
              else {
                  readytosendReq=true;
              }
              if (classDate.equals("")){
                  Toast.makeText(getApplicationContext(), "یک تاریخ انتخاب کنید ", Toast.LENGTH_SHORT).show();
                  readytosendReq = false;
              }
              else {
                  readytosendReq=true;
              }
              if (classtime.equals("")){
                    Toast.makeText(getApplicationContext(), "یک زمان انتخاب کنید ", Toast.LENGTH_SHORT).show();
                  readytosendReq = false;
              }
              else {
                  readytosendReq=true;
              }
                if (icoment.equals("")){
                    Toast.makeText(getApplicationContext(), "مباحث مورد نظر خود را بنویسید ", Toast.LENGTH_SHORT).show();
                    readytosendReq = false;
                }
                else {
                    readytosendReq=true;
                }
                if (readytosendReq == true){

                  //  Toast.makeText(getApplicationContext(), courseID + " " + classDate +" "+classtime +" "+ icoment, Toast.LENGTH_SHORT).show();
                    String url = "http://api.mim-app.ir/reserve_classTime.php";
                    GetJson igetJson= new GetJson(url);
                    igetJson.execute("sendClassSch",profid,courseID+"", classDate,classtime,icoment);
                    Toast.makeText(getApplicationContext(), "درخواست شما ارسال شد", Toast.LENGTH_SHORT).show();
                    progBar.setVisibility(View.GONE);
                    finish();
                }







            }
        });





        try {


            jsonobject = new JSONObject(JsonString);

            int count =0;
            jsonArray = jsonobject.getJSONArray("lessons_list");


            while (count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);

                courseNameList.add(jo.getString("courseName"));
                courseIDList.add(jo.getString("courseID"));

                count++;
            }

            progBar.setVisibility(View.GONE);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(SetTimeActivity.this,
                android.R.layout.simple_spinner_item, courseNameList);
        lv.setAdapter(adapter3);

        //String courseNameText = getIntent().getExtras().getString("courseNameText");
        String courseNameText = " از بالا یک درس انتخاب کنید";

        tv_selectedCours_setTimeactivity = (TextView) findViewById(R.id.tv_selectedCours_setTimeactivity);

        falseBashe = false;

        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    String temp =lv.getSelectedItem().toString();
                    tv_selectedCours_setTimeactivity.setText(temp);
                    int a = courseNameList.indexOf(temp);
                    courseID = courseIDList.get(a);

              

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SetTimeActivity.this, "یک درس انتهاب کنید ", Toast.LENGTH_SHORT).show();
            }
        });

        tv_selectedCours_setTimeactivity.setText(courseNameText.toString());


        Button btn_cancel = (Button) findViewById(R.id.Btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}


