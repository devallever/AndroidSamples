package com.allever.mysimple.FirstAndroid.chapter2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/2/28.
 */

public class SecondActivity extends FirstAndroidBaseActivity {

    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_android_second_activity_layout);

        String received = getIntent().getStringExtra("receive_string");
        if (received!=null) Toast.makeText(this,received,Toast.LENGTH_LONG).show();

        btn =(Button)findViewById(R.id.id_second_activity_btn_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("result_string","Hi");
        setResult(RESULT_OK,data);
        super.onBackPressed();
    }

    public static void actionStart(Context context, String username, int id){
        Intent intent = new Intent(context,SecondActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }
}
