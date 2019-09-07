package com.allever.allsample.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-8-26.
 */

public class DialogActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_base_alert_dialog;
    private Button btn_custom_view_dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity);
        initView();
    }

    private void initView(){
        btn_base_alert_dialog = (Button)findViewById(R.id.id_dialog_activity_btn_base_alert_dialog);
        btn_base_alert_dialog.setOnClickListener(this);
        btn_custom_view_dialog = (Button)findViewById(R.id.id_dialog_activity_btn_custom_view_alert_dialog);
        btn_custom_view_dialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_dialog_activity_btn_base_alert_dialog) {
            showBaseAlertDialog();
        } else if (id == R.id.id_dialog_activity_btn_custom_view_alert_dialog) {
            showCustomViewDialog();
        }
    }

    private void showCustomViewDialog(){
        //final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.history_dialog_choose_patter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.custom_dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom_view,null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showBaseAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This is message");
        builder.setTitle("This is Title");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this,"Click OK",Toast.LENGTH_SHORT).show();
                builder.create().dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this,"Click Cancel",Toast.LENGTH_SHORT).show();
                builder.create().dismiss();
            }
        });
        builder.create().show();
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context, DialogActivity.class);
        context.startActivity(intent);
    }
}
