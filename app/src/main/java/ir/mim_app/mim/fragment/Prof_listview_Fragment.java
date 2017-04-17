package ir.mim_app.mim.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.mim_app.mim.GetJson;
import ir.mim_app.mim.Professors_Listview_ArrayAdabter;
import ir.mim_app.mim.R;
import ir.mim_app.mim.professor;
import ir.mim_app.mim.proff_detail_activity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Prof_listview_Fragment extends Fragment {
    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;
    Professors_Listview_ArrayAdabter plvad;
    ListView lv;
    GridView gv;
    List piclist = new ArrayList();
    ProgressBar progBar;



    public Prof_listview_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_prof_listview, container, false);
        Bundle bundle = this.getArguments();
        String url = "http://api.mim-app.ir/SelectValue_profList.php";
        getJson= new GetJson(url);
        getJson.execute("register","محمد","ایمیل","123654","پسورد");


        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Bundle Fextras = getIntent().getExtras();
     //   progBar.setVisibility(View.VISIBLE);
        progBar = (ProgressBar) getView().findViewById(R.id.Fragment_prof_progressBar);

        progBar.setVisibility(View.VISIBLE);

        lv = (ListView) getView().findViewById(R.id.LV_fragment_prfoListView);
        //gv = (GridView) getView().findViewById(R.id.ProfFragment_gridview);
        //gv.setVisibility(View.INVISIBLE);
       // lv.setVisibility(View.VISIBLE);
        plvad = new Professors_Listview_ArrayAdabter(getContext(),R.layout.row_profflist);
      //  progBar.setVisibility(View.VISIBLE);
        try {
            getJson.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        JsonString = getJson.finalJson;
       //gv.setAdapter(plvad);
        lv.setAdapter(plvad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemposition = position ;
                String name = ((TextView) view.findViewById(R.id.TV_proffname)).getText().toString();
                String family = ((TextView) view.findViewById(R.id.TV_ProffFamily)).getText().toString();
                ((ImageView) view.findViewById(R.id.IV_proffIamge)).buildDrawingCache();
                Bitmap profimage = ((ImageView) view.findViewById(R.id.IV_proffIamge)).getDrawingCache();

                //        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();

                Intent item_intent = new Intent(getContext(), proff_detail_activity.class);
                Bundle extras = new Bundle();
                extras.putParcelable("profimage", profimage);
                item_intent.putExtras(extras);
                item_intent.putExtra("name",name);
                item_intent.putExtra("family",family);

                startActivity(item_intent);

            }
        });
        // gv = (GridView) findViewById(R.id.secGridView);
        //gv.setAdapter(plvad);
       // JsonString = Fextras.getExtras().getString("JSON_string_data");



        String profName;
        String profFamily;
        String profPic;
        String profRate;

        try {


            jsonobject = new JSONObject(JsonString);

            int count =0;
            jsonArray = jsonobject.getJSONArray("prof_list");


            while (count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                profName = jo.getString("name");
                profFamily = jo.getString("family");
                profPic = jo.getString("pic");
                profRate = jo.getString("rate");


                professor professorOBJ = new professor(profName,profFamily,profPic,Float.parseFloat(profRate));
                plvad.add(professorOBJ);
                count++;



            }
           // progBar.setVisibility(View.GONE);
            //gv.setVisibility(View.VISIBLE);


        } catch (JSONException e) {

            e.printStackTrace();
        }

        //Toast.makeText(getApplicationContext(), "prof_list", Toast.LENGTH_LONG).show();




        //  JsonString = getJson.finalJson;
        Log.i("in proflist_activity","json string ->" + JsonString);


    }

}
