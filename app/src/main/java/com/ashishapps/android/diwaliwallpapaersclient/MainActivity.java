package com.ashishapps.android.diwaliwallpapaersclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView1,imageView2,imageView3;
        Button button;
        button=findViewById(R.id.uploadbutton);
        imageView1=findViewById(R.id.image1);
        imageView2=findViewById(R.id.image2);
        imageView3=findViewById(R.id.image3);
      // Toolbar toolbar= getActionBar();
     getSupportActionBar().setTitle("Wallpapers");
    // getSupportActionBar().setBackgroundDrawable( new ColorDrawable(Color.parseColor("#1A000000")));
      //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("Foldername","uploads");
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("Foldername","uploadsDashara");
                startActivity(intent);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("Foldername","uploadsKarwa");
                startActivity(intent);
            }
        });



//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(MainActivity.this,UploadActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}
