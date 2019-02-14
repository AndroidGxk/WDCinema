package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.FilmReviewBean;
import com.facebook.drawee.gestures.GestureDetector;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sackcentury.shinebuttonlib.ShineButton;

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
        void onclick(ShineButton like, int commentId, TextView text, int great);
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
        final FilmReviewBean filmReviewBean = list.get(i);
        viewHolder.textView1.setText(filmReviewBean.getCommentUserName());
        Date date = new Date();
        date.setTime(filmReviewBean.getCommentTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        viewHolder.textView2.setText(sdf.format(date));
        viewHolder.textView3.setText(filmReviewBean.getCommentContent());
        viewHolder.simpleDraweeView1.setImageURI(filmReviewBean.getCommentHeadPic());
        viewHolder.textView4.setText(String.valueOf(filmReviewBean.getGreatNum()));
        viewHolder.textView5.setText("共" + filmReviewBean.getReplyNum() + "条评论");
        viewHolder.textView6.setText(String.valueOf(filmReviewBean.getReplyNum()));
        if (filmReviewBean.getIsGreat() == 1 || filmReviewBean.isClick()) {
            if (filmReviewBean.isClick()) {
                int greatNum = filmReviewBean.getGreatNum();
                viewHolder.textView4.setText(String.valueOf(++greatNum));
            }
            viewHolder.like.setChecked(true);
        } else {
            viewHolder.like.setChecked(false);
        }
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewHolder.like.isChecked()) {
                    Toast.makeText(context, "不能重复点赞", Toast.LENGTH_SHORT).show();
                    viewHolder.like.setChecked(true);
                    return;
                }
                onClick.onclick(viewHolder.like, list.get(i).getCommentId(), viewHolder.textView4, list.get(i).getGreatNum());
                if (filmReviewBean.getIsGreat() == 0) {
                    filmReviewBean.setClick(true);
                } else {
                    filmReviewBean.setClick(false);
                }
            }
        });
        Log.i("GT", "getIsGreat:" + filmReviewBean.getIsGreat() + filmReviewBean.getCommentUserName());

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null)
                    onClickListener.onClickListener(filmReviewBean.getCommentId());
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
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView1;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        ImageView imageView;
        ShineButton like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView1 = itemView.findViewById(R.id.circle_iv1);
            textView1 = itemView.findViewById(R.id.circle_name);
            textView2 = itemView.findViewById(R.id.circle_time);
            textView3 = itemView.findViewById(R.id.circle_pl);
            textView4 = itemView.findViewById(R.id.tv);
            textView5 = itemView.findViewById(R.id.replyNum);
            textView6 = itemView.findViewById(R.id.tv2);
            like = itemView.findViewById(R.id.bt_like);
            imageView = itemView.findViewById(R.id.pl);
        }
    }

    onClickListener onClickListener;

    public void setOnClickListener(FilmReviewAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void onClickListener(int commentId);
    }

}
