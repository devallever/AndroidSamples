package com.allever.mysimple.util;

/**
 * Created by Allever on 2016/12/9.
 */

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by XM on 2016/4/19.
 */
public class CommentUtil {
    public static final int REQUEST_CODE_CHOOSE_PIC = 0;
    public static final int REQUEST_CODE_CUTTING = 1;

    public static String getDate(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = month + "月" + day + "日";
        return date;
    }
    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }
    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }

    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }


    /**
     * 判断应用在前台还是后台
     * **/
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                }else{
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * 显示进度对话框
     * **/
    public static void showProgressDialog(ProgressDialog progressDialog, Context context){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在加载");
            progressDialog.setCancelable(true);
        }
        progressDialog.show();
    }


    /**
     * 关闭进度对话框
     * **/
    public static void closeProgressDialog(ProgressDialog progressDialog){
        if (progressDialog != null) progressDialog.dismiss();
    }


//    /**
//     *初始化toolbar
//     * **/
//    public static void initToolbar(final BaseActivity activity,Toolbar toolbar,String title){
//        toolbar.setTitle(title);
//        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
//        activity.setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                activity.finish();
//            }
//        });
//    }


    /**
     * 根据item设置listview高度
     * */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

//    public static boolean checkUserState(Context context){
//        CooperationDBAdapter db = new CooperationDBAdapter(context);
//        db.open();
//
//        if(db.checkUserState().equals("0")){//0:表示没有用户登录
//            return false;
//        }else{
//            return true;
//        }
//    }


    /**
     * InputStream转字节数组byte[]
     * **/
    public static byte[] inputStramToByte(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    public static void saveFile(Bitmap bm, String fileName) throws IOException {
        String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/mysimple/";


        File dirFile = new File(ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File imageFile = new File(ALBUM_PATH + fileName);
        Log.d("SaveFile", imageFile.getPath());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }


    /**
     * byte 转bitmap
     * */
    public static Bitmap byteToBitmap(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }


    /**
     *选择图片后获取路径
     * */
    public static  String getImageFilePath(Context context, Uri uri){

        String filePath;
//
//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
//            if(uri.toString().split("file://").length>1){
//                return uri.toString().split("file://")[1];
//            }else{
//
//            }
//            String wholeID = DocumentsContract.getDocumentId(uri);
//            String id = wholeID.split(":")[1];
//            String[] column = { MediaStore.Images.Media.DATA };
//
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            return picturePath;
//
////            String sel = MediaStore.Images.Media._ID + = ?;
////            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
////                    sel, new String[] { id }, null);
////            int columnIndex = cursor.getColumnIndex(column[0]);
////            if (cursor.moveToFirst()) {
////                filePath = cursor.getString(columnIndex);
////            }
////            cursor.close();
//        }else{


        //从图片
        System.out.print(uri.toString());
        if(uri.toString().split("android").length>1){
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String[] column = { MediaStore.Images.Media.DATA };

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                return picturePath;
            }

        }

        //从文件选择器....
        if(uri.toString().split("file://").length>1){
            return uri.toString().split("file://")[1];
        }

        //以下是原始代码
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String imgNo = cursor.getString(0); //图片编号
        String imgPath = cursor.getString(1); //图片文件路径
        String imgSize = cursor.getString(2); //图片大小
        String imgName = cursor.getString(3); //图片文件名

        System.out.println("imgNo = " + imgNo);
        System.out.println("imgPath = " + imgPath);
        System.out.println("imgSize = " +imgSize );
        System.out.println("imgName = " + imgName);
        cursor.close();
        return imgPath;
//        }
    }



    /**
     * 打开图片选择器
     * */
    public static void startPicChoiceIntent(Activity activity) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
        activity.startActivityForResult(intent,REQUEST_CODE_CHOOSE_PIC );
    }

    public static void startPhotoZoom(Activity activity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, REQUEST_CODE_CUTTING);
    }

    public static void startPicChoiceIntent(FragmentActivity activity,int imageview_id) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
        activity.startActivityForResult(intent, imageview_id);//requestCode
    }

    public static void startFileChoiceIntent(FragmentActivity activity) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
		/* 开启Pictures画面Type设定为file */
        intent.setType("file/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
        activity.startActivityForResult(intent, 2);
    }

    public static  String getFilePath(Context context, Uri uri){
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String filepath = cursor.getString(1); //图片文件路径
        System.out.println("filePath = " + filepath);
        cursor.close();
        return filepath;
    }


    /**
     * bitmap 转btye
     * */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

//    public static void showFailToConnectService(Context context){
//        final Dialog dialog = new Dialog(context,"提示","连接服务器失败");
//
//
//        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        dialog.show();
//    }



}
