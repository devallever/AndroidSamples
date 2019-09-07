package com.allever.mysimple.FirstAndroid.chapter4Fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.Button;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2017/3/9.
 */

public class FragmentTestActivity extends FirstAndroidBaseActivity {
    private Button btn_change;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test_activity_layout);


//        replaceFragment(new RightFragment());
//        btn_change = (Button)findViewById(R.id.id_left_fragment_layout_btn_change);
//        btn_change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                replaceFragment(new RightFragment2());
//            }
//        });
    }

    private void replaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.id_fragment_test_activity_frame_layout,fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

}
