package com.allever.mysimple.FirstAndroid.chapter2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/2/28.
 */

public class FirstActivity extends FirstAndroidBaseActivity {
    private Button btn_explicit;
    private Button btn_implicit;
    private Button btn_finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_android_first_activity_layout);

        btn_explicit = (Button)findViewById(R.id.id_first_activity_btn_explicit_start);
        btn_explicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FirstActivity.this, "you click button 1", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                intent.putExtra("receive_string","Hello!!!!");
//                startActivityForResult(intent,1);
                SecondActivity.actionStart(FirstActivity.this,"xm",1);
            }
        });

        btn_implicit = (Button)findViewById(R.id.id_first_activity_btn_implicit_start);
        btn_implicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.allever.mysimple.FirstAndroid.chapter2.ACTION_START");
                intent.addCategory("com.allever.mysimple.FirstAndroid.chapter2.MY_CATEGORY");//û�п�������category�ᱨ��
                startActivity(intent);
            }
        });

        btn_finish = (Button)findViewById(R.id.id_first_activity_btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstActivity.this.finish();
            }
        });

        ((Button)findViewById(R.id.id_first_activity_btn_start_browser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });


        ((Button)findViewById(R.id.id_first_activity_btn_start_dial)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:13046217683"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String result = data.getStringExtra("result_string");
                    Toast.makeText(this,result,Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_first_activity_menu_notification) {
            Toast.makeText(this, "Notification", Toast.LENGTH_LONG).show();
        } else if (id == R.id.id_first_activity_menu_setting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
