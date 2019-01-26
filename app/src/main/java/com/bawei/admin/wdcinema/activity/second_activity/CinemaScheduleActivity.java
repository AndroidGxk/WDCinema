package com.bawei.admin.wdcinema.activity.second_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.admin.wdcinema.activity.fragment.Fragment_Page_one;
import com.bawei.admin.wdcinema.adapter.Adapter;
import com.bawei.admin.wdcinema.adapter.CineamScheAdapter;
import com.bawei.admin.wdcinema.adapter.CinemaMovieAdapter;
import com.bawei.admin.wdcinema.adapter.HotMovieAdapter;
import com.bawei.admin.wdcinema.adapter.MovieScheAdapter;
import com.bawei.admin.wdcinema.bean.CineamScheBean;
import com.bawei.admin.wdcinema.bean.HotMovieBean;
import com.bawei.admin.wdcinema.bean.MovieScheBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.CinemaSchePresenter;
import com.bawei.admin.wdcinema.presenter.HotMoviePresenter;
import com.bawei.admin.wdcinema.presenter.MovieSchePresenter;
import com.bw.movie.R;
import com.example.coverflow.CoverFlowLayoutManger;
import com.example.coverflow.RecyclerCoverFlow;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

public class CinemaScheduleActivity extends AppCompatActivity implements ResultInfe, CineamScheAdapter.onItemClick {
    private CineamScheAdapter adapter;
    private SharedPreferences sp;
    @BindView(R.id.cinema_detalis_horse)
    RecyclerCoverFlow mList;
    @BindView(R.id.cinemarecycle)
    RecyclerView cinemarecycle;
    @BindView(R.id.cinema_detalis_sdvone)
    SimpleDraweeView cinema_detalis_sdvone;
    @BindView(R.id.cinema_detalis_textviewone)
    TextView cinema_detalis_textviewone;
    @BindView(R.id.cinema_detalis_textviewtwo)
    TextView cinema_detalis_textviewtwo;
    @BindView(R.id.home_radio_group)
    RadioGroup home_radio_group;
    private CinemaMovieAdapter cinemaMovieAdapter;
    private List<CineamScheBean> scheList;
    private int mId;
    private MovieSchePresenter movieSchePresenter;
    private int id;
    private MovieScheAdapter movieScheAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_schedule);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        String image = intent.getStringExtra("image");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        CinemaSchePresenter cinemaSchePresenter = new CinemaSchePresenter(this);
        cinemaSchePresenter.request(id);
        movieScheAdapter = new MovieScheAdapter(CinemaScheduleActivity.this);
        adapter = new CineamScheAdapter(CinemaScheduleActivity.this, this);
        mList.setAdapter(adapter);
        movieSchePresenter = new MovieSchePresenter(new movieScheList());
        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                home_radio_group.check(home_radio_group.getChildAt(position).getId());
                mId = scheList.get(position).getId();
                //请求
                movieSchePresenter.request(CinemaScheduleActivity.this.id, mId);
            }
        });


        cinema_detalis_sdvone.setImageURI(image);
        cinema_detalis_textviewone.setText(name);
        cinema_detalis_textviewtwo.setText(address);
        LinearLayoutManager manager = new LinearLayoutManager(CinemaScheduleActivity.this);
        cinemarecycle.setLayoutManager(manager);
        cinemarecycle.setAdapter(movieScheAdapter);
    }

    @Override
    public void clickItem(int id) {

    }

    @Override
    public void success(Object data) {
        Result result = (Result) data;
        scheList = (List<CineamScheBean>) result.getResult();
        movieSchePresenter.request(id, scheList.get(0).getId());
        cinemaMovieAdapter = new CinemaMovieAdapter(this);
        adapter.addItem(scheList);
        adapter.notifyDataSetChanged();
        cinemaMovieAdapter.addItem(scheList);
        cinemaMovieAdapter.notifyDataSetChanged();
        for (int i = 0; i < scheList.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            WindowManager wm = (WindowManager) this
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            double widths =width/scheList.size();
            radioButton.setWidth((int)widths);
            radioButton.setButtonTintMode(PorterDuff.Mode.CLEAR);
            radioButton.setBackgroundResource(R.drawable.radio_selector);
            home_radio_group.addView(radioButton);
        }
        home_radio_group.check(home_radio_group.getChildAt(0).getId());
    }

    @Override
    public void errors(Throwable throwable) {

    }

    class movieScheList implements ResultInfe {

        @Override
        public void success(Object data) {
            movieScheAdapter.removeItem();
            Result result = (Result) data;
            List<MovieScheBean> movieScheList = (List<MovieScheBean>) result.getResult();
            movieScheAdapter.addItem(movieScheList);
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

}
