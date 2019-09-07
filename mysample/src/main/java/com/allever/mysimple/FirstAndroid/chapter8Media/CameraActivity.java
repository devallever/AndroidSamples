package com.allever.mysimple.FirstAndroid.chapter8Media;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by allever on 17-3-27.
 */

public class CameraActivity extends FirstAndroidBaseActivity {

    private static final String TAG = "CameraActivity";

    private static final int TAKE_PHOTO = 1;
    private static final int CHOSE_PHOTO = 2;

    private Button btn_take_photo;
    private Button btn_chose_photo;
    private ImageView imageView;

    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity_layout);

        imageView = (ImageView)findViewById(R.id.id_camera_activity_iv);

        btn_take_photo = (Button)findViewById(R.id.id_camera_activity_btn_take_photo);
        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建File对象，存储图片
                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                if (outputImage.exists()) outputImage.delete();
                try {
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(CameraActivity.this,
                            "com.allever.mysimple.FirstAndroid.chapter8Media.fileprovider",
                            outputImage);
                }else{
                    imageUri = Uri.fromFile(outputImage);
                }

                //启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        btn_chose_photo = (Button)findViewById(R.id.id_camera_activity_btn_chose_photo);
        btn_chose_photo.setOnClickListener(v -> {
            //运行时权限
            if (ContextCompat.checkSelfPermission(CameraActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(CameraActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

            }else{
                openAlbum();
            }
        });
    }

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }

                }
                break;
            case CHOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断系统版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitkat(data);
                    }else {
                        handleImageBeforeKitkat(data);
                    }
                }
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitkat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        Log.d(TAG, "uri = " + data.getData());
        //快图浏览      content://com.alensw.PicFolder.FileProvider/document/%2Fstorage%2Femulated%2F0%2Foutput_image.jpg
        //最近/Image   content://com.android.providers.media.documents/document/image%3A161
        //下载         content://com.android.providers.downloads.documents
        //文件浏览器    file:///sdcard/0SDcard/P/WeiXin/001b24dc4bf4107dd4e858~01.jpg
        //谷歌照片      content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F271/ORIGINAL/NONE/1895548347
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是Document类型的Uri 则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            Log.d(TAG, "docId = " + docId);
            //docId = image:3232
            Log.d(TAG, "uri.getAuthority() = " + uri.getAuthority());
            //uri.getAuthority() = com.android.providers.media.documents
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                Log.d(TAG, "selection = " + selection);
                //selection = _id=3232
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的uri
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的uri
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }


    private void handleImageBeforeKitkat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        //通过uri和selection获取真实图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if (imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"fail to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
