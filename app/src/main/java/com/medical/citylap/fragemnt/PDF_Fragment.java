package com.medical.citylap.fragemnt;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.medical.citylap.R;

public class PDF_Fragment extends Fragment {

 String link;

    public PDF_Fragment(String link) {
        // Required empty public constructor
        this.link=link;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_p_d_f_, container, false);


        ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setTitle("PDF");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        WebView webView = view.findViewById(R.id.webview);
        ImageView imageView_=view.findViewById(R.id.exist);
        // Include dialog.xml file


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + "http://" + link);
        pDialog.dismiss();
        imageView_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {

                    getActivity(). getSupportFragmentManager().popBackStackImmediate();
                }
            }
        });
        return  view;
    }


}