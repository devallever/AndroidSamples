package com.allever.allsample.bitmapoom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Map;

/**
 * Created by allever on 17-11-7.
 */

public class BitmapTestActivity extends BaseActivity{
    private static final String TAG = "BitmapTestActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_test_activity);

        ArrayMap<String,String> arrayMap = new ArrayMap();
        arrayMap.put("1","android");
        arrayMap.put("2","java");
        arrayMap.entrySet();
        for (Map.Entry<String ,String> entry: arrayMap.entrySet()){
            Log.d(TAG, "onCreate: value = " + entry.getValue());
        }
    }

    public void loadBitmap(View view){
        showToast("loadBitmap");
        ImageView imageView = (ImageView) findViewById(R.id.id_bitmap_iv);
        Glide.with(this)
                //.load("https://cn.bing.com/az/hprichbg/rb/BFBadger_ZH-CN8490916760_1920x1080.jpg")
                .load("http://p1.pstatp.com/large/166200019850062839d3")
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
        //imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.large_bitmap));
        //imageView.setImageResource(R.mipmap.large_bitmap);
        //imageView.setImageBitmap(BitmapUtil.decodeSampleBitmapFromRes(getResources(),R.mipmap.large_bitmap,200,200));
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context, BitmapTestActivity.class);
        context.startActivity(intent);
    }


}
