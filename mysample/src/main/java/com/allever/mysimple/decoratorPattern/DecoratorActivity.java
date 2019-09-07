package com.allever.mysimple.decoratorPattern;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2016/11/19.
 */

public class DecoratorActivity extends BaseActivity {
    private Toolbar toolbar;
    private ImageView iv_pic;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String[] dress_types = {"Expensive Cloth", "Cheap Cloth"};
    private int position = 0;

    private Person angewomon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decorator_activity_layout);

        initView();

    }

    private void initView(){

        angewomon = new Angewomon();

        setToolbar((Toolbar) findViewById(R.id.id_decorator_activity_toolbar), "装饰模式");
        iv_pic = (ImageView)findViewById(R.id.id_decorator_activity_iv);
        spinner = (Spinner)findViewById(R.id.id_decorator_activity_spinner_choose_cloth);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dress_types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                switch (position){
                    case 0:
                        iv_pic.setImageDrawable(getResources().getDrawable(R.mipmap.expensive));
                        PersonCloth expensiveCloth = new ExpensiveCloth(angewomon);
                        expensiveCloth.dressed();
                        break;
                    case 1:
                        iv_pic.setImageDrawable(getResources().getDrawable(R.mipmap.cheap));
                        PersonCloth cheapCloth = new CheapCloth(angewomon);
                        cheapCloth.dressed();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                position = 0;
            }
        });


    }
}
