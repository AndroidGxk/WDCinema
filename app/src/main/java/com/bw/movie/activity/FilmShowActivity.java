package com.bw.movie.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.FilmShowAdapter;
import com.bw.movie.bean.HotMovieBean;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.ComingSoonMoviePresenter;
import com.bw.movie.presenter.HotMoviePresenter;
import com.bw.movie.presenter.MovieAttListPresenter;
import com.bw.movie.presenter.MovieCancelListPresenter;
import com.bw.movie.presenter.ReleaseMoviePresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FilmShowActivity extends WDActivity implements XRecyclerView.LoadingListener {
    private boolean animatort = false;
    private boolean animatorf = false;
    private boolean hotcheck = false;
    private boolean releasecheck = false;
    private boolean comingSooncheck = false;
    @BindView(R.id.hot_show)
    Button hot;
    @BindView(R.id.release_show)
    Button release;
    @BindView(R.id.comingSoon_show)
    Button comingSoon;
    @BindView(R.id.filmshow_recycler)
    XRecyclerView filmshow_recycler;
    @BindView(R.id.cimema_text)
    TextView textView;
    private HotMoviePresenter hotMoviePresenter;
    private ReleaseMoviePresenter releaseMoviePresenter;
    private ComingSoonMoviePresenter comingSoonMoviePresenter;
    private int page = 1;
    private FilmShowAdapter filmShowAdapter;
    @BindView(R.id.seacrch_linear2)
    LinearLayout seacrch_linear2;
    private MovieAttListPresenter movieAttListPresenter;
    ImageView imageView;
    private MovieCancelListPresenter movieCancelListPresenter;
    private int userId;
    private String sessionId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_show;
    }

    @Override
    protected void initView() {
        String getdz = WDActivity.getdz();
        textView.setText(getdz);
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 530f);
        animator.setDuration(0);
        animator.start();
        DaoSession daoSession = DaoMaster.newDevSession(FilmShowActivity.this, LoginSubBeanDao.TABLENAME);
        LoginSubBeanDao loginSubBeanDao = daoSession.getLoginSubBeanDao();
        List<LoginSubBean> list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            LoginSubBean loginSubBean = list.get(0);
            userId = loginSubBean.getId();
            sessionId = loginSubBean.getSessionId();
        }
        final Intent intent = getIntent();
        String select = intent.getStringExtra("select");
        hotMoviePresenter = new HotMoviePresenter(new Hot());
        releaseMoviePresenter = new ReleaseMoviePresenter(new Release());
        comingSoonMoviePresenter = new ComingSoonMoviePresenter(new ComingSoon());
        filmshow_recycler.setLoadingListener(this);
        filmshow_recycler.setLoadingMoreEnabled(true);
        filmshow_recycler.setPullRefreshEnabled(true);
        //点赞接口
        movieAttListPresenter = new MovieAttListPresenter(new MovieAtt());
        //取消点赞
        movieCancelListPresenter = new MovieCancelListPresenter(new MovieCan());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        filmShowAdapter = new FilmShowAdapter(this);
        filmshow_recycler.setLayoutManager(manager);
        filmshow_recycler.setAdapter(filmShowAdapter);
        if (select.equals("1")) {
            hotcheck = true;
            hot.setBackgroundResource(R.drawable.btn_gradient);
            release.setBackgroundResource(R.drawable.btn_false);
            comingSoon.setBackgroundResource(R.drawable.btn_false);
            filmShowAdapter.remove();
            hotMoviePresenter.request(userId, sessionId, page, 5);
        } else if (select.equals("2")) {
            releasecheck = true;
            release.setBackgroundResource(R.drawable.btn_gradient);
            hot.setBackgroundResource(R.drawable.btn_false);
            comingSoon.setBackgroundResource(R.drawable.btn_false);
            filmShowAdapter.remove();
            releaseMoviePresenter.request(userId, sessionId, page, 5);
        } else {
            comingSooncheck = true;
            comingSoon.setBackgroundResource(R.drawable.btn_gradient);
            hot.setBackgroundResource(R.drawable.btn_false);
            release.setBackgroundResource(R.drawable.btn_false);
            filmShowAdapter.remove();
            comingSoonMoviePresenter.request(userId, sessionId, page, 5);
        }
        filmShowAdapter.setOnClickListener(new FilmShowAdapter.OnClickListener() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(FilmShowActivity.this, MoviesByIdActivity.class);
                intent.putExtra("id", id + "");
                startActivity(intent);
            }
        });
        filmShowAdapter.setOnClickListenerAtt(new FilmShowAdapter.OnClickListenerAtt() {
            @Override
            public void onClick(int id, ImageView imag, int followCinema) {
                imageView = imag;
                if (followCinema == 2) {
                    movieAttListPresenter.request(userId, sessionId, id);
                } else {
                    movieCancelListPresenter.request(userId, sessionId, id);
                }
            }
        });
    }


    @Override
    protected void destoryData() {
        releaseMoviePresenter.unBind();
        hotMoviePresenter.unBind();
        comingSoonMoviePresenter.unBind();
    }

    @OnClick(R.id.imageView)
    public void seacrch_linear2() {
        if (animatort) {
            return;
        }
        animatort = true;
        animatorf = false;
        //这是显示出现的动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 530f, 30f);
        animator.setDuration(1000);
        animator.start();
    }

    @OnClick(R.id.seacrch_text)
    public void seacrch_text() {
        if (animatorf) {
            return;
        }
        animatorf = true;
        animatort = false;
        //这是隐藏进去的动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 530f);
        animator.setDuration(1000);
        animator.start();
    }

    //点击事件
    @OnClick(R.id.hot_show)
    public void hot() {
        if (hotcheck) {
            return;
        }
        hotcheck = true;
        page = 1;
        if (hotcheck) {
            hot.setBackgroundResource(R.drawable.btn_gradient);
            releasecheck = false;
            comingSooncheck = false;
            release.setBackgroundResource(R.drawable.btn_false);
            comingSoon.setBackgroundResource(R.drawable.btn_false);
            filmShowAdapter.remove();
            hotMoviePresenter.request(userId, sessionId, page, 5);
        }
    }

    @OnClick(R.id.release_show)
    public void release() {
        if (releasecheck) {
            return;
        }
        releasecheck = true;
        page = 1;
        if (releasecheck) {
            release.setBackgroundResource(R.drawable.btn_gradient);
            hotcheck = false;
            comingSooncheck = false;
            hot.setBackgroundResource(R.drawable.btn_false);
            comingSoon.setBackgroundResource(R.drawable.btn_false);
            filmShowAdapter.remove();
            releaseMoviePresenter.request(userId, sessionId, page, 5);
        }
    }

    @OnClick(R.id.comingSoon_show)
    public void comingSoon() {
        if (comingSooncheck) {
            return;
        }
        comingSooncheck = true;
        page = 1;
        if (comingSooncheck) {
            comingSoon.setBackgroundResource(R.drawable.btn_gradient);
            releasecheck = false;
            hotcheck = false;
            hot.setBackgroundResource(R.drawable.btn_false);
            release.setBackgroundResource(R.drawable.btn_false);
            filmShowAdapter.remove();
            comingSoonMoviePresenter.request(userId, sessionId, page, 5);
        }
    }

    //上下拉
    @Override
    public void onRefresh() {
        page = 1;
        filmShowAdapter.remove();
        if (hotcheck) {
            hotMoviePresenter.request(userId, sessionId, page, 5);
        } else if (releasecheck) {
            releaseMoviePresenter.request(userId, sessionId, page, 5);
        } else {
            comingSoonMoviePresenter.request(userId, sessionId, page, 5);
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        if (hotcheck) {
            hotMoviePresenter.request(userId, sessionId, page, 5);
        } else if (releasecheck) {
            releaseMoviePresenter.request(userId, sessionId, page, 5);
        } else {
            comingSoonMoviePresenter.request(userId, sessionId, page, 5);
        }
    }

    private class Hot implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<HotMovieBean> result = (List<HotMovieBean>) data.getResult();
            filmShowAdapter.addItem(result);
            filmShowAdapter.notifyDataSetChanged();
            filmshow_recycler.loadMoreComplete();
            filmshow_recycler.refreshComplete();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class Release implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<HotMovieBean> result = (List<HotMovieBean>) data.getResult();
            filmShowAdapter.addItem(result);
            filmShowAdapter.notifyDataSetChanged();
            filmshow_recycler.loadMoreComplete();
            filmshow_recycler.refreshComplete();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class ComingSoon implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<HotMovieBean> result = (List<HotMovieBean>) data.getResult();
            filmShowAdapter.addItem(result);
            filmShowAdapter.notifyDataSetChanged();
            filmshow_recycler.loadMoreComplete();
            filmshow_recycler.refreshComplete();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    //电影点赞
    private class MovieAtt implements ResultInfe<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(FilmShowActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                imageView.setImageResource(R.drawable.com_icon_collection_selected);
            }
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    //电影取消点赞
    private class MovieCan implements ResultInfe {

        @Override
        public void success(Object data) {
            Result result = (Result) data;
            Toast.makeText(FilmShowActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                imageView.setImageResource(R.drawable.weiguanzhu);
            }
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DaoSession daoSession = DaoMaster.newDevSession(FilmShowActivity.this, LoginSubBeanDao.TABLENAME);
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
