package com.example.aabhashgod.ecanteen;

import com.google.firebase.database.Exclude;

public class UploadModel {

    private String mName;
    private String mImageUrl;
    private String mKey;
    public UploadModel(){
        //empty constructor needed
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public UploadModel(String name, String imageUrl){

        if (name.trim().equals("")){
            name = "No name";

        }

        mName = name;
        mImageUrl = imageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;

    }
}
