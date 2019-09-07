package com.allever.allsample.serialize;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-11-4.
 */

public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

//        UserSerializable user = (UserSerializable) getIntent().getSerializableExtra("user");
//        if (user != null){
//            showToast(user.getName());
//        }

        UserParcel user = (UserParcel) getIntent().getParcelableExtra("user");
        if (user != null){
            showToast(user.getName());
        }
    }
}
