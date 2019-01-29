package com.bw.movie.activity;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.FilmReviewAdapter;
import com.bw.movie.adapter.StagePhotoAdapter;
import com.bw.movie.adapter.YGAdapter;
import com.bw.movie.bean.FilmReviewBean;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.MoviesByIdBean;
import com.bw.movie.bean.MoviesDetailBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.FilmReviewPresenter;
import com.bw.movie.presenter.MoviesByIdPresenter;
import com.bw.movie.presenter.MoviesDetailPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;

public class MoviesByIdActivity extends WDActivity implements XRecyclerView.LoadingListener {
    private MoviesByIdPresenter moviesByIdPresenter;
    @BindView(R.id.xinxin)
    ImageView xinxin;
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
    private String id;
    private YGAdapter ygAdapter;
    private SimpleDraweeView simpleDraweeView1;
    private StagePhotoAdapter stagePhotoAdapter;
    private FilmReviewAdapter filmReviewAdapter;
    private int page = 1;
    private FilmReviewPresenter filmReviewPresenter;
    private XRecyclerView filmreview_recycler;
    private TextView popupwindow_detalis_daoyan;
    private TextView popupwindow_detalis_shichang;
    private TextView popupwindow_detalis_chandi;
    private TextView plot;
    private TextView popupwindow_detalis_leixing;
    private int id1;
    private String names;
    private String movieTypes;
    private String placeOrigin;
    private String duration;
    private String director;
    private String imageUrl;
    private String sessionId;
    private int userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movies_by_id;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //调用sp，获取userID和sessionid

        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        contentView2 = LayoutInflater.from(this).inflate(R.layout.item_yg, null);
        contentView3 = LayoutInflater.from(this).inflate(R.layout.item_stagephoto, null);
        contentView4 = LayoutInflater.from(this).inflate(R.layout.item_filmreview, null);

        bottomDialog = new Dialog(this, R.style.BottomDialog);

        simpleDraweeView1 = contentView.findViewById(R.id.popupwindow_detalis_sdvone);
        popupwindow_detalis_daoyan = contentView.findViewById(R.id.popupwindow_detalis_daoyan);
        popupwindow_detalis_shichang = contentView.findViewById(R.id.popupwindow_detalis_shichang);
        popupwindow_detalis_chandi = contentView.findViewById(R.id.popupwindow_detalis_chandi);
        popupwindow_detalis_leixing = contentView.findViewById(R.id.popupwindow_detalis_leixing);
        plot = contentView.findViewById(R.id.plot);
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
        contentView2.findViewById(R.id.yg_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        contentView3.findViewById(R.id.stagephoto_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        contentView4.findViewById(R.id.filmreview_finish).setOnClickListener(new View.OnClickListener() {
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

    @OnClick(R.id.goupay)
    public void goupay() {
        Intent intent = new Intent(this, CinemasListByMovieIdActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", names);
        intent.putExtra("director", director);
        intent.putExtra("duration", duration);
        intent.putExtra("placeOrigin", placeOrigin);
        intent.putExtra("movieTypes", movieTypes);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
    }

    @OnClick(R.id.moviesbyid_finish)
    public void moviesbyid_finish() {
        finish();
    }

    @OnClick(R.id.moviesbyid_xq)
    public void moviesbyid_xq() {
        show(contentView);
    }

    @OnClick(R.id.moviesbyid_yg)
    public void moviesbyid_yg() {
        show(contentView2);
    }

    @OnClick(R.id.moviesbyid_photo)
    public void moviesbyid_photo() {
        show(contentView3);
    }

    @OnClick(R.id.moviesbyid_filmreview)
    public void moviesbyid_filmreview() {
        show(contentView4);
    }

    private void show(View contentViewss) {
        bottomDialog.setContentView(contentViewss);
        ViewGroup.LayoutParams layoutParams = contentViewss.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentViewss.setLayoutParams(layoutParams);
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
            id1 = result.getId();
            names = result.getName();
            name.setText(names);
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
            imageUrl = result.getImageUrl();
            simpleDraweeView1.setImageURI(Uri.parse(imageUrl));
            director = result.getDirector();
            duration = result.getDuration();
            placeOrigin = result.getPlaceOrigin();
            movieTypes = result.getMovieTypes();
            popupwindow_detalis_daoyan.setText("导演：" + director);
            popupwindow_detalis_shichang.setText("时长：" + duration);
            popupwindow_detalis_chandi.setText("产地：" + placeOrigin);
            popupwindow_detalis_leixing.setText("产地：" + movieTypes);
            plot.setText(result.getSummary());
            List posterList = result.getPosterList();
            List<MoviesDetailBean> shortFilmList = (List<MoviesDetailBean>) result.getShortFilmList();
            ygAdapter.addItem(shortFilmList);
            ygAdapter.notifyDataSetChanged();
            stagePhotoAdapter.addItem(posterList);
            stagePhotoAdapter.notifyDataSetChanged();
            if (result.getFollowMovie() == 1) {
                xinxin.setImageResource(R.drawable.com_icon_collection_selected);
            } else {
                xinxin.setImageResource(R.drawable.weiguanzhu);
            }

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

    @Override
    public void onResume() {
        super.onResume();
        DaoSession daoSession = DaoMaster.newDevSession(MoviesByIdActivity.this, LoginSubBeanDao.TABLENAME);
        LoginSubBeanDao loginSubBeanDao = daoSession.getLoginSubBeanDao();
        List<LoginSubBean> list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            LoginSubBean loginSubBean = list.get(0);
            userId = loginSubBean.getId();
            sessionId = loginSubBean.getSessionId();
        }
    }
}
