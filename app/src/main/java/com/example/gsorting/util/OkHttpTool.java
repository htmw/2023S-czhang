package com.example.gsorting.util;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpTool {

    private static String TAG = "OkHttpTool";

    private static final OkHttpClient myOkHttpClient;

    static {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Log.i(TAG, message);
            }
        });

        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        loggingInterceptor.setLevel(level);


        CookieJar cookieJar=new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };


        myOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cookieJar(cookieJar)
                .build();
    }


    @SuppressWarnings("unused")
    public static void httpGet(String url, Map<String, Object> parameters, ResponseCallback responseCallback) {
        Request request = createGetRequest(url, parameters);
        doRequest(request, responseCallback);
    }


    public static void httpPost(String url, Map<String, Object> parameters, ResponseCallback responseCallback) {
        Request request = createPostRequest(url, parameters);
        doRequest(request, responseCallback);
    }





    public void getImage(String url)
    {
        Bitmap response;
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback()
                {

                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Bitmap response) {
                    }
                });
    }

    public interface ResponseCallback {
        void onResponse(boolean isSuccess, int responseCode, String response, Exception exception);
    }



    private static Request createGetRequest(String url, Map<String, Object> parameters) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);
        if (url.indexOf('?') <= -1) {

            urlBuilder.append("?");
        }

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            urlBuilder.append("&");
            urlBuilder.append(entry.getKey());
            urlBuilder.append("=");
            urlBuilder.append(entry.getValue().toString());
        }
        return getBaseRequest().url(urlBuilder.toString()).build();
    }


    private static Request createPostRequest(String url, Map<String, Object> parameters) {

        FormBody.Builder builder = new FormBody.Builder(Charset.forName("UTF-8"));
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        FormBody formBody = builder.build();
        return getBaseRequest().url(url).post(formBody).build();
    }


    private static Request createPostRequestJson(String url, String json) {
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(json,mediaType);
        return getBaseRequest().url(url).post(body).build();
    }


    private static Request createPostRequestWithFile(String url, Map<String, Object> parameters, Map<String, File> files) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (files != null) {
            for (Map.Entry<String, File> fileEntry : files.entrySet()) {
                File file = fileEntry.getValue();
                if (file != null) {

                    RequestBody body = RequestBody.create(file,MediaType.parse("application/octet-stream"));
                    String filename = file.getName();

                    requestBody.addFormDataPart(fileEntry.getKey(), filename, body);
                }
            }
        }
        if (parameters != null) {

            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                requestBody.addFormDataPart(key, value);
            }
        }
        return getBaseRequest().url(url).post(requestBody.build()).build();
    }


    private static Request createPostRequestWithFileByte(String url, Map<String, Object> parameters, Map<String, byte[]> files) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (files != null) {
            for (Map.Entry<String, byte[]> fileEntry : files.entrySet()) {
                byte[] file = fileEntry.getValue();
                if (file != null) {

                    RequestBody body = RequestBody.create(file,MediaType.parse("application/octet-stream"));

                    requestBody.addFormDataPart(fileEntry.getKey(), fileEntry.getKey(), body);
                }
            }
        }
        if (parameters != null) {

            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                requestBody.addFormDataPart(key, value);
            }
        }
        return getBaseRequest().url(url).post(requestBody.build()).build();
    }


    private static void doRequest(final Request request, final ResponseCallback responseCallback) {

        myOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                responseCallback.onResponse(false, -1, null, e);

                if (e.getMessage()!=null){
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                int responseCode = response.code();//获取响应码
                ResponseBody responseBody = response.body();//获取 ResponseBody
                if (response.isSuccessful() && responseBody != null) {
                    String strResponse = responseBody.string();

                    responseCallback.onResponse(true, responseCode, strResponse, null);
                } else {

                    responseCallback.onResponse(false, responseCode, null, null);
                }
            }
        });
    }


    private static Request.Builder getBaseRequest() {
        Request.Builder builder = new Request.Builder();
        builder.addHeader("client", "Android");
        return builder;
    }
}


