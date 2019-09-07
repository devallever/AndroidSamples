package com.allever.allsample.serialize;

import android.os.Parcel;
import android.os.Parcelable;

import com.allever.allsample.okhttp.bean.User;

/**
 * Created by allever on 17-11-4.
 */

public class UserParcel implements Parcelable {
    private int id;
    private String name;
    private long isbn;
    private boolean isFirstVersion;

    private UserParcel(Parcel parcel){
        id = parcel.readInt();
        name = parcel.readString();
        isbn = parcel.readLong();
        isFirstVersion = parcel.readInt() == 1;
    }

    public UserParcel(){}

    public UserParcel(int id,String name,long isbn,boolean isFirstVersion){
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.isFirstVersion = isFirstVersion;
    }



    public static final Creator<UserParcel> CREATOR = new Creator<UserParcel>() {
        @Override
        public UserParcel createFromParcel(Parcel parcel) {
            return new UserParcel(parcel);
        }

        @Override
        public UserParcel[] newArray(int i) {
            return new UserParcel[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeLong(isbn);
        parcel.writeInt(isFirstVersion?1:0);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public boolean isFirstVersion() {
        return isFirstVersion;
    }

    public void setFirstVersion(boolean firstVersion) {
        isFirstVersion = firstVersion;
    }
}
