package com.thatnight.videoplayer.entity;

import java.util.List;

public class AbTestDataBean {
    /**
     * flow_id : 50
     * algo_list : ["kmeans_v1","vi_simi_v1","hot_fill"]
     * recom_token : By0uAdO+EjZqp3SsGV+xkqYSCjI
     */


    private int flow_id;
    private String recom_token;
    private List<String> algo_list;

    public int getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(int flow_id) {
        this.flow_id = flow_id;
    }

    public String getRecom_token() {
        return recom_token;
    }

    public void setRecom_token(String recom_token) {
        this.recom_token = recom_token;
    }

    public List<String> getAlgo_list() {
        return algo_list;
    }

    public void setAlgo_list(List<String> algo_list) {
        this.algo_list = algo_list;
    }


}