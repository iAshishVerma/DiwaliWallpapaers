package com.ashishapps.android.diwaliwallpapaersclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageViewer extends AppCompatActivity {
String extracteurl;
ImageView imageView;
FloatingActionButton fab1,fab2;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        extracteurl=getIntent().getExtras().getString("URL");
        imageView=findViewById(R.id.imageviewer);

        Picasso.get().load(extracteurl).into(imageView);
        fab1=findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //asking for permission



                //////Download File

              SaveImage(ImageViewer.this,extracteurl);
            }
        });


        fab2=findViewById(R.id.fab2);

      fab2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              readpermission();
              BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
              Bitmap bitmap = bitmapDrawable .getBitmap();
              String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"Shared from WallpaperApp by AshishApps", null);
              Uri bitmapUri = Uri.parse(bitmapPath);
              Intent shareIntent=new Intent(Intent.ACTION_SEND);
              //sharing only to whatsapp
             // shareIntent.setPackage("com.whatsapp");
              shareIntent.setType("image/jpeg");
              shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
              shareIntent.putExtra(Intent.EXTRA_TEXT,"AShish Verma");
              //shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
              startActivity(Intent.createChooser(shareIntent,"Share Image"));


          }
      });

        MobileAds.initialize(this);


        AdView adView=findViewById(R.id.bannerad);
        AdRequest adRequest= new AdRequest.Builder().build();
        adView.loadAd(adRequest);



    }

    /////////////////////////////saving image
    private static void SaveImage(final Context context, final String MyUrl){
        final ProgressDialog progress = new ProgressDialog(context);
        class SaveThisImage extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setTitle("Processing");
                progress.setMessage("Please Wait...");
                progress.setCancelable(false);
                progress.show();
            }
            @Override
            protected Void doInBackground(Void... arg0) {
                try{

                    File sdCard = Environment.getExternalStorageDirectory();
                    @SuppressLint("DefaultLocale") String fileName = String.format("%d.jpg", System.currentTimeMillis());
                    File dir = new File(sdCard.getAbsolutePath() + "/savedImageName");
                    dir.mkdirs();
                    final File myImageFile = new File(dir, fileName); // Create image file
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(myImageFile);
                        Bitmap bitmap = Picasso.get().load(MyUrl).get();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(myImageFile));
                        context.sendBroadcast(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                if(progress.isShowing()){
                    progress.dismiss();
                }
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            }
        }
        SaveThisImage shareimg = new SaveThisImage();
        shareimg.execute();
    }

//////////////////////////////////////////////////////////////////////////////////


public  void readpermission(){
    if (Build.VERSION.SDK_INT >= 23)
    {
        if (checkPermission())
        {
            // Code for above or equal 23 API Oriented Device
            // Your Permission granted already .Do next code
        } else {
            requestPermission(); // Code for permission
        }
    }
    else
    {
        // Code for Below 23 API Oriented Devic// Do next code
    }

}
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(ImageViewer.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(ImageViewer.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(ImageViewer.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(ImageViewer.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

}
