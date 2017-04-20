package ir.mim_app.mim;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.mim_app.mim.assistClasses.TimePickerFragment;

public class SetTimeActivity extends AppCompatActivity {


    PersianDatePickerDialog picker;
    TextView itv_timeset;
    static String temp ="";

    int mHour = 0;
    int mMinute =0 ;
    String profid;


    String classtime;
    String classDate;
    ProgressBar progBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        profid = extras.getString("ProfessorsID");


        progBar = (ProgressBar) findViewById(R.id.progressBar3);

        final TextView itv_date_settimeactivity = (TextView) findViewById(R.id.tv_date_settimeactivity);
         itv_timeset = (TextView) findViewById(R.id.tv_timeset);
       Button ibtn_setdate_settimeactivvity = (Button) findViewById(R.id.btn_setdate_settimeactivvity);
        Button ibtn_settime_settimeactivity = (Button) findViewById(R.id.btn_settime_settimeactivity);

        picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
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
            }
        });
    }

}


