package ir.mim_app.mim.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import ir.mim_app.mim.Courses_ListView_ArrayAdabter;
import ir.mim_app.mim.GetJson;
import ir.mim_app.mim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class search_fragment extends Fragment {

    //getting listview and json
    ListView lv;
    Courses_ListView_ArrayAdabter clvad;
    GetJson getJson;
    String url;
    String JsonString;
    JSONObject jsonobject = null;
    JSONArray jsonArray;



    boolean maghta = false;
    boolean form_o_c = true;

    public search_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_search_fragment2, container, false);

        Spinner ostadDars = (Spinner) rootView.findViewById(R.id.spinnerOstadDars);
        Spinner retListQuery = (Spinner) rootView.findViewById(R.id.spinnerRetFromQuery);
        Spinner paye = (Spinner) rootView.findViewById(R.id.spinnerPaye);

        String[] ostadDars_items = new String[] {"استاد", "درس"};
        String[] paye_items  = new String[] {"کلاس را انتخاب کنید","کلاس اول","کلاس دوم","کلاس سوم","کلاس چهارم","کلاس پنجم","کلاس ششم","کلاس هفتم","کلاس هشتم","کلاس نهم","کلاس دهم","کلاس یازدهم","کلاس دوازدهم"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, paye_items);

        paye.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, ostadDars_items);

        ostadDars.setAdapter(adapter3);




        // Inflate the layout for this fragment
        return rootView;
    }


}
