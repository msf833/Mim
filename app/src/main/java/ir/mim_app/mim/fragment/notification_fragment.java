package ir.mim_app.mim.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import ir.mim_app.mim.GetJson;
import ir.mim_app.mim.SignupActivity;
import ir.mim_app.mim.R;
import ir.mim_app.mim.assistClasses.studentAttributes;
import ir.mim_app.mim.event;
import ir.mim_app.mim.event_listview_arrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class notification_fragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    SharedPreferences sharedPreferences;

    ListView lv;
    event_listview_arrayAdapter elvad;
    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;
    GridView gv;
    ProgressBar progressBar;
Thread eeventloaderThread;


    SliderLayout mDemoSlider;

    public notification_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view =inflater.inflate(R.layout.fragment_notification_fragment, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDemoSlider = (SliderLayout)getView().findViewById(R.id.slider);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        boolean Registered = sharedPreferences.getBoolean("Registered", false);

        if (Registered == false){
            Intent item_intent = new Intent(getContext(), SignupActivity.class);
            startActivityForResult(item_intent, 1);
        }

        /*
        String url = "http://api.mim-app.ir/select_events.php";
        getJson= new GetJson(url);
        getJson.execute("eventget", studentAttributes.studentID);

        lv = (ListView) getView().findViewById(R.id.ListView_eventfragmen);

        elvad = new event_listview_arrayAdapter(getContext(),R.layout.row_event);


        eeventloaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                forthread();
            }
        });
        eeventloaderThread.run();
        progressBar = (ProgressBar) getView().findViewById(R.id.progreessbar_notifi_fragment);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("ویندوز 10", "http://shop.p30download.com/images/extra/1438624039_wind.10.jpg");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String username = sharedPreferences.getString("Username", "");
        String password = sharedPreferences.getString("Password", "");
        Toast.makeText(getContext(), "this is: " + username , Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "pass is: " + password , Toast.LENGTH_SHORT).show();
*/
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    void notification(){

    }
    void forthread(){
        String sendtimeStamp="";
        String mainContent="";
        String ActiveFlag="";
        String seeStatus="";
        String pic="";
        try {
            getJson.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JsonString = getJson.finalJson;
        lv.setAdapter(elvad);

        try {


            jsonobject = new JSONObject(JsonString);

            int count =0;
            jsonArray = jsonobject.getJSONArray("eventList");
            int a =  jsonArray.length();

            while (count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                mainContent= jo.getString("mainContent");
               /// sendtimeStamp = jo.getString("sendtimeStamp");
                ActiveFlag = jo.getString("ActiveFlag");
                seeStatus = jo.getString("seeStatus");



                event eventobj = new event(sendtimeStamp,mainContent,ActiveFlag,seeStatus);
                elvad.add(eventobj);
                count++;



            }



        } catch (JSONException e) {

            e.printStackTrace();
        }

       // progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        mDemoSlider = (SliderLayout)getView().findViewById(R.id.slider);

        String url = "http://api.mim-app.ir/select_events.php";
        getJson= new GetJson(url);
        getJson.execute("eventget", studentAttributes.studentID);

        lv = (ListView) getView().findViewById(R.id.ListView_eventfragmen);

        elvad = new event_listview_arrayAdapter(getContext(),R.layout.row_event);


        eeventloaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                forthread();
            }
        });
        eeventloaderThread.run();
        progressBar = (ProgressBar) getView().findViewById(R.id.progreessbar_notifi_fragment);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("ویندوز 10", "http://shop.p30download.com/images/extra/1438624039_wind.10.jpg");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String username = sharedPreferences.getString("Username", "");
            String password = sharedPreferences.getString("Password", "");
            Toast.makeText(getContext(), "this is: " + username , Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), "pass is: " + password , Toast.LENGTH_LONG).show();
        }
    }//onActivityResult
}
