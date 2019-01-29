package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemasListByMovieIdBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CinemasListByMovieIdAdapter extends RecyclerView.Adapter<CinemasListByMovieIdAdapter.ViewHolder> {
    List<CinemasListByMovieIdBean> list;
    private Context context;
    OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick {
        void click(String address, String name, int id);
    }

    public CinemasListByMovieIdAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cinemaslistbymovieid, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView1.setText(list.get(i).getName());
        viewHolder.textView2.setText(list.get(i).getAddress());
        viewHolder.simpleDraweeView.setImageURI(Uri.parse(list.get(i).getLogo()));
        final String address = list.get(i).getAddress();
        final String name = list.get(i).getName();
        final int id = list.get(i).getId();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.click(address, name,id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<CinemasListByMovieIdBean> result) {
        if (result != null) {
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView textView1;
        TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.movie_sim);
            textView2 = itemView.findViewById(R.id.movie_address_name);
            textView1 = itemView.findViewById(R.id.movie_name);
        }
    }
}
