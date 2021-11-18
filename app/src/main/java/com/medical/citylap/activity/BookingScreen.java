package com.medical.citylap.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medical.citylap.R;
import com.medical.citylap.RetrofitClint;
import com.medical.citylap.helperfunction.FileData;
import com.medical.citylap.modles.LocationModle;
import com.medical.citylap.modles.Reservation;
import com.medical.citylap.modles.SimpleResponse;
import com.medical.citylap.viewModel.DataLocation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class BookingScreen extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener , AdapterView.OnItemSelectedListener{
    Button buttongetimage, subment;
    EditText name, phone;
    Spinner far3;
    String location="";
    String sOffDate = "";
  List< String> far3_item=new ArrayList<>();
    LocationModle locationModlel=new LocationModle();
    DatePickerDialog.OnDateSetListener Date_booking;
    TextView textView_date;
    EditText address, number_bulding, number_floer, number_part;
    ImageView imageView;
    ProgressBar progressBar;
    int type = 0;
    DataLocation dataLocation;
    TextView textView;
    RadioGroup radioGroup;
    RadioButton radioButtonhome;
    RadioButton radioButtonlab;
    ImageView imageViewback;
    LinearLayout linearLayout;
    Reservation reservation;
    String token = null;
    List<FileData> files = new ArrayList<>();
    FileData fileData;
    ByteArrayOutputStream bytes;
    InputStream inputStream;
    Context mContext;
    public static final int REQUEST_CAMERA_CODE = 101;
    public static final int REQUEST_GALLERY_CODE = 102;
    private static final int PDF_REQUEST_CODE = 103;
    //storage data
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CAMERA = 122;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY = 123;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_PDF = 124;
    String imageBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_screen);
        inti();
        getdata();
        SharedPreferences preferences3 = BookingScreen.this.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String retrivedname_user = preferences3.getString("nameuserprofile", null);
        String retrivedphonenumber = preferences3.getString("phonenumberuser", null);
        name.setText(retrivedname_user);
        phone.setText(retrivedphonenumber);
        initDateDialog();
        Date currentTime = Calendar.getInstance().getTime();




        subment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location.equals("") || textView_date.getText().toString().equals("")) {
                    Toast.makeText(BookingScreen.this, "اكمل البيانات ", Toast.LENGTH_SHORT).show();
                } else {
                    if (type == 1) {
                        if (address.getText().toString().equals("")) {
                            Toast.makeText(BookingScreen.this, "اكمل البيانات العنوان", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ProgressDialog pDialog = new ProgressDialog(BookingScreen.this);
                            pDialog.setTitle("Booking");
                            pDialog.setMessage("Reservation...");
                            pDialog.setIndeterminate(false);
                            pDialog.setCancelable(false);
                            pDialog.show();
                            //progressBar.setVisibility(View.VISIBLE);
                            reservation.setName(retrivedname_user);
                            reservation.setPhoneNumber(retrivedphonenumber);
                            reservation.setAge(location.toString());
                            reservation.setAddress(address.getText().toString());
                            reservation.setBuildingNo(number_bulding.getText().toString());
                            reservation.setDay(0);
                            reservation.setFile(imageBase64);
                            reservation.setFloorNo(number_floer.getText().toString());
                            reservation.setAppartementNo(number_part.getText().toString());
                            reservation.setType(type);
                            SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("hh:mm:ss aa");
                            dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
                            reservation.setReservationDate((textView_date.getText().toString()+" "+dateTimeInGMT.format(new Date())).toString());


                            RetrofitClint.getInstance().upload_book(reservation, SplashScreen.token_user).enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(BookingScreen.this, "تم الحجز بنجاح", Toast.LENGTH_SHORT).show();
                                        pDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                    Log.d(TAG, "onResponse:+error booking" + t.getMessage());
                                  //  progressBar.setVisibility(View.GONE);
                                    pDialog.dismiss();
                                }
                            });

                        }
                    }
                    else {

                        ProgressDialog pDialog = new ProgressDialog(BookingScreen.this);
                        pDialog.setTitle("Booking");
                        pDialog.setMessage("Reservation...");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show();
                       // progressBar.setVisibility(View.VISIBLE);
                        reservation.setName(retrivedname_user);
                        reservation.setPhoneNumber(retrivedphonenumber);
                        reservation.setAge(location.toString());
                        reservation.setAddress(address.getText().toString());
                        reservation.setBuildingNo(number_bulding.getText().toString());
                        reservation.setDay(0);
                        reservation.setFile(imageBase64);
                        reservation.setFloorNo(number_floer.getText().toString());
                        reservation.setAppartementNo(number_part.getText().toString());
                        reservation.setType(type);
                        SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("hh:mm:ss aa");
                        dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
                        reservation.setReservationDate((textView_date.getText().toString()+" "+dateTimeInGMT.format(new Date())).toString());

                        RetrofitClint.getInstance().upload_book(reservation, SplashScreen.token_user).enqueue(new Callback<SimpleResponse>() {
                            @Override
                            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                if (response.isSuccessful()) {
                                    pDialog.dismiss();
                                    Toast.makeText(BookingScreen.this, "تم الحجز بنجاح", Toast.LENGTH_SHORT).show();
                                  //  progressBar.setVisibility(View.GONE);

                                }
                            }

                            @Override
                            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                Log.d(TAG, "onResponse:+error booking" + t.getMessage());
                               // progressBar.setVisibility(View.GONE);
                                pDialog.dismiss();
                            }
                        });
                    }
                    type=0;
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.inlab_radio_id) {
                    linearLayout.setVisibility(View.GONE);
                    type = 0;
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    type = 1;
                }
            }
        });
        buttongetimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popUpMenu(v);
            }
        });

        DateFormat dffrom = new SimpleDateFormat("M/dd/yyyy");
        DateFormat dfto = new SimpleDateFormat("yyyy-MM-dd");
        Date today = null;
        try {
            today = dffrom.parse("7/1/2011");
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        String s = dfto.format(today);
imageViewback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
        startActivity(new Intent(BookingScreen.this,Home.class));
    }
});
        textView_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                Locale locale = getResources().getConfiguration().locale.ENGLISH;
                Locale.setDefault(locale);

                DatePickerDialog dialog = new DatePickerDialog(
                        BookingScreen.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        Date_booking,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


    }

    public void inti() {
        DatePickerDialog.OnDateSetListener Date_booking;
        buttongetimage = findViewById(R.id.upload_image_booking_id);
        textView = findViewById(R.id.text_imag_reservation);
        imageView = findViewById(R.id.imag_imag_reservation);
        radioGroup = findViewById(R.id.radiogroup);
        radioButtonlab = findViewById(R.id.inlab_radio_id);
        radioButtonhome = findViewById(R.id.inhome_test_id);
        linearLayout = findViewById(R.id.layoutaddress);
        imageViewback = findViewById(R.id.backfrom_reservation_to_home);
        subment = findViewById(R.id.submet_id_booking);
        name = findViewById(R.id.nameclint_booking_id);
        far3 = findViewById(R.id.agecllint_booking_id);
        phone = findViewById(R.id.phonnumbercllint_booking_id);
        reservation = new Reservation();
        textView_date = findViewById(R.id.historyclint_booking_id);
        address = findViewById(R.id.addressclint_id_booking);
        number_bulding = findViewById(R.id.numberbuliding_id);
        number_floer = findViewById(R.id.number_flor_id);
        number_part = findViewById(R.id.number_door_id);
        progressBar = findViewById(R.id.prograsbarbook);
        mContext=getApplicationContext();



    }

    public void getdata()
    {

        ArrayList<String>locationModles=new ArrayList<>();

        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("location");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i=0;
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {

                        locationModlel = npsnapshot.getValue(LocationModle.class);
                        locationModles.add(locationModlel.getNamelabe());
                        far3_item.add(locationModlel.getNamelabe().toString());
                        i++;
                    }
                    far3.setOnItemSelectedListener(BookingScreen.this);

                    ArrayAdapter aa = new ArrayAdapter(BookingScreen.this,android.R.layout.simple_spinner_item,locationModles);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    far3.setAdapter(aa);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });


    }
    private void popUpMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(BookingScreen.this);
        popup.inflate(R.menu.file_type_items);
        popup.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera_item:
                cameraIntent();

                return true;
            case R.id.gallery_item:
                galleryIntent();
                return true;
        }


        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                    return;
                }
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                    return;
                }

            default:
                Toast.makeText(this, "يجب السماح بالوصول للملفات لتمام العملية", Toast.LENGTH_SHORT).show();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void cameraIntent() {
        if (checkStoragePermission(MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void galleryIntent() {
        if (checkStoragePermission(MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (data.getData() != null) {
                try {
                    inputStream = this.getContentResolver()
                            .openInputStream(data.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (requestCode == REQUEST_GALLERY_CODE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA_CODE)
                onCaptureImageResult(data);


        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                if (bytes == null)
                    bytes = new ByteArrayOutputStream();
                bytes.reset();
                bm.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                String sImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT).trim();

                //add image to adapter
                //    inputStream = requireContext().getContentResolver().openInputStream(data.getData());
                files.add(new FileData(bm, sImage));

                fileData = new FileData(bm, sImage);
                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(fileData.getBitmap());

              imageBase64=sImage;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        if (data != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            if (bytes == null)
                bytes = new ByteArrayOutputStream();
            bytes.reset();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            String sImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT).trim();

            files.add(new FileData(thumbnail, sImage));

            imageView.setImageBitmap(files.get(0).getBitmap());


            fileData = new FileData(thumbnail, sImage);
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(fileData.getBitmap());
            imageBase64=sImage;

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Boolean checkStoragePermission(final int REQUEST_CODE) {
        if (isStoragePermissionAllowed())
            return true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestStoragePermission(REQUEST_CODE);
        } else {
            return true;
        }

        return true;
    }

    private Boolean isStoragePermissionAllowed() {
        return ContextCompat.
                checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission(final int REQUEST_CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //explain why do u want
            showUIMessage("امكانية الوصول الي الملفات لتنفيذ العملية", REQUEST_CODE);
        } else {
            //request permission
            askStoragePermission(REQUEST_CODE);
        }
    }

    private void showUIMessage(String message, final int REQUEST_CODE) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //request permission again
                askStoragePermission(REQUEST_CODE);
            }
        });

        builder.setCancelable(false);
        builder.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askStoragePermission(final int REQUEST_CODE) {
        requestPermissions(
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE);
    }

    private void initDateDialog() {
        Date_booking = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String  day;
                String man;
                if(dayOfMonth<10)
                {
                  day=0+""+dayOfMonth;
                }
                else
                {
                    day=dayOfMonth+"";
                }
                if(month<10)
                {
                      man=0+""+month;
                }
                else
                {
                    man=month+"";
                }
                sOffDate = day + "/" + man + "/" + year;
                textView_date.setText(sOffDate);
            }
        };


    }

    private SimpleDateFormat getDateFromString(String date) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        return new SimpleDateFormat(myFormat, Locale.US);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        location=far3_item.get(position);
        //Toast.makeText(mContext, ""+location, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}