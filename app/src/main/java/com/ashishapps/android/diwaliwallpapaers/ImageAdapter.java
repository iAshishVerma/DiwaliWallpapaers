package com.ashishapps.android.diwaliwallpapaers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

//import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

//image adapter only recycyels the views ie recycels the view and do not extract the data from firebase
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
private StorageReference storageReference1=FirebaseStorage.getInstance().getReference("Folder");;;

    private Context context;
    private List<CardActivity> cardActivityList;
    private OnItemClickListener listener;


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
            //next line is important commented for test purpose
     Picasso.get().load(currentCard.getImageurl()).fit().placeholder(R.drawable.ashishapps).centerCrop().into(imageViewHolder.imageView);
//Picasso.get().load(String.valueOf(storageReference1)).into(imageViewHolder.imageView);
     // Glide.with(this).load(storageReference1).into(imageViewHolder.imageView);

        //flex layout requirement


    }

    @Override
    public int getItemCount() {
        return cardActivityList.size();
    }

    public void updatedata(Context context2, List<CardActivity> list){
        context=context2;
        cardActivityList=list;

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener,
            View.OnCreateContextMenuListener , MenuItem.OnMenuItemClickListener{
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageitem);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }
        @Override
        public void onClick(View v){
            if(listener!=null){

                int postion = getAdapterPosition();
                if(postion!=RecyclerView.NO_POSITION){
                 listener.onItemClick(postion);

                }
            }


        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Title");
            MenuItem doWhatever =contextMenu.add(Menu.NONE,1,1,"Do whatever");
            MenuItem delete=contextMenu.add(Menu.NONE,2,2,"Delete");
            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(listener!=null){

                int postion = getAdapterPosition();
                if(postion!=RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:listener.onWhateverClick(postion);
                        return  true;
                        case 2:listener.onDeleteClick(postion);
                            return  true;


                    }

                }
            }
            return false;
        }

    }

    public interface OnItemClickListener{

        void onItemClick(int positon);
        void onWhateverClick(int position);
        void onDeleteClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener2){
            listener=listener2;
        ///
    }








}
