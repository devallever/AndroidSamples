package com.allever.mysimple.retrofit.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by Allever on 2016/12/7.
 */

public class OkhttpUtil {
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), "");
}
