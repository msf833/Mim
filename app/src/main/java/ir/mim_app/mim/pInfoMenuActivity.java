package ir.mim_app.mim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class pInfoMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_info_menu);


        TextView nameFamily = (TextView) findViewById(R.id.nameFamily);
        TextView Txt_more = (TextView) findViewById(R.id.Txt_more);

        Txt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoEdit = new Intent(getApplicationContext(), infoEditActivity.class);
                startActivity(infoEdit);
            }
        });

    }
}
