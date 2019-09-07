package com.allever.allsample.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by allever on 17-10-13.
 */

public class Client {
    private void test(){
        ImageLoader imageLoader = new ImageLoader();
        //依赖注入
        imageLoader.setImageCache(new MemoryCache());
        imageLoader.setImageCache(new DiskCache());
        imageLoader.setImageCache(new DoubleCache());
        imageLoader.setImageCache(new ImageCache() {
            @Override
            public Bitmap get(String url) {
                return null;
            }

            @Override
            public void put(String url, Bitmap bitmap) {

            }
        });
        imageLoader.displayImage("",new ImageView(null));
    }

}
