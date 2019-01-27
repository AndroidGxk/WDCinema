package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.MoviesDetailBean;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;


public class YGAdapter extends RecyclerView.Adapter<YGAdapter.ViewHolder> {
    List<MoviesDetailBean> list;
    private Context context;

    public YGAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yg_recycler, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.e("asdasdaaaaa", "------------" + list.get(i).getVideoUrl());
        viewHolder.jzVideoPlayerStandard.setUp(list.get(i).getVideoUrl(),
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        Glide.with(context).load(list.get(i).getImageUrl()).into(viewHolder.jzVideoPlayerStandard.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<MoviesDetailBean> shortFilmList) {
        if (shortFilmList != null) {
            list.addAll(shortFilmList);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandard jzVideoPlayerStandard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jzVideoPlayerStandard = itemView.findViewById(R.id.videoplayer);
        }
    }
}
