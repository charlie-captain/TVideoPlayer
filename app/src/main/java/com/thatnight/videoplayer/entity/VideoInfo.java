package com.thatnight.videoplayer.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VideoInfo implements Parcelable {
    /**
     * liked : 0
     * channelid : 0
     * followed : 0
     * uid : 117556733
     * barrage_num : 2
     * tags :
     * comment_num : 2
     * share : 0
     * low_flv_size : 26.5M
     * barrage_count : 2
     * flv : https://vod.cc.163.com/file/5a0cfb2c7a2059547de69ac4.flv?client=android&protocol=https
     * pia_tagid :
     * invite_msg :
     * duration : 08:20
     * pia_activity_id :
     * height : 1080
     * nickname : BEARBEAR大熊
     * gameinfotags : []
     * src :
     * pv : 175853
     * title : 【终结者军火库】官方论坛出品--教你如何使用新手神器：M4A1
     * roomid : 0
     * live : 0
     * cover : http://vodcover.cc.netease.com/hdg1-cc-vodweb1/cover/mobile_game_capture/2017/1116/5a0cfb2c7a2059547de69ac4.jpg
     * videoid : 5a0cfb2c7a2059547de69ac4
     * share_num : 0
     * width : 1920
     * comment_count : 2
     * praise_num : 7
     * pia_tagname :
     * algo : {"item_id":"5a0cfb2c7a2059547de69ac4","sub_algo":{},"reason":"first_video","score":0,"algo":"first_video"}
     * type : 0
     * default_flv_size : 96.5M
     * purl : http://cc.res.netease.com/webcc/portrait/nsep/headicon/builtin/50
     */

    private int liked;
    private int channelid;
    private int followed;
    private int uid;
    private int barrage_num;
    private String tags;
    private int comment_num;
    private int share;
    private String low_flv_size;
    private int barrage_count;
    private String flv;
    private String pia_tagid;
    private String invite_msg;
    private String duration;
    private String pia_activity_id;
    private int height;
    private String nickname;
    private String src;
    private int pv;
    private String title;
    private int roomid;
    private int live;
    private String cover;
    private String videoid;
    private int share_num;
    private int width;
    private int comment_count;
    private int praise_num;
    private String pia_tagname;
    private AlgoBean algo;
    private int type;
    private String default_flv_size;
    private String purl;
    private List<?> gameinfotags;

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getBarrage_num() {
        return barrage_num;
    }

    public void setBarrage_num(int barrage_num) {
        this.barrage_num = barrage_num;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public String getLow_flv_size() {
        return low_flv_size;
    }

    public void setLow_flv_size(String low_flv_size) {
        this.low_flv_size = low_flv_size;
    }

    public int getBarrage_count() {
        return barrage_count;
    }

    public void setBarrage_count(int barrage_count) {
        this.barrage_count = barrage_count;
    }

    public String getFlv() {
        return flv;
    }

    public void setFlv(String flv) {
        this.flv = flv;
    }

    public String getPia_tagid() {
        return pia_tagid;
    }

    public void setPia_tagid(String pia_tagid) {
        this.pia_tagid = pia_tagid;
    }

    public String getInvite_msg() {
        return invite_msg;
    }

    public void setInvite_msg(String invite_msg) {
        this.invite_msg = invite_msg;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPia_activity_id() {
        return pia_activity_id;
    }

    public void setPia_activity_id(String pia_activity_id) {
        this.pia_activity_id = pia_activity_id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public int getShare_num() {
        return share_num;
    }

    public void setShare_num(int share_num) {
        this.share_num = share_num;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(int praise_num) {
        this.praise_num = praise_num;
    }

    public String getPia_tagname() {
        return pia_tagname;
    }

    public void setPia_tagname(String pia_tagname) {
        this.pia_tagname = pia_tagname;
    }

    public AlgoBean getAlgo() {
        return algo;
    }

    public void setAlgo(AlgoBean algo) {
        this.algo = algo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDefault_flv_size() {
        return default_flv_size;
    }

    public void setDefault_flv_size(String default_flv_size) {
        this.default_flv_size = default_flv_size;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public List<?> getGameinfotags() {
        return gameinfotags;
    }

    public void setGameinfotags(List<?> gameinfotags) {
        this.gameinfotags = gameinfotags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.liked);
        dest.writeInt(this.channelid);
        dest.writeInt(this.followed);
        dest.writeInt(this.uid);
        dest.writeInt(this.barrage_num);
        dest.writeString(this.tags);
        dest.writeInt(this.comment_num);
        dest.writeInt(this.share);
        dest.writeString(this.low_flv_size);
        dest.writeInt(this.barrage_count);
        dest.writeString(this.flv);
        dest.writeString(this.pia_tagid);
        dest.writeString(this.invite_msg);
        dest.writeString(this.duration);
        dest.writeString(this.pia_activity_id);
        dest.writeInt(this.height);
        dest.writeString(this.nickname);
        dest.writeString(this.src);
        dest.writeInt(this.pv);
        dest.writeString(this.title);
        dest.writeInt(this.roomid);
        dest.writeInt(this.live);
        dest.writeString(this.cover);
        dest.writeString(this.videoid);
        dest.writeInt(this.share_num);
        dest.writeInt(this.width);
        dest.writeInt(this.comment_count);
        dest.writeInt(this.praise_num);
        dest.writeString(this.pia_tagname);
        dest.writeParcelable(this.algo, flags);
        dest.writeInt(this.type);
        dest.writeString(this.default_flv_size);
        dest.writeString(this.purl);
        dest.writeList(this.gameinfotags);
    }

    public VideoInfo() {
    }

    protected VideoInfo(Parcel in) {
        this.liked = in.readInt();
        this.channelid = in.readInt();
        this.followed = in.readInt();
        this.uid = in.readInt();
        this.barrage_num = in.readInt();
        this.tags = in.readString();
        this.comment_num = in.readInt();
        this.share = in.readInt();
        this.low_flv_size = in.readString();
        this.barrage_count = in.readInt();
        this.flv = in.readString();
        this.pia_tagid = in.readString();
        this.invite_msg = in.readString();
        this.duration = in.readString();
        this.pia_activity_id = in.readString();
        this.height = in.readInt();
        this.nickname = in.readString();
        this.src = in.readString();
        this.pv = in.readInt();
        this.title = in.readString();
        this.roomid = in.readInt();
        this.live = in.readInt();
        this.cover = in.readString();
        this.videoid = in.readString();
        this.share_num = in.readInt();
        this.width = in.readInt();
        this.comment_count = in.readInt();
        this.praise_num = in.readInt();
        this.pia_tagname = in.readString();
        this.algo = in.readParcelable(AlgoBean.class.getClassLoader());
        this.type = in.readInt();
        this.default_flv_size = in.readString();
        this.purl = in.readString();
    }

    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
        @Override
        public VideoInfo createFromParcel(Parcel source) {
            return new VideoInfo(source);
        }

        @Override
        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }
    };
}