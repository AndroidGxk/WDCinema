package com.bawei.admin.wdcinema.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.admin.wdcinema.bean.RecommBean;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/1/24 14:24
 * 邮箱：1724959985@qq.com
 */
public class TuiMovieRecycleAdapter extends RecyclerView.Adapter<TuiMovieRecycleAdapter.Vh> {
    List<RecommBean> recommList = new ArrayList<>();

    public void addAll(List<RecommBean> recommList) {
        if (recommList != null) {
            this.recommList.addAll(recommList);
        }
    }

    public void removeAll() {
        recommList.clear();
    }

    class Vh extends RecyclerView.ViewHolder {

        SimpleDraweeView simpleDraweeView;
        SimpleDraweeView cinemasdvstwo;
        TextView cinematextviewone;
        TextView cinematextviewtwo;
        TextView cinematextviewthree;

        public Vh(@NonNull View itemView) {
            super(itemView);
            cinemasdvstwo = itemView.findViewById(R.id.cinemasdvstwo);
            cinematextviewone = itemView.findViewById(R.id.cinematextviewone);
            cinematextviewtwo = itemView.findViewById(R.id.cinematextviewtwo);
            cinematextviewthree = itemView.findViewById(R.id.cinematextviewthree);
            simpleDraweeView = itemView.findViewById(R.id.cinemasdvsone);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cinemaadapter, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        RecommBean recommBean = recommList.get(i);
        vh.cinematextviewone.setText(recommBean.getName());
        vh.simpleDraweeView.setImageURI(recommBean.getLogo());
        vh.cinematextviewtwo.setText(recommBean.getAddress());
        vh.cinematextviewthree.setText(recommBean.getDistance() + "km");
    }

    @Override
    public int getItemCount() {
        return recommList.size();
    }


}
