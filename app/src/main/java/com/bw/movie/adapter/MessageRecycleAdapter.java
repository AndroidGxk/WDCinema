package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.MoviesByIdActivity;
import com.bw.movie.bean.HotMovieBean;
import com.bw.movie.bean.MeassageListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/1/30 10:12
 * 邮箱：1724959985@qq.com
 */
public class MessageRecycleAdapter extends RecyclerView.Adapter<MessageRecycleAdapter.ViewHolder> {
    List<MeassageListBean> list = new ArrayList<>();

    public void addItem(List<MeassageListBean> result) {
        if (result != null) {
            list.addAll(result);
            notifyDataSetChanged();
        }
    }

    public void RemoveItem() {
        list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MeassageListBean meassageListBean = list.get(i);
        viewHolder.mes_title.setText(meassageListBean.getTitle());
        viewHolder.mes_mes.setText(meassageListBean.getContent());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date.setTime(meassageListBean.getPushTime());
        String Datetime = format.format(date);
        viewHolder.mes_time.setText(Datetime);
        if (meassageListBean.getStatus() == 0) {
            viewHolder.mes_num.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mes_num.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageStatus != null) {
                    messageStatus.messageStatus(meassageListBean.getId());
                    sum();
                }
            }
        });
        sum();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mes_title;
        TextView mes_time;
        TextView mes_mes;
        TextView mes_num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mes_title = itemView.findViewById(R.id.mes_title);
            mes_time = itemView.findViewById(R.id.mes_time);
            mes_mes = itemView.findViewById(R.id.mes_mes);
            mes_num = itemView.findViewById(R.id.mes_num);
        }
    }

    //接口
    private TotalPriceListener totalPriceListener;

    public void setTotalPriceListener(TotalPriceListener totalPriceListener) {
        this.totalPriceListener = totalPriceListener;
    }

    //未读消息
    private void sum() {
        int totalmessage = 0;

        for (int i = 0; i < list.size(); i++) {
            int status = list.get(i).getStatus();
            if (status == 0) {
                ++totalmessage;
            }
        }
        //给未读消息接口设置值
        if (totalPriceListener != null) {
            totalPriceListener.totalPrice(totalmessage);
        }
    }

    //内部类接口
    public interface TotalPriceListener {
        void totalPrice(int totalPrice);
    }

    messageStatus messageStatus;

    public void setMessageStatus(MessageRecycleAdapter.messageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    //状态改变接口
    public interface messageStatus {
        void messageStatus(int id);
    }
}
