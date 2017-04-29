package ir.mim_app.mim.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
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
import ir.mim_app.mim.holderClass;
import ir.mim_app.mim.professor;
import ir.mim_app.mim.proff_detail_activity;
import ir.mim_app.mim.searchResultActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class search_fragment extends Fragment {
    //making progress bar appear and disappear :)
    ProgressBar progressBar;

    //making result of the search
    Professors_Listview_ArrayAdabter plvad;
    ListView lv;

    //getting listview and json
    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;

    List<String> profNameList = new ArrayList<>();
    List<String> courseNameList = new ArrayList<>();
    List<String> profIDList = new ArrayList<>();
    List<String> courseIDList = new ArrayList<>();
    List<holderClass> holderList = new ArrayList<>();

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

        progressBar = (ProgressBar) rootView.findViewById(R.id.Search_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

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
                Intent item_intent = new Intent(getContext(), searchResultActivity.class);
                item_intent.putExtra("queryString",queryString);
                startActivity(item_intent);

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


    String queryString;

    private void searchingFunction() {

        int od = ostadDars.getSelectedItemPosition();
        int li = retListQuery.getSelectedItemPosition();
        String retListString = retListQuery.getSelectedItem().toString();
        String odA = ""; //= profIDList.get(li);
        String odB = ""; //= courseIDList.get(li);
        for (holderClass s : holderList) {
            if (s.first().equals(retListString)){
                odA = s.second();
                odB = s.second();
            }
        }
        String p = String.valueOf(paye.getSelectedItemPosition()+1);



//        Toast.makeText(getContext(), "in: " + odA, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), "paye" + p, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), "profID" + odA, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), "profPos" + od, Toast.LENGTH_SHORT).show();


        if (od == 0){
            queryString = "SELECT courseInfoTable.courseID, courseName, ProfessorsID, family, coursePic " +
                    "FROM professorsTable, coursesTable, courseInfoTable WHERE courseInfoTable.activeFlag = 1 " +
                    " AND professorsTable.ActiveFlag = 1 " +
                    " AND profID = " + odA +
                    " AND professorsTable.ProfessorsID = coursesTable.profID" +
                    " AND courseInfoTable.courseID = coursesTable.courseID" +
                    " AND grade = " + p;
        }else {
            queryString = "SELECT courseInfoTable.courseID, courseName, ProfessorsID, family, coursePic " +
                    "FROM professorsTable, coursesTable, courseInfoTable WHERE courseInfoTable.activeFlag = 1 " +
                    " AND professorsTable.ActiveFlag = 1 " +
                    " AND professorsTable.ProfessorsID = coursesTable.profID" +
                    " AND courseInfoTable.courseID = " + odB +
                    " AND courseInfoTable.courseID = coursesTable.courseID" +
                    " AND grade = " + p;
        }

        /*
        url = "http://api.mim-app.ir/SelectValue_searchActivity.php";

        getJson = new GetJson(url);
        getJson.execute("listViewSearch", queryString);

        try {
            getJson.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JsonString = getJson.finalJson;

        Toast.makeText(getContext(), "this: " + JsonString, Toast.LENGTH_SHORT).show();



        //above part is time consuming part which need to be surrounded by async task!
        //fragments can be loaded whitout any delay by using async task but there will be needed to notify array adapteres & ...

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                String courseName;
                String courseID;
                String profID;
                String profName;
                String pic;

// in yek comment e deraaaaaaaaaaaaaaaz aaaaaaaaaaaaaaaaaaaaaastttttttttt

                try {


                    jsonobject = new JSONObject(JsonString);

                    int count =0;
                    jsonArray = jsonobject.getJSONArray("search_resualt");

                    while (count < jsonArray.length()){
                        JSONObject jo = jsonArray.getJSONObject(count);
                        courseID = jo.getString("courseID");
                        courseName = jo.getString("courseName");
                        profID = jo.getString("ProfessorsID");
                        profName = jo.getString("family");
                        pic = jo.getString("coursePic");

                        Toast.makeText(getContext(), courseID, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), courseName, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), profID, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), profName, Toast.LENGTH_SHORT).show();


                        //course course = new course(courseID, courseName, profID, profName, pic);
                        //professor professorOBJ = new professor(courseID, courseName, profID, profName, "pic",true);
                        //plvad.add(professorOBJ);

                        count++;

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }


                plvad = new Professors_Listview_ArrayAdabter(getContext(),R.layout.row_profflist);
                //lv.setAdapter(plvad);

                Toast.makeText(getContext(), "everything's working. be happy :)", Toast.LENGTH_SHORT).show();

                return null;
            }


            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                progressBar.setVisibility(View.INVISIBLE);

            }
        };
        */

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


                if (profNameList.contains(profName)){
                    //do nothing
                }else {
                    holderClass holderObj = new holderClass(profName, profID);
                    profNameList.add(profName);
                    profIDList.add(profID);
                    holderList.add(holderObj);
                }


                if (courseIDList.contains(courseID)){
                    //do nothing
                }else {
                    holderClass holderObj = new holderClass(courseName, courseID);
                    courseNameList.add(courseName);
                    courseIDList.add(courseID);
                    holderList.add(holderObj);
                }

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
