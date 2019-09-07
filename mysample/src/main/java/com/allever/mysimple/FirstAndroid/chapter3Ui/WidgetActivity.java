package com.allever.mysimple.FirstAndroid.chapter3Ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/3/2.
 */

public class WidgetActivity extends FirstAndroidBaseActivity {

    private Button btn_begin;
    private ProgressBar progressBar_hori;

    private Button btn_alert_dialog;
    private Button btn_progress_dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_android_widget_activity_layout);

        progressBar_hori = (ProgressBar)findViewById(R.id.id_first_android_widget_progress_bar_h);

        btn_begin = (Button)findViewById(R.id.id_first_android_widget_btn_begin);
        btn_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = progressBar_hori.getProgress();
                progress = progress + 10;
                if (progress == 110){
                    progress = 10;
                }
                progressBar_hori.setProgress(progress);
            }
        });

        btn_alert_dialog = (Button)findViewById(R.id.id_first_android_widget_btn_show_alert_dialog);
        btn_alert_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WidgetActivity.this);
                builder.setTitle("Title");
                builder.setMessage("Message");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(WidgetActivity.this,"Okkkk",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        btn_progress_dialog = (Button)findViewById(R.id.id_first_android_widget_btn_show_progress_dialog);
        btn_progress_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(WidgetActivity.this);
                progressDialog.setTitle("Title");
                progressDialog.setMessage("Message");
                progressDialog.show();
            }
        });

    }
}
