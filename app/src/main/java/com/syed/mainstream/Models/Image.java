package com.syed.mainstream.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snaqvi on 9/28/15.
 */
public class Image implements Parcelable{
    private String mName;
    private String mDescription;
    private String mFilename;

    public Image(){

    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getFilename() {
        return mFilename;
    }

    public static String getImageBaseUrl(){
        return "http://entest-webappslab.rhcloud.com/images/";
    }

    public static Image fromJSON(JSONObject imageJson){
        Image image = new Image();
        try{
            image.mName = imageJson.getString("img_name");
            image.mDescription = imageJson.getString("img_description");
            image.mFilename = imageJson.getString("img_filename");

        } catch(JSONException e){
            e.printStackTrace();
        }

        return image;
    }
    public static List<Image> fromJSONArray(JSONArray arrayJson){

        List<Image> images = new ArrayList<>();
        int size = arrayJson.length();

        for(int i=0; i<size; i++) {
            try {
                images.add(Image.fromJSON(arrayJson.getJSONObject(i)));
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }

        return images;
    }

    private Image(Parcel in) {
        this();
        this.mName          = in.readString();
        this.mDescription   = in.readString();
        this.mFilename      = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };


    /*
    * Parcelable implementation
    * */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mDescription);
        parcel.writeString(this.mFilename);
    }
}
