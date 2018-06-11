package com.thatnight.videoplayer.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thatnight.videoplayer.R;
import com.thatnight.videoplayer.entity.VideoInfo;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private List<VideoInfo> mVideoInfoList;

    public VideoListAdapter() {
        super();
        mVideoInfoList = new ArrayList<>();
    }

    public void refreshData(List<VideoInfo> videoInfoList) {
        mVideoInfoList.clear();
        if (videoInfoList == null) {
            return;
        }
        mVideoInfoList.addAll(videoInfoList);
        notifyDataSetChanged();
    }

    public void loadMoreData(List<VideoInfo> videoInfoList) {
        if (videoInfoList == null) {
            return;
        }
        mVideoInfoList.addAll(videoInfoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_info, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = mVideoInfoList.get(position).getCover();
        if (holder == null || TextUtils.isEmpty(url)) {
            return;
        }
        holder.bindView(mVideoInfoList.get(position));
    }


    @Override
    public int getItemCount() {
        return mVideoInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImg;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickRecyclerViewListener != null) {
                        mOnClickRecyclerViewListener.onItemClick(getLayoutPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnClickRecyclerViewListener != null) {
                        mOnClickRecyclerViewListener.onItemLongClick(getLayoutPosition());
                        return true;
                    }
                    return false;
                }
            });
            coverImg = itemView.findViewById(R.id.iv_item_video);
            title = itemView.findViewById(R.id.tv_item_video);
        }

        public void bindView(VideoInfo videoInfo) {
            RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_foreground);
            Glide.with(coverImg).load(videoInfo.getCover()).apply(options).into(coverImg);
            title.setText(videoInfo.getTitle());
        }
    }

    private OnClickRecyclerViewListener mOnClickRecyclerViewListener;

    public void setOnClickRecyclerViewListener(OnClickRecyclerViewListener onClickRecyclerViewListener) {
        mOnClickRecyclerViewListener = onClickRecyclerViewListener;
    }

    public interface OnClickRecyclerViewListener {
        void onItemClick(int pos);

        void onItemLongClick(int pos);
    }
}

