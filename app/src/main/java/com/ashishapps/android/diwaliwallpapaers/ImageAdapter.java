package com.ashishapps.android.diwaliwallpapaers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

//image adapter only recycyels the views ie recycels the view and do not extract the data from firebase
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
private StorageReference storageReference1=FirebaseStorage.getInstance().getReference("Folder");;;

    private Context context;
    private List<CardActivity> cardActivityList;


    public ImageAdapter(Context context1,List<CardActivity> cardActivities){
        context=context1;
        cardActivityList=cardActivities;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_card,viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
            CardActivity currentCard=cardActivityList.get(i);
     Picasso.get().load(currentCard.getImageurl()).fit().placeholder(R.mipmap.ic_launcher).centerCrop().into(imageViewHolder.imageView);
//Picasso.get().load(String.valueOf(storageReference1)).into(imageViewHolder.imageView);
     // Glide.with(this).load(storageReference1).into(imageViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return cardActivityList.size();
    }

    public void updatedata(Context context2, List<CardActivity> list){
        context=context2;
        cardActivityList=list;

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageitem);
        }
    }

}
