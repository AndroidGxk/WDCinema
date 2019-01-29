package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.HotMovieBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class FilmShowAdapter extends RecyclerView.Adapter<FilmShowAdapter.ViewHolder> {
    private Context context;
    List<HotMovieBean> list = new ArrayList<>();

    public FilmShowAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filmshow, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.summary.setText(list.get(i).getSummary());
        viewHolder.simpleDraweeView.setImageURI(Uri.parse(list.get(i).getImageUrl()));
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(list.get(i).getId());
            }
        });
        viewHolder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(list.get(i).getId());
            }
        });
        viewHolder.summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(list.get(i).getId());
            }
        });
        if (list.get(i).getFollowMovie() == 1) {
            viewHolder.imageView.setImageResource(R.drawable.com_icon_collection_selected);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.weiguanzhu);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenerAtt.onClick(list.get(i).getId(), viewHolder.imageView,list.get(i).getFollowMovie());
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

    public void remove() {
        list.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView name;
        ImageView imageView;
        TextView summary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.filmshow_sim);
            name = itemView.findViewById(R.id.filmshow_name);
            imageView = itemView.findViewById(R.id.filmshow_heart);
            summary = itemView.findViewById(R.id.filmshow_summary);
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int id);
    }

    private OnClickListenerAtt onClickListenerAtt;

    public void setOnClickListenerAtt(OnClickListenerAtt onClickListenerAtt) {
        this.onClickListenerAtt = onClickListenerAtt;
    }

    public interface OnClickListenerAtt {
        void onClick(int id, ImageView imageView,int followCinema);
    }
}
