package ir.mim_app.mim.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import ir.mim_app.mim.CourseToProf_ListView;
import ir.mim_app.mim.Courses_ListView_ArrayAdabter;
import ir.mim_app.mim.GetJson;
import ir.mim_app.mim.R;
import ir.mim_app.mim.course;

/**
 * A simple {@link Fragment} subclass.
 */
public class courses_list_fragment extends Fragment {

    ListView lv;
    Courses_ListView_ArrayAdabter clvad;
    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;
    GridView gv;
    ProgressBar progressBar;


    public courses_list_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_courses_list_fragment, container, false);
        Bundle bundle = this.getArguments();

        String url = "http://api.mim-app.ir/SelectValue_coursesList.php";
        getJson= new GetJson(url);
        getJson.execute("courseList","محمد","ایمیل","123654","پسورد");
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //progress bar bar
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.VISIBLE);
        lv = (ListView) getView().findViewById(R.id.LV_fragment_coursesListView);
       // gv = (GridView) getView().findViewById(R.id.courseFragment_gridview);
        clvad = new  Courses_ListView_ArrayAdabter(getContext(),R.layout.row_courselist);
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
               // String name = ((TextView) view.findViewById(R.id.TV_proffname)).getText().toString();
               // String family = ((TextView) view.findViewById(R.id.TV_ProffFamily)).getText().toString();
               // ((ImageView) view.findViewById(R.id.IV_proffIamge)).buildDrawingCache();
               // Bitmap profimage = ((ImageView) view.findViewById(R.id.IV_proffIamge)).getDrawingCache();
              //  Toast.makeText(getContext(),"قراره اسم استادای  "+((TextView) view.findViewById(R.id.TV_Course_Name)).getText().toString() +" رو نشون بده", Toast.LENGTH_SHORT).show();

             // lv.setVisibility(View.INVISIBLE);
                String courseID = ((TextView) view.findViewById(R.id.TV_courseID)).getText().toString();

             //  progressBar.setVisibility(View.VISIBLE);
            //    progressBar.setVisibility(View.GONE);
                String url = "http://api.mim-app.ir/SelectValue_coursesList2profselect.php";
                getJson= new GetJson(url);
                getJson.execute("CourseToProf",courseID);

                try {
                    getJson.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getContext() , CourseToProf_ListView.class);
                i.putExtra("JSON_string_data",getJson.finalJson);
                Log.i("in courseSelectFragment","intent ");

               // progressBar.setVisibility(View.INVISIBLE);
              //  progressBar.setVisibility(View.GONE);
                startActivity(i);
               // progressBar.setVisibility(View.GONE);
              //  lv.setVisibility(View.VISIBLE);
                //Intent item_intent = new Intent(getContext(), Courses_Activity_ListView.class);
              // Bundle extras = new Bundle();
              // extras.putParcelable("profimage", profimage);
               //item_intent.putExtras(extras);
              // item_intent.putExtra("courseID",courseID);
              //  item_intent.putExtra("family",family);

                //startActivity(item_intent);

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

            progressBar.setVisibility(View.GONE);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        //Toast.makeText(getApplicationContext(), "prof_list", Toast.LENGTH_LONG).show();


        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_Fragment_coursesList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // progressBar.setVisibility(View.VISIBLE);
            }
        });


        //  JsonString = getJson.finalJson;
        Log.i("courselistview_fragment","json string ->" + JsonString);


    }
}
