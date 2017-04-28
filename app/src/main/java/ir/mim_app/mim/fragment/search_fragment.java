package ir.mim_app.mim.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.mim_app.mim.Courses_ListView_ArrayAdabter;
import ir.mim_app.mim.GetJson;
import ir.mim_app.mim.Professors_Listview_ArrayAdabter;
import ir.mim_app.mim.R;
import ir.mim_app.mim.course;

/**
 * A simple {@link Fragment} subclass.
 */
public class search_fragment extends Fragment {

    //making result of the search
    Professors_Listview_ArrayAdabter plvad;
    ListView lv;

    //getting listview and json
    Courses_ListView_ArrayAdabter clvad;
    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;
    boolean darsOstad = true;

    List<String> profNameList = new ArrayList<>();
    List<String> courseNameList = new ArrayList<>();
    List<String> profIDList = new ArrayList<>();
    List<String> courseIDList = new ArrayList<>();

    boolean maghta = false;
    boolean form_o_c = true;

    public search_fragment() {
        // Required empty public constructor
    }

    Spinner ostadDars;
    Spinner retListQuery;
    Spinner paye;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_search_fragment2, container, false);

        ostadDars = (Spinner) rootView.findViewById(R.id.spinnerOstadDars);
        retListQuery = (Spinner) rootView.findViewById(R.id.spinnerRetFromQuery);
        paye = (Spinner) rootView.findViewById(R.id.spinnerPaye);
        Button searchBtn = (Button) rootView.findViewById(R.id.Btn_search);

        String[] ostadDars_items = new String[] {"نام استاد", "نام درس"};
        String[] paye_items  = new String[] {"کلاس اول","کلاس دوم","کلاس سوم","کلاس چهارم","کلاس پنجم","کلاس ششم","کلاس هفتم","کلاس هشتم","کلاس نهم","کلاس دهم","کلاس یازدهم","کلاس دوازدهم"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, paye_items);

        paye.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, ostadDars_items);

        ostadDars.setAdapter(adapter3);

        ostadDars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onResume();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onResume();
            }
        });

        searchFunc();


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchingFunction();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void searchingFunction() {

        int od = ostadDars.getSelectedItemPosition();
        String li = retListQuery.getSelectedItem().toString();
        String p = paye.getSelectedItem().toString();
        String odA = profIDList.get(od);
        String odB = courseIDList.get(od);

        url = "http://api.mim-app.ir/SelectValue_viewMaker_search.php";

        getJson= new GetJson(url);
        getJson.execute("listViewSearch","kk");

        try {
            getJson.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JsonString = getJson.finalJson;

        String courseName;
        String courseID;
        String profID;
        String profName;
        String rate;
        String pic;

// in yek comment e deraaaaaaaaaaaaaaaz aaaaaaaaaaaaaaaaaaaaaastttttttttt

        try {


            jsonobject = new JSONObject(JsonString);

            int count =0;
            jsonArray = jsonobject.getJSONArray("search_resp");

            while (count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                courseID = jo.getString("courseID");
                courseName = jo.getString("courseName");
                profID = jo.getString("ProfessorsID");
                profName = jo.getString("family");
                pic = jo.getString("pic");
                rate = jo.getString("rate");

                course course = new course(courseID, courseName, profID, profName, pic, rate);
                plvad.add(course);

                count++;

            }


        } catch (JSONException e) {

            e.printStackTrace();
        }

        lv = (ListView) getView().findViewById(R.id.LV_fragment_prfoListView);
        plvad = new Professors_Listview_ArrayAdabter(getContext(),R.layout.row_profflist);

    }

    private void searchFunc() {

        url = "http://api.mim-app.ir/SelectValue_listMaker_search.php";

        getJson= new GetJson(url);
        getJson.execute("listSearch","kk");

        try {
            getJson.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JsonString = getJson.finalJson;

        String courseName;
        String courseID;
        String profID;
        String profName;

        try {


            jsonobject = new JSONObject(JsonString);

            int count =0;
            jsonArray = jsonobject.getJSONArray("search_resp");

            while (count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                courseID = jo.getString("courseID");
                courseName = jo.getString("courseName");
                profID = jo.getString("ProfessorsID");
                profName = jo.getString("family");

                profNameList.add(profName);
                courseNameList.add(courseName);
                profIDList.add(profID);
                courseIDList.add(courseID);

                count++;

            }


        } catch (JSONException e) {

            e.printStackTrace();
        }

    }


    @Override
    public void onResume() {
        super.onResume();

        if (ostadDars.getSelectedItem().toString().equals("نام درس")){
            ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item,  courseNameList);
            retListQuery.setAdapter(adapter6);
        }else {
            ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item,  profNameList);
            retListQuery.setAdapter(adapter5);
        }

    }
}
