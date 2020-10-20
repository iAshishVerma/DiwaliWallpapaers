package com.ashishapps.android.diwaliwallpapaers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

////////////This is th ejava class for a single item with which the list will be filled
public class CardActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_card);
//    }
    private String imageurl;
    public CardActivity(){

    }
    public CardActivity(String ImgUrl){
        imageurl=ImgUrl;///////////card me image link dwara hi load ki jayegi

    }
    ////make getter setter methods
    public String getImageurl(){
        return imageurl;

    }
    public void setImageurl(String imgurl){
        imageurl=imgurl;
    }

}
