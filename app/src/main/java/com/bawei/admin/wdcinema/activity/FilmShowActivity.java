package com.bawei.admin.wdcinema.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;

import com.bawei.admin.wdcinema.adapter.FilmShowAdapter;
import com.bawei.admin.wdcinema.bean.HotMovieBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.ComingSoonMoviePresenter;
import com.bawei.admin.wdcinema.presenter.HotMoviePresenter;
import com.bawei.admin.wdcinema.presenter.ReleaseMoviePresenter;
import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class FilmShowActivity extends AppCompatActivity implements CustomAdapt, XRecyclerView.LoadingListener {

    private boolean hotcheck = true;
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
    private HotMoviePresenter hotMoviePresenter;
    private ReleaseMoviePresenter releaseMoviePresenter;
    private ComingSoonMoviePresenter comingSoonMoviePresenter;
    private int page = 1;
    private SharedPreferences sp;
    private String sessionId;
    private int userId;
    private FilmShowAdapter filmShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_show);
        ButterKnife.bind(this);

        //调用sp，获取userID和sessionid
        sp = getSharedPreferences("login", MODE_PRIVATE);

        sessionId = sp.getString("sessionId", "1");
        userId = sp.getInt("userId", 1);

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

        hotMoviePresenter.request(userId, sessionId, page, 5);
    }

    //点击事件
    @OnClick(R.id.hot_show)
    public void hot() {
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

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
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
