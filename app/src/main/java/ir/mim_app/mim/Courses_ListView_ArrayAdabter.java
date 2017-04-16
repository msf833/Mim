package ir.mim_app.mim;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSF on 4/14/2017.
 */

public class Courses_ListView_ArrayAdabter  extends ArrayAdapter{

    String urlTemp;

    List list = new ArrayList();

    public Courses_ListView_ArrayAdabter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add(course object) {
        list.add(object);
    }


    public int getCount() {
        return list.size();
    }


    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row ;
        row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_courselist, null, false);
        }

        course crs = (course) getItem(position);

        TextView tv_crsfName = (TextView) row.findViewById(R.id.TV_Course_Name);
        tv_crsfName.setText(crs.getCourseName());
        TextView tv_courseID = (TextView) row.findViewById(R.id.TV_courseID);
        tv_crsfName.setText(crs.getCourseID());
        return row;

    }

}
