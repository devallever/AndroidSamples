package com.allever.allsample.serialize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-11-4.
 */

public class SerializeTestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serialize_test_activity);
    }

    public void startSecond(View view){
        //UserSerializable user = new UserSerializable(1,"Java Core",1);
        UserParcel user = new UserParcel(1,"Android First",1,false);
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context, SerializeTestActivity.class);
        context.startActivity(intent);
    }


}
