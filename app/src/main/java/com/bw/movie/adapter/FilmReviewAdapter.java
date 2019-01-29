package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.FilmReviewBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilmReviewAdapter extends RecyclerView.Adapter<FilmReviewAdapter.ViewHolder> {
    private Context context;
    List<FilmReviewBean> list;
    OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick {
        void onclick(ImageView like, int commentId);
    }

    public FilmReviewAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filmreview_child, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        FilmReviewBean filmReviewBean = list.get(i);
        viewHolder.textView1.setText(filmReviewBean.getCommentUserName());
        Date date = new Date();
        date.setTime(filmReviewBean.getCommentTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        viewHolder.textView2.setText(sdf.format(date));
        viewHolder.textView3.setText(filmReviewBean.getCommentContent());
        viewHolder.simpleDraweeView1.setImageURI(filmReviewBean.getCommentHeadPic());
        viewHolder.textView4.setText(String.valueOf(filmReviewBean.getReplyNum()));
        viewHolder.textView5.setText("共" + filmReviewBean.getGreatNum() + "条评论");
        viewHolder.textView6.setText(String.valueOf(filmReviewBean.getGreatNum()));
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onclick(viewHolder.like, list.get(i).getCommentId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<FilmReviewBean> result) {
        if (result != null) {
            list.addAll(result);
        }
    }

    public void remove() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView1;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        ImageView like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView1 = itemView.findViewById(R.id.circle_iv1);
            textView1 = itemView.findViewById(R.id.circle_name);
            textView2 = itemView.findViewById(R.id.circle_time);
            textView3 = itemView.findViewById(R.id.circle_pl);
            textView4 = itemView.findViewById(R.id.tv);
            textView5 = itemView.findViewById(R.id.replyNum);
            textView6 = itemView.findViewById(R.id.tv2);
            like = itemView.findViewById(R.id.like);
        }
    }
}
