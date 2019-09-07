package com.allever.allsample.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by allever on 17-12-11.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private Context mContext;
    private static final String CREATE_TABLE_BOOK = "create table book(" +
            "id integer primary key autoincrement," +
            "name text," +
            "price real," +
            "isbn text," +
            "count integer)";

    public DBHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version){
        super(context,dbName,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_BOOK);
        Toast.makeText(mContext,"create success",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: create success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists book");
        onCreate(sqLiteDatabase);
    }
}
