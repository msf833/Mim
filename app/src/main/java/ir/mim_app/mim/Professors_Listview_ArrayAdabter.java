package ir.mim_app.mim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSF on 4/9/2017.
 */

public class Professors_Listview_ArrayAdabter extends ArrayAdapter {



    String urlTemp;

    List list = new ArrayList();

    public Professors_Listview_ArrayAdabter( Context context,  int resource) {
        super(context, resource);
    }


    public void add(professor object) {

        urlTemp = object.profPic;

        list.add(object);

    }

    @Override
    public int getCount() {
        return list.size();

    }


    @Override
    public Object getItem(int position) {

        return list.get(position);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_profflist, null, false);
        }

        professor prf = (professor) getItem(position);

        if (prf.aliRid == false) {
   /* seetings up profs atributes */

            TextView tv_profName = (TextView) row.findViewById(R.id.TV_proffname);
            //tv_profName.setText(prf.getProfName());

            TextView tv_profFamily = (TextView) row.findViewById(R.id.TV_ProffFamily);

            tv_profFamily.setText(prf.getProfFamily());

            // TV_ProfessorsID
            TextView TV_ProfessorsID = (TextView) row.findViewById(R.id.TV_ProfessorsID);

            TV_ProfessorsID.setText(prf.getProfessorsID());

            String pName = prf.getProfName() + " " + prf.getProfFamily();
            tv_profFamily.setText(pName);


            ImageView iv_profPic = (ImageView) row.findViewById(R.id.IV_proffIamge);
            String url = prf.getProfPic();

            Picasso.with(getContext()).load(url).into(iv_profPic);

            //professorHolderobj.ali = (AsyncTaskLoadImage) new AsyncTaskLoadImage( professorHolderobj.iv_profPic  ).execute(url);

            // professorHolderobj.tv_profPic = (TextView) row.findViewById(R.id.tv_profPic);
            RatingBar tv_profRate = (RatingBar) row.findViewById(R.id.TV_ProfRate);
            tv_profRate.setRating(prf.getProfRate());
            return row;
        } else {
          //  TextView tv_profName = (TextView) row.findViewById(R.id.TV_proffname);
            //tv_profName.setText(prf.getProfName());

            //TextView tv_profFamily = (TextView) row.findViewById(R.id.TV_ProffFamily);
            TextView tv_courseName_alir = (TextView) row.findViewById(R.id.tv_courseName_alir);
            tv_courseName_alir.setVisibility(View.VISIBLE);
            tv_courseName_alir.setText(prf.getCourseName());

          //  tv_profFamily.setText(prf.getProfFamily());

            // TV_ProfessorsID
         //   TextView TV_ProfessorsID = (TextView) row.findViewById(R.id.TV_ProfessorsID);

         //   TV_ProfessorsID.setText(prf.getProfessorsID());

          //  String pName = prf.getProfName() + " " + prf.getProfFamily();
         //   tv_profFamily.setText(pName);


           // ImageView iv_profPic = (ImageView) row.findViewById(R.id.IV_proffIamge);
           // String url = prf.getProfPic();

           // Picasso.with(getContext()).load(url).into(iv_profPic);

            //professorHolderobj.ali = (AsyncTaskLoadImage) new AsyncTaskLoadImage( professorHolderobj.iv_profPic  ).execute(url);

            // professorHolderobj.tv_profPic = (TextView) row.findViewById(R.id.tv_profPic);
           // RatingBar tv_profRate = (RatingBar) row.findViewById(R.id.TV_ProfRate);
          //  tv_profRate.setRating(prf.getProfRate());
            return row;
        }

    }


}
//this is a good example and i didn,t used it in my app
 class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
    private final static String TAG = "AsyncTaskLoadImage";
    private ImageView imageView;
    public AsyncTaskLoadImage(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
