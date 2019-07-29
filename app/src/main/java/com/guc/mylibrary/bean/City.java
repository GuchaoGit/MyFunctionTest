package com.guc.mylibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by guc on 2019/7/29.
 * 描述：
 */
public class City implements Parcelable {
    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
    public String id;
    public String cityEn;
    public String cityZh;
    public String provinceEn;
    public String provinceZh;
    public String leaderEn;
    public String leaderZh;
    public String lat;
    public String lon;

    public City() {
    }

    protected City(Parcel in) {
        this.id = in.readString();
        this.cityEn = in.readString();
        this.cityZh = in.readString();
        this.provinceEn = in.readString();
        this.provinceZh = in.readString();
        this.leaderEn = in.readString();
        this.leaderZh = in.readString();
        this.lat = in.readString();
        this.lon = in.readString();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cityEn);
        dest.writeString(this.cityZh);
        dest.writeString(this.provinceEn);
        dest.writeString(this.provinceZh);
        dest.writeString(this.leaderEn);
        dest.writeString(this.leaderZh);
        dest.writeString(this.lat);
        dest.writeString(this.lon);
    }
}