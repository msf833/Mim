package ir.mim_app.mim.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mim_app.mim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Prof_listview_Fragment extends Fragment {


    public Prof_listview_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prof_listview, container, false);
    }

}
