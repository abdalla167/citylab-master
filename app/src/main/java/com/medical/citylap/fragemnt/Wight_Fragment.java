package com.medical.citylap.fragemnt;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import com.medical.citylap.R;


public class Wight_Fragment extends Fragment {

    public Wight_Fragment() {
        // Required empty public constructor
    }
    EditText Age,high,wight;
    TextView information;
    Button show_information;
    CardView cardView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wight, container, false);
    }
    public void inti(View view)
    {
        Age=view.findViewById(R.id.age_in_wight_leve);
        high=view.findViewById(R.id.high_in_wight_leve);
        wight=view.findViewById(R.id.wight_in_wight_leve);
        information=view.findViewById(R.id.information_in_wight_leve);
        show_information=view.findViewById(R.id.confer_wight);
        cardView=view.findViewById(R.id.cardView_wight);
    }
}