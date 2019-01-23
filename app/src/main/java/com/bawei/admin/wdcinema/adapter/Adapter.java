package com.bawei.admin.wdcinema.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.admin.wdcinema.R;
import com.bumptech.glide.Glide;

/**
 * Created by chenxiaoping on 2017/3/28.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context mContext;
    private int[] mColors = {R.drawable.baobeier, R.drawable.hutaojiazi};
    private String[] name = {"宝贝儿", "胡桃夹子与四个王国"};
    private onItemClick clickCb;

    public Adapter(Context c) {
        mContext = c;
    }

    public Adapter(Context c, onItemClick cb) {
        mContext = c;
        clickCb = cb;
    }

    public void setOnClickLstn(onItemClick cb) {
        this.clickCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_item_banner, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext).load(mColors[position % mColors.length])
                .into(holder.img);
        holder.tv.setBackgroundColor(0x55000000);
        holder.tv.setText(name[position % name.length]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "点击了："+position, Toast.LENGTH_SHORT).show();
                if (clickCb != null) {
                    clickCb.clickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public interface onItemClick {
        void clickItem(int pos);
    }
}
