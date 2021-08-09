package com.medical.citylap.helperfunction;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Toast;

import java.io.File;

public class SavingPdf {


    Context context;
    String titel;

 int id;
    public SavingPdf( Context context, String titel,int id) {
        this.context = context;
        this.titel = titel;
        this.id=id;
    }

    public String DownloadFile(String uri,int type)
    {

        SharedPreferences preferences3 = context.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String retrivedphonenumber = preferences3.getString("phonenumberuser", null);
        if(retrivedphonenumber==null)
        {
            retrivedphonenumber="";
        }
        uri = "http://" + uri;
        ContentResolver resolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        if(type==0)
        {
            //Image
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/image"+"/"+retrivedphonenumber+"/"+id);
            String path = String.valueOf(resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues));
            File folder = new File(path);
            boolean isCreada = folder.exists();
            if(!isCreada) {
                folder.mkdirs();
            }
            @SuppressLint({"NewApi", "LocalSuppress"}) DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
            request.setTitle(titel);
            request.setDescription("تحميل الملف برجاء الانتظار .......");
            String cookie = CookieManager.getInstance().getCookie(uri);
            request.addRequestHeader("cookie", cookie);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES ,"/image"+"/"+retrivedphonenumber+"/"+id+""+"/"+titel+".png" );
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);

           return "/image"+"/"+retrivedphonenumber+"/"+id+""+"/"+titel+".png";
        }
        else
        {
            //Documnet
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/file"+"/"+retrivedphonenumber+"/"+id);
            String path = String.valueOf(resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues));
            File folder = new File(path);
            boolean isCreada = folder.exists();
            if(!isCreada) {
                folder.mkdirs();
            }
            @SuppressLint({"NewApi", "LocalSuppress"}) DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
            request.setTitle(titel);
            request.setDescription("تحميل الملف برجاء الانتظار .......");
            String cookie = CookieManager.getInstance().getCookie(uri);
            request.addRequestHeader("cookie", cookie);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES  ,"/file"+"/"+retrivedphonenumber+"/"+id+""+"/"+titel+".pdf"  );
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(context, "يتم بداء التحمبل الان", Toast.LENGTH_SHORT).show();
            return  "/file"+"/"+retrivedphonenumber+"/"+id+""+"/"+titel+".pdf" ;
        }

    }
}
