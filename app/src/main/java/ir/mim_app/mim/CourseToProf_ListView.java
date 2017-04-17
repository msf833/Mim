package ir.mim_app.mim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseToProf_ListView extends  AppCompatActivity {
    GetJson getJson;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;
    Professors_Listview_ArrayAdabter plvad;
    ListView lv;
    GridView gv;
    List piclist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_to_prof__list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        plvad = new Professors_Listview_ArrayAdabter(getApplicationContext(),R.layout.row_profflist);
        lv = (ListView) findViewById(R.id.LV_coursetoProffListView);
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

                Intent item_intent = new Intent(getApplicationContext(), proff_detail_activity.class);
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
        JsonString = getIntent().getExtras().getString("JSON_string_data");
       // Toast.makeText(getApplicationContext(), JsonString, Toast.LENGTH_SHORT).show();
         TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(JsonString);
        String profName;
        String profFamily;
        String profPic;
        String profRate;

        try {


            jsonobject = new JSONObject(JsonString);

            int count = 0;
            jsonArray = jsonobject.getJSONArray("coursetoprofs_list");


            while (count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                profName = jo.getString("name");
                profFamily = jo.getString("family");
                profPic = jo.getString("pic");
                profRate = jo.getString("rate");

                piclist.add(profPic);




                professor professorOBJ = new professor(profName,profFamily,profPic,Float.parseFloat(profRate));
                plvad.add(professorOBJ);
                count++;



            }



        } catch (JSONException e) {

            e.printStackTrace();
        }





        //  JsonString = getJson.finalJson;
        Log.i("in proflist_activity","json string ->" + JsonString);


    }


    }


