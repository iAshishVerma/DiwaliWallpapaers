package com.ashishapps.android.diwaliwallpapaers;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private DatabaseReference databaseReference; //from this database we area gonna get items and feed into the list
    private List<CardActivity> cardActivityList;
    private FirebaseStorage sdac;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardActivityList= new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("");//folder is the folder made in bucket//Folder

        storageReference= FirebaseStorage.getInstance().getReference("Folder");

                adapter= new ImageAdapter(Main2Activity.this,cardActivityList);
                recyclerView.setAdapter(adapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            /////getting data out of node]
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    CardActivity item= postSnapshot.getValue(CardActivity.class);
                    cardActivityList.add(item);
                    Log.d("MeraLog", "Value is: " + item.toString());
                }
                ///
//                adapter= new ImageAdapter(Main2Activity.this,cardActivityList);
//                recyclerView.setAdapter(adapter);


                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Main2Activity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }



}
// final ImageView imageView= findViewById(R.id.imagetest);
//////     ////////////////////////////////
//         storage=FirebaseStorage.getInstance();
//         nodeRef=storage.getReferenceFromUrl("gs://diwali-wallpapers-app-3e363.appspot.com").child("0QAui6IeZD74zCCQG.png");
//
//        try {
//            final File localFile = File.createTempFile("images", "jpg");
//            nodeRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                    imageView.setImageBitmap(bitmap);
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                }
//            });
//        } catch (IOException e ) {}
//////////////////////////////////////////////////////////////////////