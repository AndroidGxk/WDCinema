package com.bw.movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.thirdly_activity.MovieSeatActivity;
import com.bw.movie.adapter.MovieScheAdapter;
import com.bw.movie.bean.MovieScheBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.presenter.MovieSchePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

public class MovieScheduleListActivity extends WDActivity {

    private MovieScheAdapter movieScheAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_moviescheduielist;
    }

    @BindView(R.id.cinema_name)
    TextView cinema_name;
    @BindView(R.id.cinema_address)
    TextView cinema_address;
    @BindView(R.id.movie_name)
    TextView movie_name;
    @BindView(R.id.movie)
    SimpleDraweeView movie;
    @BindView(R.id.movie_shichang)
    TextView movie_shichang;
    @BindView(R.id.movie_chandi)
    TextView movie_chandi;
    @BindView(R.id.movie_daoyan)
    TextView movie_daoyan;
    @BindView(R.id.movie_leixing)
    TextView movie_leixing;
    @BindView(R.id.movieScheduleList_recycler)
    RecyclerView movieScheduleList_recycler;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        final String address = intent.getStringExtra("address");
        final String name = intent.getStringExtra("name");
        final String names = intent.getStringExtra("names");
        String director = intent.getStringExtra("director");
        String duration = intent.getStringExtra("duration");
        String placeOrigin = intent.getStringExtra("placeOrigin");
        String movieTypes = intent.getStringExtra("movieTypes");
        String imageUrl = intent.getStringExtra("imageUrl");
        String id = intent.getStringExtra("id");
        final String ids = intent.getStringExtra("ids");

        cinema_address.setText(address);
        cinema_name.setText(name);
        movie_name.setText(names);
        movie_daoyan.setText("导演：" + director);
        movie_shichang.setText("时长：" + duration);
        movie_chandi.setText("产地：" + placeOrigin);
        movie_leixing.setText("类型：" + movieTypes);
        movie.setImageURI(Uri.parse(imageUrl));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        movieScheduleList_recycler.setLayoutManager(linearLayoutManager);
        movieScheAdapter = new MovieScheAdapter(this);
        movieScheduleList_recycler.setAdapter(movieScheAdapter);

        MovieSchePresenter movieSchePresenter = new MovieSchePresenter(new MovieSche());
        movieSchePresenter.request(Integer.parseInt(ids), Integer.parseInt(id));

        movieScheAdapter.setOnClickListener(new MovieScheAdapter.OnClickListener() {
            @Override
            public void onclick(int id, int position, String date, String house, int datetime, double money) {
                Intent intent = new Intent(MovieScheduleListActivity.this, MovieSeatActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("pid", ids);
                intent.putExtra("names", names);
                intent.putExtra("address", address);
                intent.putExtra("date", date);
                intent.putExtra("namemovie", name);
                intent.putExtra("name", house);
                intent.putExtra("datetime", datetime);
                intent.putExtra("money", money);
                intent.putExtra("releasetime", 1000000000);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    private class MovieSche implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            movieScheAdapter.removeItem();
            List<MovieScheBean> movieScheList = (List<MovieScheBean>) data.getResult();
            movieScheAdapter.addItem(movieScheList);
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }
}
