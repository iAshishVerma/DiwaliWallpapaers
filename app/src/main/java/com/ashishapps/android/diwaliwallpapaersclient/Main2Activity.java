package com.ashishapps.android.diwaliwallpapaersclient;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private DatabaseReference databaseReference; //from this database we area gonna get items and feed into the list
    private List<CardActivity> cardActivityList;
    private FirebaseStorage storage2; // will be used to get link of image which is clicked
    private StorageReference storageReference;
    public String FolderName;
    int position=0;

    //uploader activity is for uploading images to differernt folders and making the folder if they already do not exist
    //however the main2activiy and main3activity and main4activity are fetcher activity which are used to
    //fetch images from the folder mentioned in the uploader activity

    private ValueEventListener dblistener;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /////Extracting parameter name
        Bundle b=getIntent().getExtras();
        FolderName=b.getString("Foldername");
        ///////
 ////////////Removing text from activity toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
///////////////////////////
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setCollapsible(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);///used for back button on tool bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);



////////////////////
        recyclerView=findViewById(R.id.recyclerview);
        ////////////niche ki do line tutorial ke according hai
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /////////////////



        ////////////////////////
    // using staggered layout
StaggeredGridLayoutManager layoutManager= new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//
        //layoutManager.setReverseLayout(true);//for this to work the image inflatation starts from end since i have 2 colums
        //so upload images in the number which is the product of 2

recyclerView.setLayoutManager(layoutManager);
recyclerView.setHasFixedSize(true);


        /////////////////
        cardActivityList= new ArrayList<>();
        adapter= new ImageAdapter(Main2Activity.this,cardActivityList);
        ////trying to revers the list
      //  Collections.reverse(cardActivityList);
        recyclerView.setAdapter(adapter);

////////////
        adapter.setOnItemClickListener(Main2Activity.this);

        ////////////////
        storage2=FirebaseStorage.getInstance();
        if(FolderName!=null){

            databaseReference= FirebaseDatabase.getInstance().getReference(FolderName);//folder is the folder made in bucket//Folder

            storageReference= FirebaseStorage.getInstance().getReference(FolderName);

        }else{
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT);
        }


//        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");//folder is the folder made in bucket//Folder
//
//        storageReference= FirebaseStorage.getInstance().getReference("uploads");
 ///////////////////////////

//////////////
//                adapter= new ImageAdapter(Main2Activity.this,cardActivityList);
//                recyclerView.setAdapter(adapter);
//
//////////////
//                  adapter.setOnItemClickListener(Main2Activity.this);
        adapter.notifyDataSetChanged();

/////////
        dblistener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ////
                cardActivityList.clear();



            /////getting data out of node]
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    CardActivity item= postSnapshot.getValue(CardActivity.class);

                    item.setDbkey(postSnapshot.getKey()); //this will extract the key of image in database


                    cardActivityList.add(position,item);//positon is 0 so the newly added image is added to the 0th position line 1of 3
                    Log.d("MeraLog", "Value is: " + item.toString());



                }
                ///
//                adapter= new ImageAdapter(Main2Activity.this,cardActivityList);
//                recyclerView.setAdapter(adapter);
                   adapter.notifyItemInserted(position);//positon is 0 so the newly added image is added to the 0th position line2of 3

                adapter.notifyDataSetChanged();//positon is 0 so the newly added image is added to the 0th positionline 3of 3


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Main2Activity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

       MobileAds.initialize(this);




        AdView adView=findViewById(R.id.banneradRec);
        AdRequest adRequest= new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }
//////////////////////This method puts back button on toolbar
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
        finish();
    }
    return super.onOptionsItemSelected(item);
}

//////////////////////
    @Override
    public void onItemClick(int positon) {
       // Toast.makeText(this,"Normal Click at position"+positon,Toast.LENGTH_SHORT).show();
        CardActivity selectedItem =cardActivityList.get(positon);
        String url=selectedItem.getImageurl();
        Intent intent= new Intent(Main2Activity.this,ImageViewer.class);
        intent.putExtra("URL",url);
        startActivity(intent);

    }

    @Override
    public void onWhateverClick(int position) {
        Toast.makeText(this,"Whatever Click at position"+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        CardActivity selectedItem =cardActivityList.get(position);
        final String seletedkey=selectedItem.getDbkey();
        StorageReference imageRef=storage2.getReferenceFromUrl(selectedItem.getImageurl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(seletedkey).removeValue();
                Toast.makeText(Main2Activity.this,"Item deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onDestroy(){
          super.onDestroy();

          databaseReference.removeEventListener(dblistener);
    }

}
