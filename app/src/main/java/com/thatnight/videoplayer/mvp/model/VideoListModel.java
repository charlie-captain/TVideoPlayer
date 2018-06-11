package com.thatnight.videoplayer.mvp.model;


import com.google.gson.Gson;
import com.thatnight.videoplayer.constant.Constant;
import com.thatnight.videoplayer.entity.Data;
import com.thatnight.videoplayer.entity.Msg;
import com.thatnight.videoplayer.mvp.contract.VideoListContract;
import com.thatnight.videoplayer.util.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class VideoListModel implements VideoListContract.IModel {

    @Override
    public void getVideoList(int page, int size, final OnVideoListCallBack callBack) {
        String url = Constant.URL_GET_VIDEO_LIST;
        Map<String, String> params = new HashMap<>(8);
        params.put("client", "android");
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("videoid", "5a0cfb2c7a2059547de69ac4");

        OkHttpUtil.getInstance().get(url, params, null, new OkHttpUtil.OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                callBack.error(e.toString());
            }

            @Override
            public void onResponse(byte[] bytes) {
                String response = new String(bytes);
                Gson gson = new Gson();
                try {
                    Msg msg = gson.fromJson(response, Msg.class);
                    Data data = msg.getData();
                    callBack.getResult(data.getInfo_list());
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.error(e.toString());
                }

            }
        });
    }
}
