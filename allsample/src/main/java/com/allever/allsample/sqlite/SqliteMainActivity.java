package com.allever.allsample.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * @author allever
 * Created by allever on 17-12-11.
 */

public class SqliteMainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SqliteMainActivity";

    private Button btn_create;
    private Button btn_add;
    private Button btn_delete;
    private Button btn_update;
    private Button btn_query;

    private EditText et_name;
    private EditText et_isbn;
    private EditText et_price;
    private EditText et_count;
    private EditText et_condiction;

    private TextView tv_result;

    private String mName;
    private String mIsbn;
    private int mCount;
    private float mPrice;
    private String mCondiction;

    private DBHelper mDBHelper;
    private SQLiteDatabase mSqliteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_main_activity_layout);

        initView();

        mDBHelper = new DBHelper(this,"BookStore.db",null,2);
        mSqliteDatabase = mDBHelper.getWritableDatabase();


    }

    private void initView(){
        btn_add = (Button)findViewById(R.id.id_sqlite_main_activity_btn_add);
        btn_delete = (Button)findViewById(R.id.id_sqlite_main_activity_btn_delete);
        btn_update = (Button)findViewById(R.id.id_sqlite_main_activity_btn_update);
        btn_query = (Button)findViewById(R.id.id_sqlite_main_activity_btn_query);
        btn_create = (Button)findViewById(R.id.id_sqlite_main_activity_btn_create);
        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_create.setOnClickListener(this);

        et_name = (EditText)findViewById(R.id.id_sqlite_main_activity_et_name);
        et_isbn = (EditText)findViewById(R.id.id_sqlite_main_activity_et_isbn);
        et_price = (EditText)findViewById(R.id.id_sqlite_main_activity_et_price);
        et_condiction = (EditText)findViewById(R.id.id_sqlite_main_activity_et_condiction);
        et_count = (EditText)findViewById(R.id.id_sqlite_main_activity_et_count);

        tv_result = (TextView)findViewById(R.id.id_sqlite_main_activity_tv_result);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.id_sqlite_main_activity_btn_add) {
            addBook();
        } else if (id == R.id.id_sqlite_main_activity_btn_delete) {
        } else if (id == R.id.id_sqlite_main_activity_btn_update) {
        } else if (id == R.id.id_sqlite_main_activity_btn_query) {
            queryBook();
        } else if (id == R.id.id_sqlite_main_activity_btn_create) {
            createDB();
        }
    }

    private void getData(){
        mName = et_name.getText().toString();
        mIsbn = et_isbn.getText().toString();
        mCount = Integer.valueOf(et_count.getText().toString());
        mPrice = Float.valueOf(et_price.getText().toString());
    }

    private void createDB(){

    }

    private void addBook(){
        getData();
        ContentValues cv = new ContentValues();
        cv.put("name",mName);
        cv.put("isbn",mIsbn);
        cv.put("price",mPrice);
        cv.put("count", mCount);
        long result = mSqliteDatabase.insert("book",null ,cv);
        Log.d(TAG, "addBook: result = " + result);
    }

    private void queryBook(){
        mCondiction = et_condiction.getText().toString();
        Log.d(TAG, "queryBook: ");
        //public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        Cursor cursor = null;
        if (TextUtils.isEmpty(mCondiction)) {
            cursor = mSqliteDatabase.query("book",null,null,null,null,null,null);
        }else {
            cursor = mSqliteDatabase.rawQuery("select * from book where " + mCondiction,null);
        }
        //Cursor cursor = mSqliteDatabase.query("book",null,null,null,null,null,"price desc");
        Log.d(TAG, "queryBook: sql = " + "select * from book where " + mCondiction);
        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()){
            Log.d(TAG, "queryBook: 有数据");
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String price = String.valueOf(cursor.getFloat(cursor.getColumnIndex("price")));

                Log.d(TAG, "queryBook: name = " + name + ": price = " + price);
                sb.append(name + ": " + price + "\n");
            }while (cursor.moveToNext());
        }
        tv_result.setText(sb.toString());
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context, SqliteMainActivity.class);
        context.startActivity(intent);
    }
}
