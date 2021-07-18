package com.medical.citylap.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.medical.citylap.R;
import com.medical.citylap.modles.Datum;

import java.util.ArrayList;

public class OfferAdapter   extends RecyclerView.Adapter<OfferAdapter.ViewHolder2> {

    ArrayList<Datum>listoffer=new ArrayList<>();
    private Context mContext;
    Uri uri;
    public void setlist(ArrayList<Datum> listOffer){

        this.listoffer=listOffer;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer,parent,false);
        return new ViewHolder2(view);
    }

    public OfferAdapter( Context mContext) {

        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {
     holder.title.setText(listoffer.get(position).getTitle());
     holder.discreption.setText(listoffer.get(position).getDescription());
       String endTime= removefromdate(listoffer.get(position).getEndTime());
       String startTime=removefromdate(listoffer.get(position).getStartTime());
     holder.olddate.setText("ينتهى في:"+endTime);
     holder.newdate.setText("يبدأ من:"+startTime);
     holder.oldprice.setText("جنيه :"+listoffer.get(position).getPreviousPrice().toString());
     holder.newprice.setText("جنيه :"+listoffer.get(position).getCurrentPrice().toString());
        Glide.with(mContext).load("http://"+listoffer.get(position).getFiles().get(0))
              .into(holder.image);


    }

    @Override
    public int getItemCount() {
        if(listoffer.size()>0)
        return listoffer.size();
        else
            return 0;
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder
    {
public TextView title,discreption,oldprice,newprice,olddate,newdate;
public ImageView image;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_offers_id);
            discreption=itemView.findViewById(R.id.description_offer_id);
            oldprice=itemView.findViewById(R.id.old_price_offer_id);
            newprice=itemView.findViewById(R.id.new_price_offer_id);
            olddate=itemView.findViewById(R.id.end_data_offer_id);
            newdate=itemView.findViewById(R.id.start_data_offer_id);
            image=itemView.findViewById(R.id.image_offer_id);
            discreption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.customdaliog_text);       // Include dialog.xml file
                    dialog.show();      // Include dialog.xml file
                    TextView textView=dialog.findViewById(R.id.text_in_dalog);
                    textView.setText(listoffer.get(getAdapterPosition()).getDescription().toString());

                }
            });
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.custom_only_imag);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
// Include dialog.xml file
                    dialog.show();      // Include dialog.xml file
                    ImageView imaged=dialog.findViewById(R.id.only_image);
                    Glide.with(mContext).load("http://"+listoffer.get(getAdapterPosition()).getFiles().get(0))
                            .into(imaged);

                }
            });
        }
    }
    public String removefromdate(String s)
    {
        String[] date = s.split(" ");
        return date[0];
    }

}
