package ir.mim_app.mim;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class proff_detail_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proff_detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("profimage");
        ImageView profimg =(ImageView) findViewById(R.id.IV_ProfDet_Profimg);
        profimg.setImageBitmap(bmp );
       TextView tv_name = ((TextView)findViewById(R.id.TV_ProfDet_NAME));
       tv_name.setText (extras.getString("name"));

        TextView tv_family = ((TextView)findViewById(R.id.TV_ProfDet_FAMILY));
        tv_family.setText (extras.getString("family"));

        TextView tvProfessorsID = ((TextView)findViewById(R.id.tv_profID_Detailview));
        tvProfessorsID.setText (extras.getString("ProfessorsID"));



    }

}
