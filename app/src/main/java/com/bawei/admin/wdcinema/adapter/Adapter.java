package com.bawei.admin.wdcinema.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.admin.wdcinema.bean.HotMovieBean;
import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiaoping on 2017/3/28.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context mContext;
    onItemClick clickCb;
    List<HotMovieBean> list = new ArrayList<>();

    public Adapter(Context c, onItemClick cb) {
        mContext = c;
        clickCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_item_banner, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext).load(list.get(position).getImageUrl())
                .into(holder.img);
        holder.tv.setBackgroundColor(0x55000000);
        holder.tv.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "点击了："+position, Toast.LENGTH_SHORT).show();
                if (clickCb != null) {
                    clickCb.clickItem(list.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<HotMovieBean> result) {
        if (result != null) {
            list.addAll(result);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public interface onItemClick {
        void clickItem(int id);
    }
}
