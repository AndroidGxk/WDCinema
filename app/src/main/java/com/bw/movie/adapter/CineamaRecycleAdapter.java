package com.bw.movie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaPageList;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/1/25 09:08
 * 邮箱：1724959985@qq.com
 */
public class CineamaRecycleAdapter extends RecyclerView.Adapter<CineamaRecycleAdapter.Vh> {
    List<CinemaPageList> movieList = new ArrayList<>();

    public void addAll(List<CinemaPageList> movieList) {
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

        public Vh(@NonNull View itemView) {
            super(itemView);
            cinemasdvsone = itemView.findViewById(R.id.cinemasdvsone);
            cinematextviewone = itemView.findViewById(R.id.cinematextviewone);
            cinematextviewtwo = itemView.findViewById(R.id.cinematextviewtwo);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cineamlist, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, final int i) {
        CinemaPageList cinemaPageList = movieList.get(i);
        vh.cinemasdvsone.setImageURI(cinemaPageList.getLogo());
        vh.cinematextviewone.setText(cinemaPageList.getName());
        vh.cinematextviewtwo.setText(cinemaPageList.getAddress());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onClick(movieList.get(i).getId(), movieList.get(i).getLogo(), movieList.get(i).getName(), movieList.get(i).getAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface Onclick {
        void onClick(int id, String logo, String name, String address);
    }

    private Onclick onclick;

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }

}
