package com.allever.mysimple.FirstAndroid.chapter6DataPersistence;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by allever on 17-3-20.
 */

public class PersistenceActivity extends FirstAndroidBaseActivity implements View.OnClickListener {

    private static final String TAG = "PersistenceActivity";

    private EditText et_data;
    private String data;

    private Button btn_save_file;
    private Button btn_read_file;
    private Button btn_save_preference;
    private Button btn_read_preference;

    private Button btn_create_db;
    private Button btn_add_data;
    private Button btn_delete_data;
    private Button btn_query_data;
    private Button btn_update_data;

    private Button btn_lite_pal;

    private MyDatabaseHelper myDatabaseHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persistence_activity_layout);

        myDatabaseHelper = new MyDatabaseHelper(this,"BookStore.db",null,3);

        et_data = (EditText)findViewById(R.id.id_persistence_activity_et_data);

        btn_save_file = (Button)findViewById(R.id.id_persistence_activity_btn_save_file);
        btn_save_file.setOnClickListener(this);
        btn_read_file = (Button)findViewById(R.id.id_persistence_activity_btn_read_file);
        btn_read_file.setOnClickListener(this);
        btn_save_preference = (Button)findViewById(R.id.id_persistence_activity_btn_save_preference);
        btn_save_preference.setOnClickListener(this);
        btn_read_preference = (Button)findViewById(R.id.id_persistence_activity_btn_read_preference);
        btn_read_preference.setOnClickListener(this);

        btn_create_db = (Button)findViewById(R.id.id_persistence_activity_btn_create_db);
        btn_create_db.setOnClickListener(this);
        btn_add_data = (Button)findViewById(R.id.id_persistence_activity_btn_add_data);
        btn_add_data.setOnClickListener(this);
        btn_delete_data = (Button)findViewById(R.id.id_persistence_activity_btn_delete_data);
        btn_delete_data.setOnClickListener(this);
        btn_query_data = (Button)findViewById(R.id.id_persistence_activity_btn_query_data);
        btn_query_data.setOnClickListener(this);
        btn_update_data = (Button)findViewById(R.id.id_persistence_activity_btn_update_data);
        btn_update_data.setOnClickListener(this);

        btn_lite_pal = (Button)findViewById(R.id.id_persistence_activity_btn_lite_pal);
        btn_lite_pal.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        data = et_data.getText().toString();
//        if (data != null || data.length()==0) {
//            Toast.makeText(this,"Please input data!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        SQLiteDatabase db;
        //Cursor cursor;

        if (id == R.id.id_persistence_activity_btn_save_file) {
            saveFile(data);
            et_data.setText("");
        } else if (id == R.id.id_persistence_activity_btn_read_file) {
            data = readFile();
            et_data.setText(data);
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.id_persistence_activity_btn_save_preference) {
            SharedPreferences sharedPreferences;

            /*制定文件名*/
            sharedPreferences = getSharedPreferences("my_share", MODE_PRIVATE);

            /*当前活动类名作为文件名*/
            //sharedPreferences = getPreferences(MODE_PRIVATE);

            /**/
            //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("data", data);
            //editor.commit();
            editor.apply();
            et_data.setText("");
        } else if (id == R.id.id_persistence_activity_btn_read_preference) {
            SharedPreferences sharedPreferences;
            sharedPreferences = getSharedPreferences("my_share", MODE_PRIVATE);
            String result = sharedPreferences.getString("data", "");
            et_data.setText(result);
        } else if (id == R.id.id_persistence_activity_btn_create_db) {
            myDatabaseHelper.getWritableDatabase();
        } else if (id == R.id.id_persistence_activity_btn_add_data) {
            db = myDatabaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", "Android first Code");
            values.put("author", "Guo Lin");
            values.put("pages", 700);
            values.put("price", 79.00);
            db.insert("Book", null, values);

            values.clear();
            values.put("name", "Android Design Model");
            values.put("author", "Guan Aimin");
            values.put("pages", 700);
            values.put("price", 69.00);
            db.insert("Book", null, values);
        } else if (id == R.id.id_persistence_activity_btn_query_data) {
            db = myDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.query("Book", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                    double price = cursor.getDouble(cursor.getColumnIndex("price"));
                    Log.d(TAG, "name = " + name);
                    Log.d(TAG, "author = " + author);
                    Log.d(TAG, "pages = " + pages);
                    Log.d(TAG, "price = " + price);
                    Log.d(TAG, "\n");
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else if (id == R.id.id_persistence_activity_btn_delete_data) {
            db = myDatabaseHelper.getWritableDatabase();
            db.delete("Book", "author = ? ", new String[]{"Guan Aimin"});
        } else if (id == R.id.id_persistence_activity_btn_update_data) {
            db = myDatabaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("price", 100.33);
            db.update("Book", contentValues, "name = ?", new String[]{"Android first Code"});
        } else if (id == R.id.id_persistence_activity_btn_lite_pal) {
            Intent intent = new Intent(this, LitePalActivity.class);
            startActivity(intent);
        }
    }


    private void saveFile(String data){
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = openFileOutput("data",MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(data);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException ioE){
            ioE.printStackTrace();
        }finally {
            if (bufferedWriter!=null){
                try {
                    //fileOutputStream.close();
                    bufferedWriter.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                    //bufferedWriter.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }

    }

    private String readFile(){
        String data = "";
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = null;
        try {
            fileInputStream = openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            builder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine())!=null){
                builder.append(line);
            }
        }catch (FileNotFoundException fileE){
            fileE.printStackTrace();
        }catch (IOException ioE){
            ioE.printStackTrace();
        }finally {
            if (bufferedReader!=null){
                try {
                    bufferedReader.close();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }

            }
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }

            }
        }
        return builder.toString();
    }
}
