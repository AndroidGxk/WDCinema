package com.bw.movie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CineamComListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenxiaoping on 2017/3/28.
 */

public class CinemaComListAdapter extends RecyclerView.Adapter<CinemaComListAdapter.ViewHolder> {

    List<CineamComListBean> list = new ArrayList<>();

    public void setList(List<CineamComListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void RemoveAll() {
        this.list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cinemacom, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CineamComListBean cineamComListBean = list.get(i);
        viewHolder.circle_iv1.setImageURI(cineamComListBean.getCommentHeadPic());
        viewHolder.circle_pl.setText(cineamComListBean.getCommentContent());
        viewHolder.circle_name.setText(cineamComListBean.getCommentUserName());
        viewHolder.tv.setText(cineamComListBean.getGreatNum() + "");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date.setTime(cineamComListBean.getCommentTime());
        String Datetime = format.format(date);
        viewHolder.circle_time.setText(Datetime);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView circle_iv1;
        TextView circle_pl;
        TextView circle_time;
        ImageView like;
        TextView tv;
        TextView circle_name;

        public ViewHolder(View itemView) {
            super(itemView);
            circle_iv1 = itemView.findViewById(R.id.circle_iv1);
            circle_pl = itemView.findViewById(R.id.circle_pl);
            circle_time = itemView.findViewById(R.id.circle_time);
            circle_name = itemView.findViewById(R.id.circle_name);
            like = itemView.findViewById(R.id.like);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
