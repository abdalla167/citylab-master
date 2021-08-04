package com.medical.citylap.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.medical.citylap.R;
import com.medical.citylap.activity.ResultActivty;
import com.medical.citylap.fragemnt.PDF_Fragment;
import com.medical.citylap.helperfunction.SavingPdf;
import com.medical.citylap.modles.Result;
import com.medical.citylap.modles.ResultApi;
import com.medical.citylap.modles.Resultcopy;
import com.medical.citylap.modles.Resultss;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AdapterResult extends RecyclerView.Adapter<AdapterResult.ViewHolder2> {
    ResultApi resultApi;
    ArrayList<Result> listresult = new ArrayList<>();
    ArrayList<Resultss> listresultapi = new ArrayList<>();
    private Context mContext;
    SavingPdf savingPdf;
    Uri uri;
    ArrayList<Resultcopy> re = new ArrayList<>();

    public void setlist2(ResultApi resultApi, ArrayList<Resultcopy> re) {
        this.resultApi = resultApi;
        this.re = re;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterResult.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_result_befor, parent, false);
        return new AdapterResult.ViewHolder2(view);
    }

    public AdapterResult(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterResult.ViewHolder2 holder, int position) {

        holder.typetest.setText(resultApi.getData().get(position).getNotes() + "");
        boolean isExpand = re.get(position).isExpand();
        holder.layout.setVisibility(isExpand ? View.VISIBLE : View.GONE);
        if (isExpand = re.get(position).isExpand() == false) {
            holder.imageViewmax.setImageResource(R.drawable.ic_baseline_add_24);
        } else

            {
            holder.imageViewmax.setImageResource(R.drawable.ic_baseline_minimize_24);
            if (resultApi.getData().get(position).getMediaType() == 1) {
                //file
                holder.recycler.setVisibility(View.GONE);
                holder.noimag.setVisibility(View.VISIBLE);
                holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentJump(resultApi.getData().get(position).getFiles().get(0));

                    }
                });
                holder.showPdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragmentJump(resultApi.getData().get(position).getFiles().get(0));

                    }
                });
                holder.downloadPdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        savingPdf=new SavingPdf(mContext,resultApi.getData().get(position).getNotes() + "",
                                Integer.parseInt(String.valueOf(resultApi.getData().get(position).getResultId())));
                        savingPdf.DownloadFile(resultApi.getData().get(position).getFiles().get(0).trim(),1);

                    }
                });


            }
            if(resultApi.getData().get(position).getMediaType() == 0)
                {
                holder.recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                holder.adapter = new AdapteronlyImage(mContext);
                holder.adapter.setlist(resultApi.getData().get(position).getFiles());
                holder.recycler.setAdapter(holder.adapter);
                    holder.downloadPdf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            savingPdf=new SavingPdf(mContext,resultApi.getData().get(position).getNotes() + "",
                                    Integer.parseInt(String.valueOf(resultApi.getData().get(position).getResultId())));
                            holder.downloadPdf.setClickable(false);
                            for(int i=0;i<resultApi.getData().get(position).getFiles().size();i++) {
                                savingPdf.DownloadFile(resultApi.getData().get(position).getFiles().get(i).trim(), 0);
                            }
                            holder.downloadPdf.setClickable(true);
                        }
                    });
                }
        }




    }

    @Override
    public int getItemCount() {
        if (resultApi.getData().size() > 0)
            return resultApi.getData().size();
        else
            return 0;
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView imageViewmax, imageViewmin,showPdf,downloadPdf;
        LinearLayout layout, linearLayout_pdf;
        RecyclerView recycler;
        AdapteronlyImage adapter;
        TextView typetest, date, noimag;
        ConstraintLayout constraintLayout;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.constraintLayout_show_pdf);
            imageViewmax = itemView.findViewById(R.id.plus_expandbal_list_result);
            layout = itemView.findViewById(R.id.body_expand_result_id);
            recycler = itemView.findViewById(R.id.recyclerview_image_inseid_result_id);
            typetest = itemView.findViewById(R.id.name_of_singl_rsult_id);
            noimag = itemView.findViewById(R.id.textnnoimage);
            showPdf=itemView.findViewById(R.id.showpdf);
            linearLayout_pdf = itemView.findViewById(R.id.linear_download_pdf);
            downloadPdf=itemView.findViewById(R.id.downloadpdf);
            linearLayout_pdf.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onClick(View v) {
                    if (resultApi.getData().get(getAdapterPosition()).getMediaType() == 1) {
//
//                        final Dialog dialog = new Dialog(mContext);
//                        dialog.setContentView(R.layout.custom_web_view);
//                        WebView webView = dialog.findViewById(R.id.webview);
//                        ImageView imageView_=dialog.findViewById(R.id.exist);
//                              // Include dialog.xml file
//                        final ProgressDialog pDialog = new ProgressDialog(mContext);
//                        pDialog.setTitle("PDF");
//                        pDialog.setMessage("Loading...");
//                        pDialog.setIndeterminate(false);
//                        pDialog.setCancelable(false);
//                        dialog.show();
//                        pDialog.show();
//
//                        webView.getSettings().setJavaScriptEnabled(true);
//                        webView.setWebViewClient(new WebViewClient() {
//                            @Override
//                            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                                super.onPageStarted(view, url, favicon);
//                                pDialog.show();
//                            }
//
//                            @Override
//                            public void onPageFinished(WebView view, String url) {
//                                super.onPageFinished(view, url);
//                                pDialog.dismiss();
//                            }
//                        });
//                        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + "http://" + resultApi.getData().get(getAdapterPosition()).getFiles().get(0));
//                        pDialog.dismiss();
//imageView_.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        dialog.dismiss();
//    }
//});

                    }
//                    new DownloadPDF().
//                            execute("http://"+resultApi.getData().get(getAdapterPosition()).getFiles().get(0).toString());
//
//
//                    final Dialog dialog = new Dialog(mContext);
//                    dialog.setContentView(R.layout.custompdfview);       // Include dialog.xml file
//                    dialog.show();      // Include dialog.xml file
//                    PDFView PDF=dialog.findViewById(R.id.pdfView);
//

                }
            });

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

    private void fragmentJump(String link) {
        Fragment mFragment = new PDF_Fragment(link);
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
            mainActivity.switchContent(id, frag,true);
        }

    }
}