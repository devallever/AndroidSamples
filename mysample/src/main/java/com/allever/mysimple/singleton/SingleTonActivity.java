package com.allever.mysimple.singleton;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2016/11/24.
 */

public class SingleTonActivity extends BaseActivity {
    private Button btn_dcl;
    private Button btn_static;
    private Button btn_none;

    private MyOkhttp myOkhttp;
    private MyWeb myWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleton_activity_layout);

        setToolbar((Toolbar)findViewById(R.id.id_toolbar),"单例模式");

        initView();

    }

    private void initView(){
        btn_dcl = (Button)findViewById(R.id.id_singleton_activity_dcl);
        btn_none= (Button)findViewById(R.id.id_singleton_activity_none_singleton);
        btn_static = (Button)findViewById(R.id.id_singleton_activity_static);

        btn_dcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(MyOkhttp.getInstance().toString() + "\n" + MyOkhttp.getInstance().toString() + "\n" + MyOkhttp.getInstance().toString());
            }
        });
        btn_static.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(MyWeb.getInstance().toString() + "\n" + MyWeb.getInstance().toString() + "\n" + MyWeb.getInstance().toString());
            }
        });


    }

}
