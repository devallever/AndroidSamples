package com.allever.allsample.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allever on 17-12-11.
 */

public class Book implements Parcelable{
    private String name;
    private float price;
    private String isbn;
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeFloat(this.price);
        dest.writeString(this.isbn);
        dest.writeInt(this.count);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.name = in.readString();
        this.price = in.readFloat();
        this.isbn = in.readString();
        this.count = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
