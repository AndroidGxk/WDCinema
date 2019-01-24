package com.bawei.admin.wdcinema.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.admin.wdcinema.bean.HotMovieBean;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class ComingSoonMovieAdapter extends RecyclerView.Adapter<ComingSoonMovieAdapter.ViewHolder> {
    private Context context;
    List<HotMovieBean> list = new ArrayList<>();

    public ComingSoonMovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotmovie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setBackgroundColor(0x55000000);
        viewHolder.textView.setText(list.get(i).getName());
        viewHolder.imageView.setImageURI(Uri.parse(list.get(i).getImageUrl()));
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}