package com.medical.citylap.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.medical.citylap.R;
import com.medical.citylap.activity.ResultActivty;
import com.medical.citylap.fragemnt.PDF_Fragment;
import com.medical.citylap.helperfunction.SavingPdf;
import com.medical.citylap.modles.CashModelSave;
import com.medical.citylap.modles.Data;
import com.medical.citylap.modles.Result;
import com.medical.citylap.modles.ResultApi;
import com.medical.citylap.modles.Resultcopy;
import com.medical.citylap.modles.Resultss;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class AdapterResult extends RecyclerView.Adapter<AdapterResult.ViewHolder2> {
    ResultApi resultApi;
    ArrayList<Result> listresult = new ArrayList<>();
    ArrayList<Resultss> listresultapi = new ArrayList<>();
    public ArrayList<CashModelSave> cashModelSaveslist;
    private Context mContext;
    SavingPdf savingPdf;
    Uri uri;
    CashModelSave cashModelSave;
    ArrayList<Resultcopy> re = new ArrayList<>();
    int network;
    String date;
    public void setlist1( ArrayList<Resultcopy> re) {
        this.re = re;
        this.cashModelSaveslist = new ArrayList<CashModelSave>();
        loadData();
        notifyDataSetChanged();

    }
    public void setlist2(ResultApi resultApi, ArrayList<Resultcopy> re) {
        this.resultApi = resultApi;
        this.re = re;
        this.cashModelSaveslist = new ArrayList<CashModelSave>();
        loadData();
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AdapterResult.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_result_befor, parent, false);
        return new AdapterResult.ViewHolder2(view);
    }

    public AdapterResult(Context mContext, int network) {
        this.mContext = mContext;
        this.network = network;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AdapterResult.ViewHolder2 holder, int position) {

        boolean isExpand = re.get(position).isExpand();
        if(network==1)
        {holder.typetest.setText(resultApi.getData().get(position).getNotes().toString());
        }
        holder.layout.setVisibility(isExpand ? View.VISIBLE : View.GONE);
        if (isExpand = re.get(position).isExpand() == false)
        {
            holder.imageViewmax.setImageResource(R.drawable.ic_baseline_add_24);
        }
        else {

            if (network==1) {
                holder.typetest.setText(resultApi.getData().get(position).getNotes() + "");
                date = resultApi.getData().get(position).getUploadDate().substring(0, 10);
                holder.dateinside.setText(date);

                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "onBindViewHolder: " + date1);
                String[] dayes = date1.toString().split(" ");
                Log.d(TAG, "onBindViewHolder: " + dayes[0].toLowerCase());

                if (dayes[0].toLowerCase().equals("sat")) {
                    holder.day.setText("اليوم : السبت");
                }
                if (dayes[0].toLowerCase().equals("sun")) {
                    holder.day.setText("اليوم : الاحد");
                }
                if (dayes[0].toLowerCase().equals("mon")) {
                    holder.day.setText("اليوم : الاثنين");
                }
                if (dayes[0].toLowerCase().equals("tue")) {
                    holder.day.setText("اليوم : الثلاثاء");
                }
                if (dayes[0].toLowerCase().equals("wed")) {
                    holder.day.setText("اليوم : الاربعاء");
                }
                if (dayes[0].toLowerCase().equals("thu")) {
                    holder.day.setText("اليوم : الخميس");
                }
                if (dayes[0].toLowerCase().equals("fri")) {
                    holder.day.setText("اليوم : الجمعه");
                }


                if (resultApi.getData().get(position).getMediaType() == 1) {
                    //file
                    holder.recycler.setVisibility(View.GONE);
                    holder.noimag.setVisibility(View.VISIBLE);
                    holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentJump(resultApi.getData().get(position).getFiles().get(0), network);

                        }
                    });
                    holder.showPdf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fragmentJump(resultApi.getData().get(position).getFiles().get(0), network);

                        }
                    });
                    //if networking is work
                    holder.downloadPdf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (network == 1) {
                                CashModelSave cashModelSave2 = new CashModelSave(Integer.parseInt(String.valueOf(resultApi.getData().get(position).getResultId())));
                                if (CheckIfExsit(cashModelSave2) == 1) {
                                    savingPdf = new SavingPdf(mContext, resultApi.getData().get(position).getNotes() + "",
                                            Integer.parseInt(String.valueOf(resultApi.getData().get(position).getResultId())));
                                    String path = savingPdf.DownloadFile(resultApi.getData().get(position).getFiles().get(0).trim(), 1);
                                    cashModelSave = new CashModelSave(1, path,
                                            Integer.parseInt(String.valueOf(resultApi.getData().get(position).getResultId())),
                                            resultApi.getData().get(position).getNotes() + "");
                                    cashModelSaveslist.add(cashModelSave);
                                    SaveData();
                                } else {
                                    Toast.makeText(mContext, "تم تحمل هذا الملف من قبل", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "لا يوجد اتصال بالانترنت", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                // if is images
                if (resultApi.getData().get(position).getMediaType() == 0) {

                        holder.recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        holder.adapter = new AdapteronlyImage(mContext, 1);
                        holder.adapter.setlist(resultApi.getData().get(position).getFiles());
                        holder.recycler.setAdapter(holder.adapter);
                        //if networking is work
                        holder.downloadPdf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CashModelSave cashModelSave2 = new CashModelSave(Integer.parseInt(String.valueOf(resultApi.getData().get(position).getResultId())));
                                if (CheckIfExsit(cashModelSave2) == 0) {
                                    Toast.makeText(mContext, "تم تحمل هذا الملف من قبل", Toast.LENGTH_SHORT).show();
                                } else {
                                    savingPdf = new SavingPdf(mContext, resultApi.getData().get(position).getNotes() + "",
                                            Integer.parseInt(String.valueOf(resultApi.getData().get(position).getResultId())));
                                    holder.downloadPdf.setClickable(false);
                                    Toast.makeText(mContext, "يتم بداء التحمبل الان", Toast.LENGTH_SHORT).show();
                                    List<String>path=new ArrayList<>();
                                    for (int i = 0; i < resultApi.getData().get(position).getFiles().size(); i++) {
                                        path.add(savingPdf.DownloadFile(resultApi.getData().get(position).getFiles().get(i).trim(), 0));

                                    }
                                    cashModelSave = new CashModelSave(0,
                                            Integer.parseInt(String.valueOf(resultApi.getData().get(position).getResultId())),
                                            resultApi.getData().get(position).getNotes() + "",path);
                                    cashModelSaveslist.add(cashModelSave);
                                    SaveData();
                                    holder.downloadPdf.setClickable(true);
                                }


                            }
                        });
                    }

            }
            if(network==0)
            {
                holder.typetest.setText(cashModelSaveslist.get(position).getNametest() + "");
                if (cashModelSaveslist.get(position).getType() == 1) {
                    //file
                    holder.recycler.setVisibility(View.GONE);
                    holder.noimag.setVisibility(View.VISIBLE);
                    holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentJump(cashModelSaveslist.get(position).getPath(), network);

                        }
                    });
                    holder.showPdf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fragmentJump(cashModelSaveslist.get(position).getPath(), network);
                        }
                    });

                }
                if (cashModelSaveslist.get(position).getType() == 0) {

                        holder.recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        holder.adapter = new AdapteronlyImage(mContext, 0);
                        holder.adapter.setlist(resultApi.getData().get(position).getFiles());
                        holder.recycler.setAdapter(holder.adapter);

                    }


            }
            holder.imageViewmax.setImageResource(R.drawable.ic_baseline_minimize_24);

            }
            // if is pdf

        }




    public void SaveData() {


        SharedPreferences sharedPref = mContext.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String retrivedphonenumber = sharedPref.getString("phonenumberuser", null);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cashModelSaveslist);
        editor.putString(retrivedphonenumber, json);
        editor.apply();


    }

    private void loadData() {
        SharedPreferences sharedPref = mContext.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String retrivedphonenumber = sharedPref.getString("phonenumberuser", null);

        Gson gson = new Gson();
        String json = sharedPref.getString(retrivedphonenumber, null);
        Type type = new TypeToken<ArrayList<CashModelSave>>() {
        }.getType();
        cashModelSaveslist = gson.fromJson(json, type);

    }

    public int CheckIfExsit(CashModelSave cashModelSave) {
        if (cashModelSaveslist == null) {
            cashModelSaveslist = new ArrayList<>();
            return 1;
        }

        for (int i = 0; i < cashModelSaveslist.size(); i++) {
            if (cashModelSaveslist.get(i).getId() == cashModelSave.getId())
                return 0;

        }

        return 1;
    }


    @Override
    public int getItemCount() {
        if(network==1) {
            if (resultApi.getData().size() > 0)
                return resultApi.getData().size();
            else
                return 0;
        }
        else
            return cashModelSaveslist.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView imageViewmax, imageViewmin, showPdf, downloadPdf;
        LinearLayout layout, linearLayout_pdf;
        RecyclerView recycler;
        AdapteronlyImage adapter;
        TextView typetest, dateinside, noimag, day;
        ConstraintLayout constraintLayout;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout_show_pdf);
            imageViewmax = itemView.findViewById(R.id.plus_expandbal_list_result);
            layout = itemView.findViewById(R.id.body_expand_result_id);
            recycler = itemView.findViewById(R.id.recyclerview_image_inseid_result_id);
            typetest = itemView.findViewById(R.id.name_of_singl_rsult_id);
            noimag = itemView.findViewById(R.id.textnnoimage);
            showPdf = itemView.findViewById(R.id.showpdf);
            linearLayout_pdf = itemView.findViewById(R.id.linear_download_pdf);
            downloadPdf = itemView.findViewById(R.id.downloadpdf);
            dateinside = itemView.findViewById(R.id.history_of_rsult_id_inside_result);
            day = itemView.findViewById(R.id.day_item_inside_result);


            imageViewmax.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Resultcopy resultcopy = re.get(getAdapterPosition());
                    resultcopy.setExpand(!resultcopy.isExpand());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    private void fragmentJump(String link, int net) {


        Fragment mFragment = new PDF_Fragment(link,net);

        Bundle mBundle = new Bundle();

        mFragment.setArguments(mBundle);
        switchContent(R.id.fram_result, mFragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (mContext == null)
            return;
        if (mContext instanceof ResultActivty) {
            ResultActivty mainActivity = (ResultActivty) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag, true);
        }

    }
}