package com.allever.mysimple.strategyModel;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2016/11/14.
 */

public class CashActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private String[] list_type = {"正常计费","打8折","满300返100"};
    private Spinner spinnerType;
    private ArrayAdapter<CharSequence> arrayAdapter_type;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter_reslut;
    private List<String> list_result = new ArrayList<>();

    private EditText et_price;
    private EditText et_count;
    private String str_price;
    private String str_count;
    private String type = list_type[0];
    private double price = 0;
    private int count = 0;

    private Button btn_reset;
    private Button btn_submit;

    private TextView tv_result;

    private double result = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_activity_layout);
        initView();

    }

    private void initView(){
        toolbar = (Toolbar)findViewById(R.id.id_cash_activity_toolbar);
        setToolbar(toolbar,"计费工具");

        spinnerType = (Spinner)findViewById(R.id.id_cash_activity_spinner_type);
        arrayAdapter_type = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,list_type);
        arrayAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(arrayAdapter_type);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = list_type[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //list_result.add("单价：100 \n数量：2 \n小计：200\t正常计费");
        //list_result.add("单价：100 \n数量：3 \n小计：200\t满300减100");
        //list_result.add("单价：200 \n数量：4 \n小计：640\t打8折");
        arrayAdapter_reslut = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_result);
        listView = (ListView)findViewById(R.id.id_cash_activity_list_view);
        listView.setAdapter(arrayAdapter_reslut);

        btn_reset = (Button)findViewById(R.id.id_cash_activity_btn_reset);
        btn_submit = (Button)findViewById(R.id.id_cash_activity_btn_submit);
        btn_reset.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        tv_result = (TextView)findViewById(R.id.id_cash_activity_tv_result);
        et_count = (EditText)findViewById(R.id.id_cash_activity_et_count);
        et_price = (EditText)findViewById(R.id.id_cash_activity_et_price);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.id_cash_activity_btn_reset) {
            str_count = "";
            et_count.setText(str_count);
            str_price = "";
            et_price.setText(str_price);
            tv_result.setText("");
            price = 0;
            count = 0;
            result = 0;
            list_result.clear();
            arrayAdapter_reslut.notifyDataSetChanged();
        } else if (id == R.id.id_cash_activity_btn_submit) {
            str_price = et_price.getText().toString();
            str_count = et_count.getText().toString();
            if (str_price.equals("")) {
                Toast.makeText(this, "请输入价格", Toast.LENGTH_LONG).show();
                return;
            }
            if (str_count.equals("")) {
                Toast.makeText(this, "请输入数量", Toast.LENGTH_LONG).show();
                return;
            }
            count = Integer.valueOf(str_count);
            price = Double.valueOf(str_price);
            CashContext cashContext = new CashContext(type);
            result = result + cashContext.getResult(price * count);
            tv_result.setText(result + "");
            //list_result.add("单价：100 \n数量：2 \n小计：200\t正常计费");
            list_result.add("单价：" + price + "\n数量：" + count + "\n小计：" + result + "\t" + type);
            arrayAdapter_reslut.notifyDataSetChanged();
        }
    }
}
