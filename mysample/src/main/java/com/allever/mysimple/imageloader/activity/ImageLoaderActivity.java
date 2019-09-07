package com.allever.mysimple.imageloader.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.imageloader.DoubleCache;
import com.allever.mysimple.imageloader.ImageLoader;

/**
 * Created by Allever on 2016/11/16.
 */

public class ImageLoaderActivity extends BaseActivity {

    private ImageView imageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageloader_activity_layout);

        initView();
    }

    private void initView(){
        ((Toolbar)findViewById(R.id.id_image_loader_activity_toolbar)).setTitle("ImageLoader");
        ((Toolbar)findViewById(R.id.id_image_loader_activity_toolbar)).setTitleTextColor(getResources().getColor(R.color.white));
        imageView = (ImageView)this.findViewById(R.id.id_image_loader_activity_iv);
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.setImageCache(new DoubleCache());
        String url = "http://27.54.249.252:8080/SocialServer/images/head/xm.jpg";
        imageLoader.displayImage(url ,imageView);
        //show(url);
    }
}
