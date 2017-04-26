package ir.mim_app.mim.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.mim_app.mim.Courses_ListView_ArrayAdabter;
import ir.mim_app.mim.GetJson;
import ir.mim_app.mim.R;
import ir.mim_app.mim.course;

/**
 * A simple {@link Fragment} subclass.
 */
public class search_fragment extends Fragment {

    //getting listview and json
    Courses_ListView_ArrayAdabter clvad;
    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;



    boolean maghta = false;
    boolean form_o_c = true;

    public search_fragment() {
        // Required empty public constructor
    }

    Spinner retListQuery;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_search_fragment2, container, false);

        Spinner ostadDars = (Spinner) rootView.findViewById(R.id.spinnerOstadDars);
        retListQuery = (Spinner) rootView.findViewById(R.id.spinnerRetFromQuery);
        Spinner paye = (Spinner) rootView.findViewById(R.id.spinnerPaye);
        Button searchBtn = (Button) rootView.findViewById(R.id.Btn_search);

        String[] ostadDars_items = new String[] {"نام استاد", "نام درس"};
        String[] paye_items  = new String[] {"کلاس اول","کلاس دوم","کلاس سوم","کلاس چهارم","کلاس پنجم","کلاس ششم","کلاس هفتم","کلاس هشتم","کلاس نهم","کلاس دهم","کلاس یازدهم","کلاس دوازدهم"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, paye_items);

        paye.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, ostadDars_items);

        ostadDars.setAdapter(adapter3);

        searchFunc();

        /*String[] stockArr = new String[stockList.size()];
        stockArr = stockList.toArray(stockArr);*/

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, stockArr);
        retListQuery.setAdapter(adapter);
*/
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Inflate the layout for this fragment
        return rootView;
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

        String temp = getJson.finalJson;

        String courseName;
        String courseID;
        String profID;
        String profName;

// in yek comment e deraaaaaaaaaaaaaaaz aaaaaaaaaaaaaaaaaaaaaastttttttttt


        try {


            jsonobject = new JSONObject(JsonString);

            int count =0;
            jsonArray = jsonobject.getJSONArray("search_resp");

            while (count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                courseName = jo.getString("courseID");
                courseID = jo.getString("courseName");
                profID = jo.getString("ProfessorsID");
                profName = jo.getString("family");

                course courseobj = new course(courseName, profName, courseID, profID);
                clvad.add(courseobj);

                count++;

            }


        } catch (JSONException e) {

            e.printStackTrace();
        }

    }


}
