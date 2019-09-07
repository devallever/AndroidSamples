package com.allever.mysimple.simpleFactory;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2016/11/12.
 */

public class CalculatorActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_result;
    private double number_1 = 0;
    private double number_2 = 0;
    private String operate = "";

    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private Button btn_0;
    private Button btn_clean;
    private Button btn_add;
    private Button btn_reduce;
    private Button btn_multiply;
    private Button btn_division;
    private Button btn_get_result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity_layout);


        initView();

        //tv_result.setText("1 + 2 = ");

    }

    private void initView(){
        tv_result = (TextView) this.findViewById(R.id.id_calculator_activity_tv_result);

        btn_0 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_0);
        btn_1 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_1);
        btn_2 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_2);
        btn_3 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_3);
        btn_4 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_4);
        btn_5 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_5);
        btn_6 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_6);
        btn_7 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_7);
        btn_8 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_8);
        btn_9 = (Button) this.findViewById(R.id.id_calculator_activity_btn_number_9);
        btn_add = (Button) this.findViewById(R.id.id_calculator_activity_btn_add);
        btn_reduce = (Button) this.findViewById(R.id.id_calculator_activity_btn_reduce);
        btn_multiply = (Button) this.findViewById(R.id.id_calculator_activity_btn_multiply);
        btn_division = (Button) this.findViewById(R.id.id_calculator_activity_btn_division);
        btn_clean = (Button) this.findViewById(R.id.id_calculator_activity_btn_clean);
        btn_get_result = (Button) this.findViewById(R.id.id_calculator_activity_btn_get_result);


        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_reduce.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_division.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
        btn_get_result.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        StringBuilder stringBuilder = new StringBuilder();
        int id  = view.getId();
        if (id == R.id.id_calculator_activity_btn_number_0) {
            show(0);
        } else if (id == R.id.id_calculator_activity_btn_number_1) {
            show(1);
        } else if (id == R.id.id_calculator_activity_btn_number_2) {
            show(2);
        } else if (id == R.id.id_calculator_activity_btn_number_3) {
            show(3);
        } else if (id == R.id.id_calculator_activity_btn_number_4) {
            show(4);
        } else if (id == R.id.id_calculator_activity_btn_number_5) {
            show(5);
        } else if (id == R.id.id_calculator_activity_btn_number_6) {
            show(6);
        } else if (id == R.id.id_calculator_activity_btn_number_7) {
            show(7);
        } else if (id == R.id.id_calculator_activity_btn_number_8) {
            show(8);
        } else if (id == R.id.id_calculator_activity_btn_number_9) {
            show(9);
        } else if (id == R.id.id_calculator_activity_btn_add) {
            operate = "+";
            tv_result.setText(number_1 + operate);
        } else if (id == R.id.id_calculator_activity_btn_reduce) {
            operate = "-";
            tv_result.setText(number_1 + operate);
        } else if (id == R.id.id_calculator_activity_btn_multiply) {
            operate = "*";
            tv_result.setText(number_1 + operate);
        } else if (id == R.id.id_calculator_activity_btn_division) {
            operate = "/";
            tv_result.setText(number_1 + operate);
        } else if (id == R.id.id_calculator_activity_btn_clean) {
            number_1 = 0;
            number_2 = 0;
            operate = "";
            tv_result.setText("0");
        } else if (id == R.id.id_calculator_activity_btn_get_result) {
            if (operate.equals("")) return;
            Operation operation = OperationFactory.createOperate(operate);
            operation.setNumber_1(number_1);
            operation.setNumber_2(number_2);
            double result = operation.getResult();
            tv_result.setText(number_1 + " " + operate + " " + number_2 + " = " + result);
            //
            number_1 = result;
            number_2 = 0;
            operate = "";
        }
    }


    private void show(double number){
        if (operate.equals("")){
            number_1 = number;
            tv_result.setText(number_1 +"");
        }
        else {
            number_2 = number;
            tv_result.setText(number_1 +" " + operate + " " + number_2);
        }
    }


}
