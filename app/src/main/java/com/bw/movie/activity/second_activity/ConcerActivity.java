package com.bw.movie.activity.second_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.activity.MoviesByIdActivity;
import com.bw.movie.adapter.CineamaRecycleAdapter;
import com.bw.movie.adapter.ConcerRecycleAdapter;
import com.bw.movie.bean.CinemaPageList;
import com.bw.movie.bean.MovieListBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.presenter.GuanCineamListPresenter;
import com.bw.movie.presenter.GuanMovieListPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class ConcerActivity extends AppCompatActivity implements ResultInfe, CustomAdapt, XRecyclerView.LoadingListener {
    private boolean recommcheck = true;
    private boolean nearbycheck = false;
    private SharedPreferences sp;
    @BindView(R.id.movie_btn)
    Button movie_btn;
    @BindView(R.id.cinema_btn)
    Button cinema_btn;
    @BindView(R.id.concerrecycleview)
    XRecyclerView concerrecycleview;
    @BindView(R.id.concerrecycleview2)
    XRecyclerView concerrecycleview2;
    private ConcerRecycleAdapter concerRecycleAdapter;
    private int page = 1;
    private static final int count = 5;
    private GuanMovieListPresenter movieListPresenter;
    private String seesionId;
    private int userId;
    private GuanCineamListPresenter cineamListPresenter;
    private CineamaRecycleAdapter cineamaRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        ButterKnife.bind(this);
        movie_btn.setBackgroundResource(R.drawable.btn_gradient);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        seesionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        movieListPresenter = new GuanMovieListPresenter(this);
        cineamListPresenter = new GuanCineamListPresenter(new CineamaP());
        movieListPresenter.request(userId, seesionId, page, count);
        concerRecycleAdapter = new ConcerRecycleAdapter();
        cineamaRecycleAdapter = new CineamaRecycleAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(ConcerActivity.this);
        LinearLayoutManager manager2 = new LinearLayoutManager(ConcerActivity.this);
        concerrecycleview.setLoadingListener(this);
        concerrecycleview.setPullRefreshEnabled(true);
        concerrecycleview.setLoadingMoreEnabled(true);
        concerrecycleview.setLayoutManager(manager);
        concerrecycleview.setAdapter(concerRecycleAdapter);
//        concerRecycleAdapter.setOnclick(new ConcerRecycleAdapter.Onclick() {
//            @Override
//            public void onClick(int id) {
//                Intent intent = new Intent(ConcerActivity.this, MoviesByIdActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
//            }
//        });
        concerrecycleview2.setLoadingListener(new LoadingListener2());
        concerrecycleview2.setLoadingMoreEnabled(true);
        concerrecycleview2.setPullRefreshEnabled(true);
        concerrecycleview2.setLayoutManager(manager2);
        concerrecycleview2.setAdapter(cineamaRecycleAdapter);
    }

    @OnClick(R.id.moviesbyid_finish)
    public void moviesbyid_finish() {
        finish();
    }

    class LoadingListener2 implements XRecyclerView.LoadingListener {

        @Override
        public void onRefresh() {
            page = 1;
            cineamaRecycleAdapter.removeAll();
            cineamListPresenter.request(userId, seesionId, page, count);
        }

        @Override
        public void onLoadMore() {
            page++;
            cineamListPresenter.request(userId, seesionId, page, count);
        }
    }

    class CineamaP implements ResultInfe {

        @Override
        public void success(Object data) {
            Result result = (Result) data;
            List<CinemaPageList> pageLists = (List<CinemaPageList>) result.getResult();
            cineamaRecycleAdapter.addAll(pageLists);
            concerrecycleview2.refreshComplete();
            concerrecycleview2.loadMoreComplete();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    @OnClick(R.id.movie_btn)
    public void recommend() {
        if (recommcheck) {
            return;
        }
        recommcheck = true;
        page = 1;
        if (recommcheck) {
            concerRecycleAdapter.removeAll();
            cineamaRecycleAdapter.removeAll();
            movieListPresenter.request(userId, seesionId, page, count);
            movie_btn.setBackgroundResource(R.drawable.btn_gradient);
            concerrecycleview2.setVisibility(View.GONE);
            concerrecycleview.setVisibility(View.VISIBLE);
            nearbycheck = false;
            cinema_btn.setBackgroundResource(R.drawable.btn_false);
        }
    }

    @OnClick(R.id.cinema_btn)
    public void nearby() {
        if (nearbycheck) {
            return;
        }
        nearbycheck = true;
        page = 1;
        if (nearbycheck) {
            concerRecycleAdapter.removeAll();
            cineamaRecycleAdapter.removeAll();
            concerrecycleview.setVisibility(View.GONE);
            concerrecycleview2.setVisibility(View.VISIBLE);
            cineamListPresenter.request(userId, seesionId, page, count);
            cinema_btn.setBackgroundResource(R.drawable.btn_gradient);
            recommcheck = false;
            movie_btn.setBackgroundResource(R.drawable.btn_false);
        }
    }

    /**
     * 关注电影列表
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        List<MovieListBean> movieList = (List<MovieListBean>) result.getResult();
        concerRecycleAdapter.addAll(movieList);
        concerrecycleview.refreshComplete();
        concerrecycleview.loadMoreComplete();
    }

    @Override
    public void errors(Throwable throwable) {

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    /**
     * 上拉下拉刷新加载
     */
    @Override
    public void onRefresh() {
        page = 1;
        concerRecycleAdapter.removeAll();
        movieListPresenter.request(userId, seesionId, page, count);
    }

    @Override
    public void onLoadMore() {
        page++;
        movieListPresenter.request(userId, seesionId, page, count);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieListPresenter.unBind();
    }
}
