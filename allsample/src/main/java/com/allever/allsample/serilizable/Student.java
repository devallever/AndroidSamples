package com.allever.allsample.serilizable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by allever on 17-11-11.
 */

public class Student implements Parcelable{
    private long id;
    private String name;
    private int age;


    public static final Creator<Student> CREATOR = new Creator<Student>() {
        /**
         * 读取writeToParcel中写出的字段,并返回该对象
         * 读取的顺序和写出时的顺序一致*/
        @Override
        public Student createFromParcel(Parcel parcel) {
            Student student = new Student();
            student.id = parcel.readLong();
            student.name = parcel.readString();
            student.age = parcel.readInt();
            return student;
        }

        /**
         * 返回方法中参数大小的数组对象*/
        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    /**
     * 接口方法，直接返回0*/
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 接口方法：将该类的字段一一写出*/
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeInt(age);
    }
}
