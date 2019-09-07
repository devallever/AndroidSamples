package com.allever.mysimple.FirstAndroid.chapter10Service.bestPractice;

/**
 * Created by allever on 17-4-20.
 */

public interface DownloadListener {
    void onProgress(int progress,int length);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
