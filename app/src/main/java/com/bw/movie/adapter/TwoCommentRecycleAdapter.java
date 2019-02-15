package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.FindCommentReply;
import com.bw.movie.bean.RecommBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/1/24 14:24
 * 邮箱：1724959985@qq.com
 */
public class TwoCommentRecycleAdapter extends RecyclerView.Adapter<TwoCommentRecycleAdapter.Vh> {
    List<FindCommentReply> commentReply = new ArrayList<>();
    Context context;

    public TwoCommentRecycleAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<FindCommentReply> commentReply) {
        if (commentReply != null) {
            this.commentReply.addAll(commentReply);
        }
    }

    public void removeAll() {
        commentReply.clear();
    }

    class Vh extends RecyclerView.ViewHolder {

        SimpleDraweeView com_circle_iv1;
        TextView com_circle_name;
        TextView com_circle_pl;
        TextView com_circle_time;

        public Vh(@NonNull View itemView) {
            super(itemView);
            com_circle_iv1 = itemView.findViewById(R.id.com_circle_iv1);
            com_circle_name = itemView.findViewById(R.id.com_circle_name);
            com_circle_pl = itemView.findViewById(R.id.com_circle_pl);
            com_circle_time = itemView.findViewById(R.id.com_circle_time);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        FindCommentReply findCommentReply = commentReply.get(i);
        vh.com_circle_iv1.setImageURI(findCommentReply.getReplyHeadPic());
        vh.com_circle_name.setText(findCommentReply.getReplyUserName());
        vh.com_circle_pl.setText(findCommentReply.getReplyContent());
        Date date = new Date();
        date.setTime(findCommentReply.getCommentTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String commdate = sdf.format(date);
        vh.com_circle_time.setText(commdate);
    }

    @Override
    public int getItemCount() {
        return commentReply.size();
    }


}
