package ir.mim_app.mim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static java.security.AccessController.getContext;

/**
 * Created by NFP_7037 on 4/29/2017.
 */

public class searchResultActivity extends AppCompatActivity {

    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;

    Professors_Listview_ArrayAdabter plvad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        ListView lv = (ListView) findViewById(R.id.search_result_list);
        plvad = new Professors_Listview_ArrayAdabter(getApplicationContext(),R.layout.row_profflist);
        lv.setAdapter(plvad);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemposition = position ;
                String name = ((TextView) view.findViewById(R.id.TV_proffname)).getText().toString();
                float rb = ((RatingBar) view.findViewById(R.id.TV_ProfRate)).getRating();
                String family = ((TextView) view.findViewById(R.id.TV_ProffFamily)).getText().toString();
                ((ImageView) view.findViewById(R.id.IV_proffIamge)).buildDrawingCache();
                String ProfessorsID = ((TextView) view.findViewById(R.id.TV_ProfessorsID)).getText().toString();
                Bitmap profimage = ((ImageView) view.findViewById(R.id.IV_proffIamge)).getDrawingCache();
                String  tcourseName= ((TextView) view.findViewById(R.id.tv_courseName_alir)).getText().toString();
                //  Toast.makeText(getContext(),name,Toast.LENGTH_LONG).show();

                Intent item_intent = new Intent(getApplicationContext(), proff_detail_activity.class);
                Bundle extras = new Bundle();
                extras.putParcelable("profimage", profimage);
                item_intent.putExtras(extras);
                item_intent.putExtra("ProfessorsID",ProfessorsID);

                item_intent.putExtra("name",name);
                item_intent.putExtra("rate",rb);
                item_intent.putExtra("family",family);
                item_intent.putExtra("courseNameText",tcourseName);
                startActivity(item_intent);

            }
        });
        String queryString = getIntent().getStringExtra("queryString");


        url ="http://api.mim-app.ir/SelectValue_searchActivity.php";

        getJson =new GetJson(url);
        getJson.execute("listViewSearch",queryString);

        try {
            getJson.get();
        } catch(InterruptedException e) {
            e.printStackTrace();
        } catch(ExecutionException e) {
            e.printStackTrace();
        }

        JsonString =getJson.finalJson;

      //  Toast.makeText( getApplicationContext(), "this: "+JsonString,Toast.LENGTH_SHORT).show();


        //above part is time consuming part which need to be surrounded by async task!
        //fragments can be loaded whitout any delay by using async task but there will be needed to notify array adapteres & ...

        String courseName;
        String courseID;
        String profID;
        String profName;
        String pic;

// in yek comment e deraaaaaaaaaaaaaaaz aaaaaaaaaaaaaaaaaaaaaastttttttttt

        try {


            jsonobject = new JSONObject(JsonString);

            int count = 0;
            jsonArray = jsonobject.getJSONArray("search_resualt");

            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                courseID = jo.getString("courseID");
                courseName = jo.getString("courseName");
                profID = jo.getString("ProfessorsID");
                profName = jo.getString("family");
                pic = jo.getString("coursePic");

//                Toast.makeText(getApplicationContext(), courseID, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), courseName, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), profID, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), profName, Toast.LENGTH_SHORT).show();
//

                //course course = new course(courseID, courseName, profID, profName, pic);
                professor professorOBJ = new professor(courseID, courseName, profID, profName, pic,true);
                plvad.add(professorOBJ);

                count++;

            }


        } catch (JSONException e) {

            e.printStackTrace();
        }


        plvad = new Professors_Listview_ArrayAdabter(getApplicationContext(), R.layout.row_profflist);


      //  Toast.makeText(getApplicationContext(), "everything's working. be happy :)", Toast.LENGTH_SHORT).show();

    }
}