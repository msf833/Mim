package ir.mim_app.mim;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSF on 4/28/2017.
 */

public class event_listview_arrayAdapter extends ArrayAdapter {


    List list = new ArrayList();
    public event_listview_arrayAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add (event object) {
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
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View row ;
        row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_event, null, false);
        }

        event Oevent = (event) getItem(position);

        TextView Tv_maincontent = (TextView) row.findViewById(R.id.Tv_maincontent);
        Tv_maincontent.setText(Oevent.getMainContent());

        return row;
    }
}
