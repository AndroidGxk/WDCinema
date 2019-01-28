package com.bw.movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

public class MovieScheduleListActivity extends WDActivity {
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

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String name = intent.getStringExtra("name");
        String names = intent.getStringExtra("names");
        String director = intent.getStringExtra("director");
        String duration = intent.getStringExtra("duration");
        String placeOrigin = intent.getStringExtra("placeOrigin");
        String movieTypes = intent.getStringExtra("movieTypes");
        String imageUrl = intent.getStringExtra("imageUrl");
        cinema_address.setText(address);
        cinema_name.setText(name);
        movie_name.setText(names);
        movie_daoyan.setText("导演：" + director);
        movie_shichang.setText("时长：" + duration);
        movie_chandi.setText("产地：" + placeOrigin);
        movie_leixing.setText("类型：" + movieTypes);
        movie.setImageURI(Uri.parse(imageUrl));
    }

    @Override
    protected void destoryData() {

    }
}
