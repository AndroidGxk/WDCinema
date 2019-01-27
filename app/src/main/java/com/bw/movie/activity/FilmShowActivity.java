package com.bw.movie.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.FilmShowAdapter;
import com.bw.movie.bean.HotMovieBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.presenter.ComingSoonMoviePresenter;
import com.bw.movie.presenter.HotMoviePresenter;
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
    private SharedPreferences sp;
    private String sessionId;
    private int userId;
    private FilmShowAdapter filmShowAdapter;
    @BindView(R.id.seacrch_linear2)
    LinearLayout seacrch_linear2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_show;
    }

    @Override
    protected void initView() {
        String getdz = WDActivity.getdz();
        textView.setText(getdz);
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 550f);
        animator.setDuration(0);
        animator.start();
        //调用sp，获取userID和sessionid
        sp = getSharedPreferences("login", MODE_PRIVATE);

        sessionId = sp.getString("sessionId", "1");
        userId = sp.getInt("userId", 1);

        Intent intent = getIntent();
        String select = intent.getStringExtra("select");

        hotMoviePresenter = new HotMoviePresenter(new Hot());
        releaseMoviePresenter = new ReleaseMoviePresenter(new Release());
        comingSoonMoviePresenter = new ComingSoonMoviePresenter(new ComingSoon());
        filmshow_recycler.setLoadingListener(this);
        filmshow_recycler.setLoadingMoreEnabled(true);
        filmshow_recycler.setPullRefreshEnabled(true);

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
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 550f, 30f);
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 550f);
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
}
