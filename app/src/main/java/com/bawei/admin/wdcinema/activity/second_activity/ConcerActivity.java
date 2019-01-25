package com.bawei.admin.wdcinema.activity.second_activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bawei.admin.wdcinema.bean.MovieListBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.GuanMovieListPresenter;
import com.bw.movie.R;

import java.util.List;

public class ConcerActivity extends AppCompatActivity implements ResultInfe {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        String seesionId = sp.getString("sessionId", "");
        int userId = sp.getInt("userId", 0);
        GuanMovieListPresenter movieListPresenter = new GuanMovieListPresenter(this);
        movieListPresenter.request(userId, seesionId, 1, 5);
    }

    /**
     * 关注电影列表
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        List<MovieListBean> movieListBeans = (List<MovieListBean>) result.getResult();
        Toast.makeText(this, "" + movieListBeans.get(0).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errors(Throwable throwable) {

    }
}
