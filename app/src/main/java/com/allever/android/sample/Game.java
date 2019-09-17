package com.allever.android.sample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author allever
 */
public class Game implements Parcelable {
    public String gameName;
    public String gameDescribe;

    public Game(String name, String desc){
        gameName = name;
        gameDescribe = desc;
    }

    protected Game(Parcel in) {
        gameName = in.readString();
        gameDescribe = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gameName);
        dest.writeString(gameDescribe);
    }
}
