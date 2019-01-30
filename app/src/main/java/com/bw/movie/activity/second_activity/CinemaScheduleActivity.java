package com.bw.movie.activity.second_activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.thirdly_activity.MovieSeatActivity;
import com.bw.movie.adapter.CineamScheAdapter;
import com.bw.movie.adapter.CinemaComListAdapter;
import com.bw.movie.adapter.CinemaMovieAdapter;
import com.bw.movie.adapter.MovieScheAdapter;
import com.bw.movie.adapter.UserTicketRecycleAdapter;
import com.bw.movie.bean.CineamComListBean;
import com.bw.movie.bean.CineamScheBean;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.MovieScheBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.CineamConListPresenter;
import com.bw.movie.presenter.CinemaSchePresenter;
import com.bw.movie.presenter.MovieSchePresenter;
import com.example.coverflow.CoverFlowLayoutManger;
import com.example.coverflow.RecyclerCoverFlow;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CinemaScheduleActivity extends AppCompatActivity implements ResultInfe, CineamScheAdapter.onItemClick, LoadingListener {
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
    @BindView(R.id.time)
    TextView timeview;
    private CinemaMovieAdapter cinemaMovieAdapter;
    private List<CineamScheBean> scheList = new ArrayList<>();
    private int mId;
    private Dialog bottomDialog;
    private MovieSchePresenter movieSchePresenter;
    private int ids;
    private MovieScheAdapter movieScheAdapter;
    private String name;
    private String address;
    private Date dates;
    private SimpleDateFormat format;
    private String namemovie;
    private View contentView;
    private TextView mTv_dialog_monitor_xiang;
    private TextView mTv_dialog_monitor_ping;
    private View mView_dialog_monitor_xiang;
    private View mView_dialog_monitor_ping;
    private XRecyclerView xrecycle;
    private CineamConListPresenter cineamConListPresenter;
    private LoginSubBeanDao loginSubBeanDao;
    private List<LoginSubBean> list;
    private CinemaComListAdapter cinemaComListAdapter;
    private int mPage = 1;
    private int mCount = 5;
    private LoginSubBean loginSubBean;
    private ImageView popupwindow_detalis_sdvtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_schedule);
        ButterKnife.bind(this);

        //影院评论列表
        cineamConListPresenter = new CineamConListPresenter(new CineamComList());
        dates = new Date();
        format = new SimpleDateFormat("MM-dd hh:mm:ss");
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        Intent intent = getIntent();
        ids = intent.getIntExtra("id", 0);
        String image = intent.getStringExtra("image");
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        CinemaSchePresenter cinemaSchePresenter = new CinemaSchePresenter(this);
        cinemaSchePresenter.request(ids);
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
                namemovie = scheList.get(position).getName();
                movieSchePresenter.request(CinemaScheduleActivity.this.ids, mId);
                dates.setTime(scheList.get(position).getReleaseTime());
                String Datetime = format.format(dates);
                timeview.setText(Datetime);
            }
        });
        movieScheAdapter.setOnClickListener(new MovieScheAdapter.OnClickListener() {
            @Override
            public void onclick(int id, int position, String date, String house, int datetime, double money) {
                CineamScheBean cineamScheBean = scheList.get(position);
                Intent intent = new Intent(CinemaScheduleActivity.this, MovieSeatActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("pid", mId);
                intent.putExtra("names", name);
                intent.putExtra("address", address);
                intent.putExtra("date", date);
                intent.putExtra("namemovie", namemovie);
                intent.putExtra("name", house);
                intent.putExtra("datetime", datetime);
                intent.putExtra("money", money);
                intent.putExtra("releasetime", cineamScheBean.getReleaseTime());
                startActivity(intent);
            }
        });
        contentView = LayoutInflater.from(this).inflate(R.layout.cinemainfo_item, null);

        cinema_detalis_sdvone.setImageURI(image);
        cinema_detalis_textviewone.setText(name);
        cinema_detalis_textviewtwo.setText(address);
        LinearLayoutManager manager = new LinearLayoutManager(CinemaScheduleActivity.this);
        cinemarecycle.setLayoutManager(manager);
        cinemarecycle.setAdapter(movieScheAdapter);
        bottomDialog = new Dialog(this, R.style.BottomDialog);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DaoSession daoSession = DaoMaster.newDevSession(CinemaScheduleActivity.this, LoginSubBeanDao.TABLENAME);
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
    }

    // TODO: 2019/1/30  影院详情显示
    private void initview(View inflate) {
        final RelativeLayout rec_2 = inflate.findViewById(R.id.rec_2);
        final RelativeLayout RelativeLayout = inflate.findViewById(R.id.xiangqing);
        mTv_dialog_monitor_xiang = inflate.findViewById(R.id.tv_dialog_monitor_xiang);
        mTv_dialog_monitor_ping = inflate.findViewById(R.id.tv_dialog_monitor_ping);
        mView_dialog_monitor_xiang = inflate.findViewById(R.id.view_dialog_monitor_xiang);
        mView_dialog_monitor_ping = inflate.findViewById(R.id.view_dialog_monitor_ping);
        popupwindow_detalis_sdvtwo = inflate.findViewById(R.id.popupwindow_detalis_sdvtwo);
        xrecycle = inflate.findViewById(R.id.xrecycle);
        LinearLayoutManager manager = new LinearLayoutManager(CinemaScheduleActivity.this);
        cinemaComListAdapter = new CinemaComListAdapter();
        xrecycle.setAdapter(cinemaComListAdapter);
        xrecycle.setLoadingMoreEnabled(true);
        xrecycle.setLoadingListener(this);
        xrecycle.setPullRefreshEnabled(true);
        xrecycle.setLayoutManager(manager);
        if (list.size() > 0) {
            loginSubBean = list.get(0);
            cineamConListPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), ids, mPage, mCount);
        }
        mTv_dialog_monitor_xiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView_dialog_monitor_xiang.setVisibility(View.VISIBLE);
                mView_dialog_monitor_ping.setVisibility(View.GONE);
                rec_2.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.VISIBLE);
            }
        });
        mTv_dialog_monitor_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView_dialog_monitor_xiang.setVisibility(View.GONE);
                mView_dialog_monitor_ping.setVisibility(View.VISIBLE);
                rec_2.setVisibility(View.VISIBLE);
                RelativeLayout.setVisibility(View.GONE);

            }
        });
        popupwindow_detalis_sdvtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.cinema_detalis_textviewtwo)
    public void cinema_detalis_textviewtwo() {
        show(contentView);
    }

    @OnClick(R.id.moviesbyid_finish)
    public void moviesbyid_finish() {
        finish();
    }

    @Override
    public void clickItem(int id) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        scheList = (List<CineamScheBean>) result.getResult();
        namemovie = scheList.get(0).getName();
        dates.setTime(scheList.get(0).getReleaseTime());
        String Datetime = format.format(dates);
        timeview.setText(Datetime);
        movieSchePresenter.request(ids, scheList.get(0).getId());
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
            double widths = width / scheList.size();
            radioButton.setWidth((int) widths);
            radioButton.setButtonTintMode(PorterDuff.Mode.CLEAR);
            radioButton.setBackgroundResource(R.drawable.radio_selector);
            home_radio_group.addView(radioButton);
        }
        home_radio_group.check(home_radio_group.getChildAt(0).getId());
    }

    @Override
    public void errors(Throwable throwable) {

    }

    /**
     * 上拉下拉刷新加载
     */
    @Override
    public void onRefresh() {
        mPage = 1;
        cinemaComListAdapter.RemoveAll();
        cineamConListPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), ids, mPage, mCount);

    }

    @Override
    public void onLoadMore() {
        mPage++;
        cineamConListPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), ids, mPage, mCount);
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

    /**
     * 影院详情
     */
    private void show(View contentViewss) {
        bottomDialog.setContentView(contentViewss);
        ViewGroup.LayoutParams layoutParams = contentViewss.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentViewss.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        initview(contentView);
    }

    /**
     * 影院评论列表接口
     */
    private class CineamComList implements ResultInfe {

        @Override
        public void success(Object data) {
            Result result = (Result) data;
            List<CineamComListBean> comListBeans = (List<CineamComListBean>) result.getResult();
            cinemaComListAdapter.setList(comListBeans);
            xrecycle.loadMoreComplete();
            xrecycle.refreshComplete();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }
}
