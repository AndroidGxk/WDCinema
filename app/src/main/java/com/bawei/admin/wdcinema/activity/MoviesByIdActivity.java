package com.bawei.admin.wdcinema.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.admin.wdcinema.adapter.StagePhotoAdapter;
import com.bawei.admin.wdcinema.adapter.YGAdapter;
import com.bawei.admin.wdcinema.bean.MoviesByIdBean;
import com.bawei.admin.wdcinema.bean.MoviesDetailBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.MoviesByIdPresenter;
import com.bawei.admin.wdcinema.presenter.MoviesDetailPresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import me.jessyan.autosize.internal.CustomAdapt;

public class MoviesByIdActivity extends AppCompatActivity implements CustomAdapt {
    private SharedPreferences sp;
    private MoviesByIdPresenter moviesByIdPresenter;

    @BindView(R.id.moviesbyid_name)
    TextView name;
    @BindView(R.id.moviesbyid_sim)
    SimpleDraweeView simpleDraweeView;
    private View contentView;
    private View contentView2;
    private View contentView3;
    private Dialog bottomDialog;
    private MoviesDetailPresenter moviesDetailPresenter;
    private String sessionId;
    private int userId;
    private String id;
    private YGAdapter ygAdapter;
    private SimpleDraweeView simpleDraweeView1;
    private StagePhotoAdapter stagePhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_by_id);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //调用sp，获取userID和sessionid
        sp = getSharedPreferences("login", MODE_PRIVATE);

        sessionId = sp.getString("sessionId", "1");
        userId = sp.getInt("userId", 1);

        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        contentView2 = LayoutInflater.from(this).inflate(R.layout.item_yg, null);
        contentView3 = LayoutInflater.from(this).inflate(R.layout.item_stagephoto, null);

        bottomDialog = new Dialog(this, R.style.BottomDialog);

        simpleDraweeView1 = contentView.findViewById(R.id.popupwindow_detalis_sdvone);
        moviesDetailPresenter = new MoviesDetailPresenter(new MoviesDetail());

        moviesByIdPresenter = new MoviesByIdPresenter(new MoviesById());
        moviesByIdPresenter.request(userId, sessionId, Integer.parseInt(id));

        RecyclerView recyclerView = contentView2.findViewById(R.id.yg_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ygAdapter = new YGAdapter(this);
        recyclerView.setAdapter(ygAdapter);

        RecyclerView stagePhoto_recycler = contentView3.findViewById(R.id.stagephoto_recycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        stagePhoto_recycler.setLayoutManager(staggeredGridLayoutManager);
        stagePhotoAdapter = new StagePhotoAdapter(this);
        stagePhoto_recycler.setAdapter(stagePhotoAdapter);

        moviesDetailPresenter.request(userId, sessionId, Integer.parseInt(id));

        contentView.findViewById(R.id.popupwindow_detalis_sdvtwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

    }

    @OnClick(R.id.moviesbyid_finish)
    public void moviesbyid_finish() {
        finish();
    }

    @OnClick(R.id.moviesbyid_xq)
    public void moviesbyid_xq() {
        show1();
    }

    @OnClick(R.id.moviesbyid_yg)
    public void moviesbyid_yg() {
        show2();
    }
    @OnClick(R.id.moviesbyid_photo)
    public void moviesbyid_photo() {
        show3();
    }

    private void show1() {
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private void show2() {
        bottomDialog.setContentView(contentView2);
        ViewGroup.LayoutParams layoutParams = contentView2.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView2.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }
    private void show3() {
        bottomDialog.setContentView(contentView3);
        ViewGroup.LayoutParams layoutParams = contentView3.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView3.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class MoviesById implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            MoviesByIdBean result = (MoviesByIdBean) data.getResult();
            name.setText(result.getName());
            simpleDraweeView.setImageURI(Uri.parse(result.getImageUrl()));
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class MoviesDetail implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            MoviesByIdBean result = (MoviesByIdBean) data.getResult();
            simpleDraweeView1.setImageURI(Uri.parse(result.getImageUrl()));
            List posterList = result.getPosterList();
            List<MoviesDetailBean> shortFilmList = (List<MoviesDetailBean>) result.getShortFilmList();
            ygAdapter.addItem(shortFilmList);
            ygAdapter.notifyDataSetChanged();
            stagePhotoAdapter.addItem(posterList);
            stagePhotoAdapter.notifyDataSetChanged();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
