package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.RecommBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/1/24 14:24
 * 邮箱：1724959985@qq.com
 */
public class TuiMovieRecycleAdapter extends RecyclerView.Adapter<TuiMovieRecycleAdapter.Vh> {
    List<RecommBean> recommList = new ArrayList<>();
    Context context;

    public TuiMovieRecycleAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<RecommBean> recommList) {
        if (recommList != null) {
            this.recommList.addAll(recommList);
            notifyDataSetChanged();
        }
    }

    public void removeAll() {
        recommList.clear();
        notifyDataSetChanged();
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView simpleDraweeView;
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
    public void onBindViewHolder(@NonNull final Vh vh, int i) {
        final RecommBean recommBean = recommList.get(i);
        vh.cinematextviewone.setText(recommBean.getName());
        final int followCinema = recommBean.getFollowCinema();
        if (followCinema == 1||recommBean.isGreate()) {
            vh.cinemasdvstwo.setImageResource(R.drawable.com_icon_collection_selected);
        } else {
            vh.cinemasdvstwo.setImageResource(R.drawable.weiguanzhu);
        }
        String logo = recommBean.getLogo();
        Glide.with(context).load(logo).into(vh.simpleDraweeView);
        vh.cinematextviewtwo.setText(recommBean.getAddress());
        vh.cinematextviewthree.setText(recommBean.getDistance() + "m");
        vh.cinematextviewtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onclick(recommBean.getId(), recommBean.getLogo(), recommBean.getName(), recommBean.getAddress());
            }
        });
        vh.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onclick(recommBean.getId(), recommBean.getLogo(), recommBean.getName(), recommBean.getAddress());
            }
        });
        vh.cinematextviewone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onclick(recommBean.getId(), recommBean.getLogo(), recommBean.getName(), recommBean.getAddress());
            }
        });
        vh.cinemasdvstwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenerAtte.onclick(recommBean.getId(), vh.cinemasdvstwo, recommBean.getFollowCinema());
                if (followCinema == 1) {
                    recommBean.setGreate(false);
                } else {
                    recommBean.setGreate(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommList.size();
    }


    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onclick(int id, String img, String name, String address);
    }

    private OnClickListenerAtte onClickListenerAtte;

    public void setOnClickListenerAtte(OnClickListenerAtte onClickListenerAtte) {
        this.onClickListenerAtte = onClickListenerAtte;
    }

    public interface OnClickListenerAtte {
        void onclick(int id, ImageView image, int followCinema);
    }
}
