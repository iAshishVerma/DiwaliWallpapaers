package com.ashishapps.android.diwaliwallpapaersclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Main3Activity extends AppCompatActivity {
public FirebaseStorage storage= FirebaseStorage.getInstance();
public StorageReference storageReference=storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }
}
