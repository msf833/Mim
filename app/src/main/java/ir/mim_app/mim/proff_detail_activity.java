package ir.mim_app.mim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class proff_detail_activity extends AppCompatActivity {
    GetJson getJson;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;
    Professors_Listview_ArrayAdabter plvad;
    ListView lv;
    GridView gv;
    List piclist = new ArrayList();
    String proffidfrombundle ;
    String profresume="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proff_detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("profimage");
        ImageView profimg = (ImageView) findViewById(R.id.IV_ProfDet_Profimg);
        profimg.setImageBitmap(bmp);
        TextView tv_name = ((TextView) findViewById(R.id.TV_ProfDet_NAME));
        tv_name.setText(extras.getString("name"));

        TextView tv_family = ((TextView) findViewById(R.id.TV_ProfDet_FAMILY));
        tv_family.setText(extras.getString("family"));

        TextView tvProfessorsID = ((TextView) findViewById(R.id.tv_profID_Detailview));
        proffidfrombundle = extras.getString("ProfessorsID");
        tvProfessorsID.setText(proffidfrombundle);

        RatingBar rb = ((RatingBar) findViewById(R.id.RateBar_proffDetailactivity));
        rb.setRating(extras.getFloat("rate"));


       // Toast.makeText(getApplicationContext(), proffidfrombundle, Toast.LENGTH_SHORT).show();

        String url = "http://api.mim-app.ir/select_profResume.php";
        getJson = new GetJson(url);
        getJson.execute("proffresume", proffidfrombundle);

        try {
            getJson.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Button btn_sabt = (Button) findViewById(R.id.btn_setTimeToSend);
        btn_sabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent item_intent = new Intent(getApplicationContext(), SetTimeActivity.class);
                Bundle extras = new Bundle();

                item_intent.putExtra("name", extras.getString("name"));
                item_intent.putExtra("ProfessorsID", proffidfrombundle);
                item_intent.putExtra("family", extras.getString("family"));

                startActivity(item_intent);
                finish();
            }
        });

        // TV_ProffResume


        if (getJson.Recieved) {
            try {


                jsonobject = new JSONObject(getJson.finalJson);

                int count = 0;
                jsonArray = jsonobject.getJSONArray("resumearr");


                JSONObject jo = jsonArray.getJSONObject(0);

                profresume = jo.getString("resume");

                //Toast.makeText(getApplicationContext(), profresume, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {

                e.printStackTrace();
            }

            TextView TV_ProffResume = ((TextView) findViewById(R.id.TV_ProffResume));
            TV_ProffResume.setText(profresume);


        }
    }


    @Override
    protected void onResume() {
        super.onResume();


        ProgressBar progressBar4 = ((ProgressBar) findViewById(R.id.progressBar4));
        progressBar4.setVisibility(View.GONE);
    }
}
