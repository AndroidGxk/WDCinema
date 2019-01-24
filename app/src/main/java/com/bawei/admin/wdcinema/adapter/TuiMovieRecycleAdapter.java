package com.bawei.admin.wdcinema.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.admin.wdcinema.bean.RecommBean;

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

    class Vh extends RecyclerView.ViewHolder {

        public Vh(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
