package com.allever.allsample.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by allever on 17-10-12.
 */

public class ImageLoader {
    private ImageCache mImageCache = new MemoryCache();
    //private DiskCache mDiskCache = new DiskCache();
    private DoubleCache mDoubleCache = new DoubleCache();
    private boolean mIsUseDiskCache = false;
    private boolean mIsUseDoubleCache = false;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void setImageCache(ImageCache imageCache){
        mImageCache = imageCache;
    }

    public void displayImage(final String url, final ImageView imageView){
        //
        /*
        Bitmap bitmap = mIsUseDiskCache ? mDiskCache.get(url): mImageCache.get(url);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        */

        /*
        Bitmap bitmap = null;
        if (mIsUseDoubleCache){
            bitmap = mDoubleCache.get(url);
        }else if (mIsUseDiskCache){
            bitmap = mDiskCache.get(url);
        }else {
            bitmap = mImageCache.get(url);
        }
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        */

        Bitmap bitmap = mImageCache.get(url);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }


        submitLoaderRequest(url,imageView);


    }

    private void submitLoaderRequest(final String url, final ImageView imageView){
        //
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(url);
                if (bitmap == null){
                    return;
                }
                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }

                mImageCache.put(url,bitmap);
            }
        });
    }


    public Bitmap downloadImage(String imageUrl){
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            httpURLConnection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
    /*
    public void useDiskCache(boolean useDiskCache){
        mIsUseDiskCache = useDiskCache;
    }

    public void useDoubleCache(boolean useDoubleCache){
        mIsUseDoubleCache = useDoubleCache;
    }
    */
}
