package ir.mim_app.mim;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.mim_app.mim.assistClasses.TimePickerFragment;
import ir.mim_app.mim.assistClasses.studentAttributes;

import ir.mim_app.mim.assistClasses.studentAttributes;

public class SetTimeActivity extends AppCompatActivity {

    ListView lv;
    Courses_ListView_ArrayAdabter clvad;
    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;

    EditText iTexT_Comments;
    TextView itv_selectedCours_setTimeactivity;

    PersianDatePickerDialog picker;
    TextView itv_timeset;
    static String temp ="";

    int mHour = 0;
    int mMinute =0 ;
    String profid;


    int courseID=0;
    String classtime;
    String classDate;
    ProgressBar progBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progBar = (ProgressBar) findViewById(R.id.progressBar3);
        progBar.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        profid = extras.getString("ProfessorsID");

         iTexT_Comments = (EditText) findViewById( R.id.TexT_Comments);


        String url = "http://api.mim-app.ir/SelectValue_coursesList.php";
        getJson= new GetJson(url);
        getJson.execute("courseList","محمد","ایمیل","123654","پسورد");

        lv= (ListView) findViewById(R.id.LV_Settime_courseListview);
        clvad = new  Courses_ListView_ArrayAdabter(getApplicationContext(),R.layout.row_courselist);
        try {
            getJson.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JsonString = getJson.finalJson;
        //gv.setAdapter(clvad);
        lv.setAdapter(clvad);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemposition = position ;
                courseID = Integer.parseInt(((TextView) findViewById(R.id.TV_courseID)).getText().toString());
                itv_selectedCours_setTimeactivity  = (TextView) findViewById(R.id.tv_selectedCours_setTimeactivity);
                String coursename = ((TextView) findViewById(R.id.TV_Course_Name)).getText().toString();
                itv_selectedCours_setTimeactivity.setText(coursename);
            }
        });


        final TextView itv_date_settimeactivity = (TextView) findViewById(R.id.tv_date_settimeactivity);
         itv_timeset = (TextView) findViewById(R.id.tv_timeset);
       Button ibtn_setdate_settimeactivvity = (Button) findViewById(R.id.btn_setdate_settimeactivvity);
        Button ibtn_settime_settimeactivity = (Button) findViewById(R.id.btn_settime_settimeactivity);

        picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setMinYear(1396)
                .setTodayButtonVisible(true)
               .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        //Toast.makeText(getApplicationContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                        itv_date_settimeactivity.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                        classDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();

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


        ibtn_setdate_settimeactivvity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show();
            }
        });




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
                                classtime = hourOfDay + ":" + minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();



            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progBar.setVisibility(View.VISIBLE);




             String icoment = iTexT_Comments.getText().toString();


                String url = "http://api.mim-app.ir/SelectValue_profList.php";
                getJson= new GetJson(url);
                getJson.execute("sendClassSch",profid,(courseID+"").toString(), studentAttributes.studentID,classDate,classtime,icoment);

                Toast.makeText(getApplicationContext(), "درخواست شما ارسال شد", Toast.LENGTH_SHORT).show();
               // SetTimeActivity.this.finish();


            }
        });

        String courseName;
        String courseID;
        String profPic;
        String profRate;

        try {


            jsonobject = new JSONObject(JsonString);

            int count =0;
            jsonArray = jsonobject.getJSONArray("lessons_list");


            while (count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                courseName = jo.getString("courseName");
                courseID = jo.getString("courseID");



                course courseobj = new course(courseName,courseID);
                clvad.add(courseobj);
                count++;



            }

            progBar.setVisibility(View.GONE);

        } catch (JSONException e) {

            e.printStackTrace();
        }

    }

}


