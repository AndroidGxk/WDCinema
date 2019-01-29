package com.bw.movie.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemasListByMovieIdAdapter;
import com.bw.movie.bean.CinemasListByMovieIdBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.presenter.CinemasListByMovieIdPresenter;

import java.util.List;

import butterknife.BindView;

public class CinemasListByMovieIdActivity extends WDActivity {

    private CinemasListByMovieIdPresenter cinemasListByMovieIdPresenter;
    @BindView(R.id.movie_recycler)
    RecyclerView movie_recycler;
    private CinemasListByMovieIdAdapter cinemasListByMovieIdAdapter;
    @BindView(R.id.moview_name)
    TextView moview_name;
    private String names;
    private String id;
    private String director;
    private String duration;
    private String placeOrigin;
    private String movieTypes;
    private String imageUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinemas_list_by_movie_id;
    }

    @Override
    protected void initView() {
        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        names = intent.getStringExtra("name");
        director = intent.getStringExtra("director");
        duration = intent.getStringExtra("duration");
        placeOrigin = intent.getStringExtra("placeOrigin");
        movieTypes = intent.getStringExtra("movieTypes");
        imageUrl = intent.getStringExtra("imageUrl");

        moview_name.setText(names);
        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        cinemasListByMovieIdPresenter = new CinemasListByMovieIdPresenter(new CinemasListByMovieId());
        cinemasListByMovieIdPresenter.request(Integer.parseInt(id));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        movie_recycler.setLayoutManager(linearLayoutManager);
        cinemasListByMovieIdAdapter = new CinemasListByMovieIdAdapter(this);
        movie_recycler.setAdapter(cinemasListByMovieIdAdapter);

        cinemasListByMovieIdAdapter.setOnClick(new CinemasListByMovieIdAdapter.OnClick() {
            @Override
            public void click(String address, String name, int ids) {
                Intent intent1 = new Intent(CinemasListByMovieIdActivity.this, MovieScheduleListActivity.class);
                intent1.putExtra("address", address);
                intent1.putExtra("name", name);
                intent1.putExtra("names", names);
                intent1.putExtra("director", director);
                intent1.putExtra("duration", duration);
                intent1.putExtra("placeOrigin", placeOrigin);
                intent1.putExtra("movieTypes", movieTypes);
                intent1.putExtra("imageUrl", imageUrl);
                intent1.putExtra("id", id);
                intent1.putExtra("ids", String.valueOf(ids));
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void destoryData() {
        cinemasListByMovieIdPresenter.unBind();
    }

    private class CinemasListByMovieId implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<CinemasListByMovieIdBean> result = (List<CinemasListByMovieIdBean>) data.getResult();
            cinemasListByMovieIdAdapter.addItem(result);
            cinemasListByMovieIdAdapter.notifyDataSetChanged();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }
}
