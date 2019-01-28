package com.bw.movie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.UserTicketBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/1/28 14:55
 * 邮箱：1724959985@qq.com
 */
public class UserTicketRecycleAdapter extends RecyclerView.Adapter<UserTicketRecycleAdapter.Vh> {
    List<UserTicketBean> ticketList = new ArrayList<>();

    public void addAll(List<UserTicketBean> ticketList) {
        this.ticketList.addAll(ticketList);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.ticketList.clear();
        notifyDataSetChanged();
    }

    class Vh extends RecyclerView.ViewHolder {
        TextView title;
        Button fukuan;
        TextView two_dingdan;
        TextView two_film;
        TextView two_yingting;
        TextView two_time;
        TextView two_count;
        TextView two_money;

        public Vh(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.two_title);
            fukuan = itemView.findViewById(R.id.fukuan);
            two_dingdan = itemView.findViewById(R.id.two_dingdan);
            two_film = itemView.findViewById(R.id.two_film);
            two_yingting = itemView.findViewById(R.id.two_yingting);
            two_time = itemView.findViewById(R.id.two_time);
            two_count = itemView.findViewById(R.id.two_count);
            two_money = itemView.findViewById(R.id.two_money);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_ticket, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        final UserTicketBean userTicketBean = ticketList.get(i);
        vh.title.setText(userTicketBean.getMovieName());
        vh.two_dingdan.setText("订单号：" + userTicketBean.getOrderId());
        vh.two_film.setText("影院：" + userTicketBean.getCinemaName());
        vh.two_yingting.setText("影厅：" + userTicketBean.getScreeningHall());
        vh.two_time.setText("时间：" + userTicketBean.getBeginTime() + "-" + userTicketBean.getEndTime());
        vh.two_count.setText("数量：" + userTicketBean.getAmount());
        vh.two_money.setText("金额：" + userTicketBean.getPrice());
        vh.fukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onclick(userTicketBean.getId(), userTicketBean.getOrderId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onclick(int id, String oderId);
    }
}
