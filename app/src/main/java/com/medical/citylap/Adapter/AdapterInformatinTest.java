package com.medical.citylap.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.medical.citylap.R;
import com.medical.citylap.activity.ShowMedicalInformation;
import com.medical.citylap.modles.InformationTest;
import com.medical.citylap.modles.MedicalInformationModel;

import java.util.ArrayList;

public class AdapterInformatinTest   extends RecyclerView.Adapter<AdapterInformatinTest.ViewHolder2> {
    Context mContext;
    ArrayList<InformationTest> informationTests=new ArrayList<>();

    @NonNull
    @Override
    public AdapterInformatinTest.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information_befor_test,parent,false);
        return new AdapterInformatinTest.ViewHolder2(view);
    }

    public AdapterInformatinTest( Context mContext,ArrayList<InformationTest> informationTests) {

        this.mContext = mContext;
        this.informationTests=informationTests;
    }

    public AdapterInformatinTest() {
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInformatinTest.ViewHolder2 holder, int position) {
        boolean isExpand = informationTests.get(position).isExpand();
        holder.content.setVisibility(isExpand ? View.VISIBLE : View.GONE);
        holder.title.setText(informationTests.get(position).getTitle());

        if (informationTests.get(position).isExpand() == false)
        {
            holder.content.setVisibility(View.GONE);

        }
        if (informationTests.get(position).isExpand() == true)
        {

            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText(informationTests.get(position).getContent());
        }


    }

    @Override
    public int getItemCount() {
        if(informationTests.size()>0)
            return informationTests.size();
        else
            return 0;
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder
    {
   TextView title,content;


        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

           title=itemView.findViewById(R.id.titleinformationtest);
           content=itemView.findViewById(R.id.contentinformationtest);
           title.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   informationTests.get(getAdapterPosition()).setExpand(!informationTests.get(getAdapterPosition()).isExpand());
                   notifyItemChanged(getLayoutPosition());

               }
           });
        }
    }


}
