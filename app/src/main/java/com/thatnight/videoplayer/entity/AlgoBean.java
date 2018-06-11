package com.thatnight.videoplayer.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class AlgoBean implements Parcelable {
    /**
     * item_id : 5a0cfb2c7a2059547de69ac4
     * sub_algo : {}
     * reason : first_video
     * score : 0
     * algo : first_video
     */

    private String item_id;
    private String reason;
    private float score;
    private String algo;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.item_id);
        dest.writeString(this.reason);
        dest.writeFloat(this.score);
        dest.writeString(this.algo);
    }

    public AlgoBean() {
    }

    protected AlgoBean(Parcel in) {
        this.item_id = in.readString();
        this.reason = in.readString();
        this.score = in.readInt();
        this.algo = in.readString();
    }

    public static final Creator<AlgoBean> CREATOR = new Creator<AlgoBean>() {
        @Override
        public AlgoBean createFromParcel(Parcel source) {
            return new AlgoBean(source);
        }

        @Override
        public AlgoBean[] newArray(int size) {
            return new AlgoBean[size];
        }
    };
}