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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.google.firebase.BuildConfig;
import com.medical.citylap.R;
import com.medical.citylap.activity.ResultActivty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Environment.DIRECTORY_DOCUMENTS;
import static android.os.Environment.DIRECTORY_PICTURES;

public class PDF_Fragment extends Fragment {
    ProgressDialog pDialog;
 String link;
int stat;
PDFView pdfView;
    private static final int PERMISSION_REQUEST_CODE = 200;

    public PDF_Fragment(String link,int stat) {
        // Required empty public constructor
        this.link=link;
        this.stat=stat;
    }
    private static final String[] PERMISSIONS = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(stat==1) {
            pDialog = new ProgressDialog(getContext());
            pDialog.setTitle("PDF");
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_p_d_f_, container, false);
        hasPermissions(getContext(),PERMISSIONS);
        WebView webView = view.findViewById(R.id.webview);
        ImageView imageView_=view.findViewById(R.id.exist);
        pdfView=view.findViewById(R.id.pdfview);
        // Include dialog.xml file
        pDialog = new ProgressDialog(getContext());
        request(view);
        imageView_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ResultActivty.class));
            }
        });
        if (checkPermission()) {
        } else {
            requestPermission();
        }
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
            Log.d("TAG", "onCreateView: "+link);
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

            webView.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
            File file= new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)+"/"+link);
            pdfView.fromFile(new File(file.getAbsolutePath())).enableSwipe(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .scrollHandle(null)
                    .enableDoubletap(true)
                    .load();


        }
        pDialog.dismiss();
        return  view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( data != null && data.getData() != null && requestCode == 0 ){
           Uri uri  = data.getData();
           pdfView.fromUri(uri)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .scrollHandle(null)
                    .enableDoubletap(true)
                    .load();

        }
        pDialog.dismiss();
    }
    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    public void request(View view) {

        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, 112);

    }
    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(getContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permission Denined.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}