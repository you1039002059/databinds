package com.app.model;

import android.os.Parcel;
import android.os.Parcelable;
/*
 * Created by Yash on 13/6/18.
 */

public class DataManager implements Parcelable
{
    private String title;
    private String image;
    private String htmlText;

    public DataManager()
    {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    @Override
    public String toString()
    {
        return "DataManager{" +
                "title='" + title + '\'' +
                "image='" + image + '\'' +
                ", htmlText='" + htmlText + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(htmlText);
    }

    public DataManager(Parcel in)
    {
        title = in.readString();
        image = in.readString();
        htmlText = in.readString();
    }

    public static final Parcelable.Creator<DataManager> CREATOR = new Parcelable.Creator<DataManager>() {
        @Override
        public DataManager createFromParcel(Parcel in) {
            return new DataManager(in);
        }

        @Override
        public DataManager[] newArray(int size) {
            return new DataManager[size];
        }
    };
}
