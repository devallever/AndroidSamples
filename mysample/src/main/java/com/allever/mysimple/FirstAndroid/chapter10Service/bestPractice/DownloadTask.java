package com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.PUT;

/**
 * Created by allever on 17-4-20.
 */

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    public enum DOWNLOAD_STATE{
        DOWNLOADING,PAUSE,FINISHED,CANCEL,FAILED
    }

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    public static DOWNLOAD_STATE DOWNLOAD_STATUE = DOWNLOAD_STATE.DOWNLOADING; //-1:未下载,失败,取消  0:正在下载   1:展厅

    private DownloadListener downloadListener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownloadListener downloadListener){
        this.downloadListener = downloadListener;
    }

    public static long getContentLength(String url) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream inputStream = null;
        RandomAccessFile saveFile = null;
        File file = null;
        try {
            long downloadedLength = 0;  //已下载文件长度
            String url = params[0];
            String fileName = url.substring(url.lastIndexOf("/"));
            String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(dir + fileName);
            if (file.exists()){
                downloadedLength = file.length();
            }
            long contentLength = getContentLength(url);
            if (contentLength == 0 ){
                return TYPE_FAILED;
            }else if (contentLength == downloadedLength){
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //断点下载
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null){
                inputStream = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadedLength); //跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = inputStream.read(b)) != -1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        saveFile.write(b,0,len);
                        int progress = (int)((total + downloadedLength) * 100 / contentLength);
                        DOWNLOAD_STATUE = DOWNLOAD_STATE.DOWNLOADING;
                        publishProgress(progress,(int)contentLength);
                    }

                }
                response.body().close();
                return TYPE_SUCCESS;
            }


        }catch (IOException ioe){
            ioe.printStackTrace();
        }finally {
            try {
                if (inputStream != null) inputStream.close();
                if (saveFile != null) saveFile.close();
                if (isCanceled && file != null) file.delete();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        int fileLength = values[1];
        if (progress > lastProgress){
            downloadListener.onProgress(progress,fileLength);
            lastProgress = progress;
        }
        //super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case TYPE_SUCCESS:
                DOWNLOAD_STATUE = DOWNLOAD_STATE.FINISHED;
                downloadListener.onSuccess();
                break;
            case TYPE_FAILED:
                DOWNLOAD_STATUE = DOWNLOAD_STATE.FAILED;
                downloadListener.onFailed();
                break;
            case TYPE_PAUSED:
                DOWNLOAD_STATUE = DOWNLOAD_STATE.PAUSE;
                downloadListener.onPaused();
                break;
            case TYPE_CANCELED:
                DOWNLOAD_STATUE = DOWNLOAD_STATE.CANCEL;
                downloadListener.onCanceled();
                break;
        }
        //super.onPostExecute(integer);
    }

    public void pauseDownload(){
        isPaused = true;
    }
    public void cancelDownload(){
        isCanceled = true;
    }
}
