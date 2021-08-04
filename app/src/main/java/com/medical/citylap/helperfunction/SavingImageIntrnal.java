package com.medical.citylap.helperfunction;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_WRITE;

public class SavingImageIntrnal extends AsyncTask<String ,Integer,String> {
ProgressDialog progressDialog;
Context context;
    @Override
    public void onPreExecute() {
        super .onPreExecute();
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... url) {
        File mydir = new File(Environment.getExternalStorageDirectory() + "/11zon");
        if (!mydir.exists()) {
            mydir.mkdirs();
        }

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(url[0]);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        SimpleDateFormat dateFormat = new SimpleDateFormat("mmddyyyyhhmmss");
        String date = dateFormat.format(new Date());

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("Downloading")
                .setDestinationInExternalPublicDir("/11zon", date + ".jpg");

        manager.enqueue(request);
        return mydir.getAbsolutePath() + File.separator + date + ".jpg";
    }

    @Override
    public void onPostExecute(String s) {
        super .onPostExecute(s);
        progressDialog.dismiss();
        Toast.makeText(context.getApplicationContext(), "Image Saved", Toast.LENGTH_SHORT).show();
    }


    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

       }

