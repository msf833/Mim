package ir.mim_app.mim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Activity_proffListView extends AppCompatActivity {
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
        setContentView(R.layout.activity_proff_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        plvad = new Professors_Listview_ArrayAdabter(getApplicationContext(),R.layout.row_profflist);
        lv = (ListView) findViewById(R.id.LV_ProffListView);
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

                piclist.add(profPic);




                professor professorOBJ = new professor(profName,profFamily,profPic,Float.parseFloat(profRate));
                plvad.add(professorOBJ);
                count++;



            }



        } catch (JSONException e) {

            e.printStackTrace();
        }

        //Toast.makeText(getApplicationContext(), "prof_list", Toast.LENGTH_LONG).show();




     //  JsonString = getJson.finalJson;
        Log.i("in proflist_activity","json string ->" + JsonString);


    }

}

