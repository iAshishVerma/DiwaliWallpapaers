package com.ashishapps.android.diwaliwallpapaersclient;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {
private static final int PICK_IMAGE_REQUEST=  1;
private Button chooseImageButton,uploadButton,chooseimage2,chooseimage3;
private TextView showUploadtextview;
private ImageView previewImageview;
private ProgressBar progressBar;
private Uri imageuri;
private StorageReference storageReference;
private DatabaseReference databaseReference;
private StorageTask uploadTask;

       @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        chooseImageButton=findViewById(R.id.choosefilebutton);
        chooseimage2=findViewById(R.id.choosefilebutton2);
        chooseimage3=findViewById(R.id.choosefilebutton3);

        uploadButton=findViewById(R.id.uploadbutton);
        showUploadtextview=findViewById(R.id.showUploadTXTview);
        previewImageview=findViewById(R.id.previewIMageVIew);
        progressBar=findViewById(R.id.progressbar);

        storageReference= FirebaseStorage.getInstance().getReference("uploads");//files will be uploaded to uploads folder
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");




        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 openfilechooser();

            }
        });
        chooseimage2.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   //mkaing new folder uplon clicking this button
                   storageReference= FirebaseStorage.getInstance().getReference("uploadsDashara");//files will be uploaded to uploads folder
                   databaseReference= FirebaseDatabase.getInstance().getReference("uploadsDashara");
                   openfilechooser();

               }
           });

           chooseimage3.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   //mkaing new folder uplon clicking this button
                   storageReference= FirebaseStorage.getInstance().getReference("uploadsKarwa");//files will be uploaded to uploads folder
                   databaseReference= FirebaseDatabase.getInstance().getReference("uploadsKarwa");
                   openfilechooser();

               }
           });





        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(uploadTask!=null && uploadTask.isInProgress()){
               Toast.makeText(UploadActivity.this,"Upload is in Progress",Toast.LENGTH_SHORT).show();

               }else {
                   uploadFile();

               }



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
         startActivityForResult(intent,PICK_IMAGE_REQUEST);//intent,PICK_IMAGE_REQUEST

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);//requestCode ==1 agar isko resultcode==1 ki jagah rako fir bhi kam kar raha
        if(requestCode==PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data!=null && data.getData()!=null){

            imageuri=data.getData();
            Log.d("Checking Log","Data inside uri"+imageuri);
            //Picasso.get().load(imageuri).into(previewImageview);
            previewImageview.setImageURI(imageuri);
//            String realpath=getRealPathFromDocumentUri(UploadActivity.this,imageuri);
//            File imgfile = new File(realpath);
//            Bitmap bitmap= BitmapFactory.decodeFile(imgfile.getAbsolutePath());
//            previewImageview.setImageBitmap(bitmap);

        }
    }
    private String getFileExtention(Uri uri){

        ContentResolver cr= getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private  void uploadFile(){
if(imageuri!=null){

    StorageReference fileReference= storageReference.child(System.currentTimeMillis()+"."+ getFileExtention(imageuri));
    uploadTask=fileReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(0);
                }
            },500);

            Toast.makeText(UploadActivity.this,"Upload Successful",Toast.LENGTH_SHORT).show();
            CardActivity cardActivity= new CardActivity(taskSnapshot.getDownloadUrl().toString());
            String uploadid=databaseReference.push().getKey();
            databaseReference.child(uploadid).setValue(cardActivity);

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

            Toast.makeText(UploadActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
            double  progress=(100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
            progressBar.setProgress((int)progress);


        }
    });


}
else{

    Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
}


    }
/////////////////////////



}
