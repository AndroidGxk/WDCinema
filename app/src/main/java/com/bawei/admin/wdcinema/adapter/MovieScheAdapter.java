package com.bawei.admin.wdcinema.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.admin.wdcinema.bean.MovieScheBean;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiaoping on 2017/3/28.
 */

public class MovieScheAdapter extends RecyclerView.Adapter<MovieScheAdapter.ViewHolder> {

    private Context mContext;
    List<MovieScheBean> list = new ArrayList<>();

    public MovieScheAdapter(Context c) {
        mContext = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cinemapay_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MovieScheBean movieScheBean = list.get(position);
        holder.pay_ting.setText(movieScheBean.getScreeningHall());
        holder.pay_starttime.setText(movieScheBean.getBeginTime());
        holder.pay_endtime.setText(movieScheBean.getEndTime());
        holder.pay_money.setText(movieScheBean.getPrice() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onclick(movieScheBean.getId(), position,
                            movieScheBean.getBeginTime() + "一" + movieScheBean.getEndTime(),
                            movieScheBean.getScreeningHall(), movieScheBean.getSeatsTotal(), movieScheBean.getPrice());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<MovieScheBean> result) {
        if (result != null) {
            list.addAll(result);
            notifyDataSetChanged();
        }
    }

    public void removeItem() {
        list.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView pay_ting;
        TextView pay_yuyan;
        TextView pay_starttime;
        TextView pay_endtime;
        TextView pay_money;
        ImageView go_image;

        public ViewHolder(View itemView) {
            super(itemView);
            pay_ting = itemView.findViewById(R.id.pay_ting);
            pay_yuyan = itemView.findViewById(R.id.pay_yuyan);
            pay_starttime = itemView.findViewById(R.id.pay_starttime);
            pay_endtime = itemView.findViewById(R.id.pay_endtime);
            pay_money = itemView.findViewById(R.id.pay_money);
            go_image = itemView.findViewById(R.id.go_image);
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onclick(int id, int position, String date, String name, int datetime, double money);
    }
}
