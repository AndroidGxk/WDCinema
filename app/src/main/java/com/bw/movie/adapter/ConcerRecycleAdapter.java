package com.bw.movie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/1/25 09:08
 * 邮箱：1724959985@qq.com
 */
public class ConcerRecycleAdapter extends RecyclerView.Adapter<ConcerRecycleAdapter.Vh> {
    List<MovieListBean> movieList = new ArrayList<>();

    public void addAll(List<MovieListBean> movieList) {
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.movieList.clear();
        notifyDataSetChanged();
    }

    class Vh extends RecyclerView.ViewHolder {

        SimpleDraweeView cinemasdvsone;
        TextView cinematextviewone;
        TextView cinematextviewtwo;
        TextView cinematextviewthree;

        public Vh(@NonNull View itemView) {
            super(itemView);
            cinemasdvsone = itemView.findViewById(R.id.cinemasdvsone);
            cinematextviewone = itemView.findViewById(R.id.cinematextviewone);
            cinematextviewtwo = itemView.findViewById(R.id.cinematextviewtwo);
            cinematextviewthree = itemView.findViewById(R.id.cinematextviewthree);
        }
    }


    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movielist, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        final MovieListBean movieListBean = movieList.get(i);
        vh.cinemasdvsone.setImageURI(movieListBean.getImageUrl());
        vh.cinematextviewone.setText(movieListBean.getName());
        vh.cinematextviewtwo.setText(movieListBean.getSummary());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date.setTime(movieListBean.getReleaseTime());
        String Datetime = format.format(date);
        vh.cinematextviewthree.setText(Datetime);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onClick(movieListBean.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface Onclick {
        void onClick(int id);
    }

    private Onclick onclick;

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }
}
