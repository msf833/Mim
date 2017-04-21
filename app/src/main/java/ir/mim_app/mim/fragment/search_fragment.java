package ir.mim_app.mim.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ir.mim_app.mim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class search_fragment extends Fragment {

    boolean maghta = false;
    boolean form_o_c = true;

    public search_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_search_fragment2, container, false);
        TextView Text_addFilter = (TextView) rootView.findViewById(R.id.Text_addFilter);
        final RadioGroup rgp_level = (RadioGroup) rootView.findViewById(R.id.RGp_level);
        final RadioGroup rgp_maghta = (RadioGroup) rootView.findViewById(R.id.RGp_maghta);
        final RadioGroup gp1 = (RadioGroup) rootView.findViewById(R.id.RGp_1);
        final LinearLayout Linear_paye = (LinearLayout) rootView.findViewById(R.id.Linear_paye);
        final RadioButton RBtn_M_dabestan = (RadioButton) rootView.findViewById(R.id.RBtn_M_dabestan);

        ImageView imgSearch = (ImageView) rootView.findViewById(R.id.imgSearch);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here query should be created and sent
            }
        });

        Text_addFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout addFilter = (LinearLayout) rootView.findViewById(R.id.View_filter);

                if (addFilter.getVisibility() == View.INVISIBLE){
                    addFilter.setVisibility(View.VISIBLE);
                }else {
                    addFilter.setVisibility(View.INVISIBLE);
                    rgp_level.clearCheck();
                    gp1.clearCheck();
                    rgp_maghta.clearCheck();
                    RBtn_M_dabestan.setChecked(false);
                }
            }
        });

        rgp_maghta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == group.getId()+1){
                   maghta = false;
                    rgp_level.clearCheck();
                    gp1.clearCheck();

                }else {
                    maghta = true;
                    rgp_level.clearCheck();
                    gp1.clearCheck();
                }
                Linear_paye.setVisibility(View.INVISIBLE);
            }
        });

        rgp_level.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rgp_maghta.getCheckedRadioButtonId() == -1){
                    RBtn_M_dabestan.setChecked(true);
                }

                Linear_paye.setVisibility(View.VISIBLE);

                gp1.setVisibility(View.VISIBLE);

                RadioButton Rbtn_1 = (RadioButton) rootView.findViewById(R.id.RBtn_p_1);
                RadioButton Rbtn_2 = (RadioButton) rootView.findViewById(R.id.RBtn_p_2);
                RadioButton Rbtn_3 = (RadioButton) rootView.findViewById(R.id.RBtn_p_3);

                if (checkedId == group.getId()+1){
                    if (!maghta){
                        Rbtn_1.setText("اول");
                        Rbtn_2.setText("دوم");
                        Rbtn_3.setText("سوم");
                    }else {
                        Rbtn_1.setText("هفتم");
                        Rbtn_2.setText("هشتم");
                        Rbtn_3.setText("نهم");
                    }
                }else {
                    if (!maghta) {
                        Rbtn_1.setText("چهارم");
                        Rbtn_2.setText("پنجم");
                        Rbtn_3.setText("ششم");
                    }else {
                        Rbtn_1.setText("دهم");
                        Rbtn_2.setText("یازدهم");
                        Rbtn_3.setText("دوازدهم");
                    }
                }
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }


}
