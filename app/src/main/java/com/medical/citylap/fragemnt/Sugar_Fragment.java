package com.medical.citylap.fragemnt;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.medical.citylap.R;

public class Sugar_Fragment extends Fragment {
    EditText Age,sugerleve,wight;
    TextView information;
    Button show_information;
    CardView cardView;
    public Sugar_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sugar_, container, false);
    }
    public void inti(View view)
    {
        Age=view.findViewById(R.id.age_in_sugar_leve);
        sugerleve=view.findViewById(R.id.sugar_in_sugar_leve);
        wight=view.findViewById(R.id.wight_in_sugar_leve);
        information=view.findViewById(R.id.information_in_sugar_leve);
        show_information=view.findViewById(R.id.confer_sugar);
        cardView=view.findViewById(R.id.cardView_sugar);
    }
}