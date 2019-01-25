package com.bawei.admin.wdcinema.activity.second_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.admin.wdcinema.activity.fragment.Fragment_Page_one;
import com.bawei.admin.wdcinema.adapter.Adapter;
import com.bawei.admin.wdcinema.adapter.CineamScheAdapter;
import com.bawei.admin.wdcinema.adapter.CinemaMovieAdapter;
import com.bawei.admin.wdcinema.adapter.HotMovieAdapter;
import com.bawei.admin.wdcinema.bean.CineamScheBean;
import com.bawei.admin.wdcinema.bean.HotMovieBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.CinemaSchePresenter;
import com.bawei.admin.wdcinema.presenter.HotMoviePresenter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_schedule);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String image = intent.getStringExtra("image");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        CinemaSchePresenter cinemaSchePresenter = new CinemaSchePresenter(this);
        cinemaSchePresenter.request(id);
        adapter = new CineamScheAdapter(CinemaScheduleActivity.this, this);
        mList.setAdapter(adapter);
        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                home_radio_group.check(home_radio_group.getChildAt(position).getId());
            }
        });
        cinemaMovieAdapter = new CinemaMovieAdapter(this);
        cinema_detalis_sdvone.setImageURI(image);
        cinema_detalis_textviewone.setText(name);
        cinema_detalis_textviewtwo.setText(address);

    }

    @Override
    public void clickItem(int id) {

    }

    @Override
    public void success(Object data) {
        Result result = (Result) data;
        scheList = (List<CineamScheBean>) result.getResult();
        adapter.addItem(scheList);
        adapter.notifyDataSetChanged();
        cinemaMovieAdapter.addItem(scheList);
        cinemaMovieAdapter.notifyDataSetChanged();

//            <RadioButton
//        android:id="@+id/home_radio_1"
//        android:layout_width="0dp"
//        android:layout_height="match_parent"
//        android:layout_weight="1"
//        android:background="@drawable/radio_selector"
//        android:button="@null"
//        android:checked="true" />
        for (int i = 0; i < scheList.size(); i++) {
            RadioButton radioButton = (RadioButton) View.inflate(CinemaScheduleActivity.this, R.layout.radio_button, null);
            home_radio_group.addView(radioButton);
        }
        home_radio_group.check(home_radio_group.getChildAt(0).getId());
    }

    @Override
    public void errors(Throwable throwable) {

    }


}
