package com.ashishapps.android.diwaliwallpapaersclient;

import com.google.firebase.database.Exclude;

////////////This is th ejava class for a single item with which the list will be filled
public class CardActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_card);
//    }
    private String imageurl;
    private String dbkey;
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
    @Exclude
    public String getDbkey(){
        return dbkey;
    }
    @Exclude
    public void setDbkey(String key){
        dbkey=key;
    }


}
