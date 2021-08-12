package com.medical.citylap.fragemnt;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.google.firebase.BuildConfig;
import com.medical.citylap.R;
import com.medical.citylap.activity.ResultActivty;

import java.io.File;
import java.nio.file.Files;

import static android.os.Environment.DIRECTORY_DOCUMENTS;
import static android.os.Environment.DIRECTORY_PICTURES;

public class PDF_Fragment extends Fragment {

 String link;
int stat;
PDFView pdfView;
    public PDF_Fragment(String link,int stat) {
        // Required empty public constructor
        this.link=link;
        this.stat=stat;
    }
    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_p_d_f_, container, false);
        hasPermissions(getContext(),PERMISSIONS);

        ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setTitle("PDF");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        WebView webView = view.findViewById(R.id.webview);
        ImageView imageView_=view.findViewById(R.id.exist);
         pdfView=view.findViewById(R.id.pdfview);
        // Include dialog.xml file
        request(view);
imageView_.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), ResultActivty.class));
    }
});
        if (stat==1) {
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

                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                }
            });
        }
        else
        {
            pDialog.show();




            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ DIRECTORY_PICTURES+ link);
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file),"application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent = Intent.createChooser(target, "Open File");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
            }



//            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//            pdfIntent.setDataAndType(path, "application/pdf");
//            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            try{
//                startActivity(pdfIntent);
//            }catch(ActivityNotFoundException e){
//                Toast.makeText(getContext(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
//            }

//            Log.d("TAG", "onCreateView:pdf "+Environment.DIRECTORY_PICTURES+ link);
//            webView.setVisibility(View.GONE);
//            pdfView.setVisibility(View.VISIBLE);
//
//            File file=new File(Environment.DIRECTORY_PICTURES, link);
//            pdfView.fromFile(file).defaultPage(1).onLoad(new OnLoadCompleteListener() {
//                @Override
//                public void loadComplete(int nbPages) {
//                    pDialog.dismiss();
//                    Toast.makeText(getContext(), String.valueOf(nbPages), Toast.LENGTH_LONG).show();
//                    Log.d("TAG", "loadComplete: ");
//                }
//            }).load();

pDialog.dismiss();
        }
        return  view;
    }

    public void request(View view) {

        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, 112);

    }



}