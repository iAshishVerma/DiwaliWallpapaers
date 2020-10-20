package com.ashishapps.android.diwaliwallpapaers;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UploadActivity extends AppCompatActivity {
private static final int PICK_IMAGE_REQUEST=1;
private Button chooseImageButton,uploadButton;
private TextView showUploadtextview;
private ImageView previewImageview;
private ProgressBar progressBar;
private Uri imageuri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        chooseImageButton=findViewById(R.id.choosefilebutton);
        uploadButton=findViewById(R.id.uploadbutton);
        showUploadtextview=findViewById(R.id.showUploadTXTview);
        previewImageview=findViewById(R.id.previewIMageVIew);
        progressBar=findViewById(R.id.progressbar);


        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 openfilechooser();

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
         showUploadtextview.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

             }
         });

    }

     private void openfilechooser(){
         Intent intent= new Intent();
         intent.setType("image/*");
         intent.setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && requestCode ==RESULT_OK && data!=null && data.getData()!=null){
            imageuri=data.getData();
            Picasso.get().load(imageuri).into(previewImageview);

        }
    }
}
