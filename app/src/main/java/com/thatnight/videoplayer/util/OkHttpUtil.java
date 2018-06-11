package com.thatnight.videoplayer.util;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {


    private static OkHttpClient mOkHttpClient;
    private static OkHttpClient.Builder mBuilder;
    private Handler mHandler;

    public static final int CONNECT_TIMEOUT = 6;
    public static final int READ_TIMEOUT = 8;
    public static final int WRITE_TIMEOUT = 8;

    private OkHttpUtil() {
        mBuilder = new OkHttpClient.Builder();
        mBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).
                writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClient = mBuilder.build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpUtil getInstance() {
        return Instance.INSTANCE;
    }

    private static class Instance {
        private static final OkHttpUtil INSTANCE = new OkHttpUtil();
    }


    public void get(String url, OkHttpResultCallback callback) {
        pullToRequest(buildGetRequest(url, null, null), callback);
    }

    public void get(String url, Map<String, String> headers, OkHttpResultCallback callback) {
        pullToRequest(buildGetRequest(url, null, headers), callback);
    }

    public void get(String url, Map<String, String> params, Map<String, String> headers, OkHttpResultCallback callback) {
        pullToRequest(buildGetRequest(url, params, headers), callback);
    }

    private void pullToRequest(Request request, final OkHttpResultCallback callback) {
        if (Instance.INSTANCE == null) {
            getInstance();
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                postFailedCallback(call, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    switch (response.code()) {
                        case 200:
                            postSuccessCallback(response.body().bytes(), callback);
                            break;
                        case 302:
                            postSuccessCallback(response.body().bytes(), callback);
                            break;
                        default:
                            throw new IOException();
                    }
                } catch (IOException e) {
                    postFailedCallback(call, e, callback);
                }
            }
        });
    }

    /**
     * UI线程回调
     *
     * @param call
     * @param e
     * @param callback
     */
    private void postFailedCallback(final Call call, final Exception e, final OkHttpResultCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(call, e);
                }
            }
        });
    }

    private void postSuccessCallback(final byte[] bytes, final OkHttpResultCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(bytes);
                }
            }
        });
    }


    /**
     * 构建get请求[带头, 带参数]
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    private Request buildGetRequest(String url, Map<String, String> params, Map<String, String> headers) {
        Headers.Builder builder = new Headers.Builder();
        if (headers != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }


        if (params != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            sb.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            sb.deleteCharAt(sb.lastIndexOf("&"));
            url = sb.toString();
        }
        return new Request.Builder().url(url).headers(builder.build()).build();
    }


    /**
     * 请求回调接口
     */
    public interface OkHttpResultCallback {
        void onError(Call call, Exception e);

        void onResponse(byte[] bytes);
    }
}
