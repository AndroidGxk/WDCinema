package com.bawei.admin.wdcinema.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.admin.wdcinema.adapter.FilmReviewAdapter;
import com.bawei.admin.wdcinema.adapter.StagePhotoAdapter;
import com.bawei.admin.wdcinema.adapter.YGAdapter;
import com.bawei.admin.wdcinema.bean.FilmReviewBean;
import com.bawei.admin.wdcinema.bean.MoviesByIdBean;
import com.bawei.admin.wdcinema.bean.MoviesDetailBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.FilmReviewPresenter;
import com.bawei.admin.wdcinema.presenter.MoviesByIdPresenter;
import com.bawei.admin.wdcinema.presenter.MoviesDetailPresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;

public class MoviesByIdActivity extends WDActivity implements XRecyclerView.LoadingListener {
    private SharedPreferences sp;
    private MoviesByIdPresenter moviesByIdPresenter;

    @BindView(R.id.moviesbyid_name)
    TextView name;
    @BindView(R.id.moviesbyid_sim)
    SimpleDraweeView simpleDraweeView;
    private View contentView;
    private View contentView2;
    private View contentView3;
    private View contentView4;
    private Dialog bottomDialog;
    private MoviesDetailPresenter moviesDetailPresenter;
    private String sessionId;
    private int userId;
    private String id;
    private YGAdapter ygAdapter;
    private SimpleDraweeView simpleDraweeView1;
    private StagePhotoAdapter stagePhotoAdapter;
    private FilmReviewAdapter filmReviewAdapter;
    private int page = 1;
    private FilmReviewPresenter filmReviewPresenter;
    private XRecyclerView filmreview_recycler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movies_by_id;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //调用sp，获取userID和sessionid
        sp = getSharedPreferences("login", MODE_PRIVATE);

        sessionId = sp.getString("sessionId", "1");
        userId = sp.getInt("userId", 1);

        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        contentView2 = LayoutInflater.from(this).inflate(R.layout.item_yg, null);
        contentView3 = LayoutInflater.from(this).inflate(R.layout.item_stagephoto, null);
        contentView4 = LayoutInflater.from(this).inflate(R.layout.item_filmreview, null);

        bottomDialog = new Dialog(this, R.style.BottomDialog);

        simpleDraweeView1 = contentView.findViewById(R.id.popupwindow_detalis_sdvone);
        moviesDetailPresenter = new MoviesDetailPresenter(new MoviesDetail());

        moviesByIdPresenter = new MoviesByIdPresenter(new MoviesById());
        moviesByIdPresenter.request(userId, sessionId, Integer.parseInt(id));

        filmReviewPresenter = new FilmReviewPresenter(new FilmReview());
        filmReviewPresenter.request(Integer.parseInt(id), 1, 5);

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

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        filmreview_recycler = contentView4.findViewById(R.id.filmreview_recycler);
        filmreview_recycler.setLayoutManager(linearLayoutManager1);
        filmReviewAdapter = new FilmReviewAdapter(this);
        filmreview_recycler.setAdapter(filmReviewAdapter);

        filmreview_recycler.setLoadingListener(this);
        filmreview_recycler.setLoadingMoreEnabled(true);
        filmreview_recycler.setPullRefreshEnabled(true);

        moviesDetailPresenter.request(userId, sessionId, Integer.parseInt(id));

        contentView.findViewById(R.id.popupwindow_detalis_sdvtwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
    }

    @Override
    protected void destoryData() {
        moviesDetailPresenter.unBind();
        filmReviewPresenter.unBind();
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

    @OnClick(R.id.moviesbyid_filmreview)
    public void moviesbyid_filmreview() {
        show4();
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

    private void show4() {
        bottomDialog.setContentView(contentView4);
        ViewGroup.LayoutParams layoutParams = contentView4.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView4.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }


    //上下拉
    @Override
    public void onRefresh() {
        page = 1;
        filmReviewAdapter.remove();
        filmReviewPresenter.request(Integer.parseInt(id), page, 5);
    }

    @Override
    public void onLoadMore() {
        page++;
        filmReviewPresenter.request(Integer.parseInt(id), page, 5);
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

    private class FilmReview implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<FilmReviewBean> result = (List<FilmReviewBean>) data.getResult();
            filmReviewAdapter.addItem(result);
            filmReviewAdapter.notifyDataSetChanged();
            filmreview_recycler.loadMoreComplete();
            filmreview_recycler.refreshComplete();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }
}
