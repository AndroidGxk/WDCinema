package com.bawei.admin.wdcinema.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bawei.admin.wdcinema.activity.fragment.Fragment_Page_one;
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
    private boolean animatort = false;
    private boolean animatorf = false;
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
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    @BindView(R.id.seacrch_linear2)
    LinearLayout seacrch_linear2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_show);
        ButterKnife.bind(this);
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 510f);
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
            hot.setBackgroundResource(R.drawable.btn_gradient);
            release.setBackgroundResource(R.drawable.btn_false);
            comingSoon.setBackgroundResource(R.drawable.btn_false);
            filmShowAdapter.remove();
            hotMoviePresenter.request(userId, sessionId, page, 5);
        } else if (select.equals("2")) {
            release.setBackgroundResource(R.drawable.btn_gradient);
            hot.setBackgroundResource(R.drawable.btn_false);
            comingSoon.setBackgroundResource(R.drawable.btn_false);
            filmShowAdapter.remove();
            releaseMoviePresenter.request(userId, sessionId, page, 5);
        } else {
            comingSoon.setBackgroundResource(R.drawable.btn_gradient);
            hot.setBackgroundResource(R.drawable.btn_false);
            release.setBackgroundResource(R.drawable.btn_false);
            filmShowAdapter.remove();
            comingSoonMoviePresenter.request(userId, sessionId, page, 5);
        }
    }

    @OnClick(R.id.imageView)
    public void seacrch_linear2() {
        if (animatort) {
            return;
        }
        animatort = true;
        animatorf = false;
        //这是显示出现的动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 510f, 30f);
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 510f);
        animator.setDuration(1000);
        animator.start();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMoviePresenter.unBind();
        hotMoviePresenter.unBind();
        comingSoonMoviePresenter.unBind();
    }

    @Override
    public void onResume() {
        super.onResume();
        //百度定位
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    //定位
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
//            double latitude = location.getLatitude();    //获取纬度信息
//            double longitude = location.getLongitude();    //获取经度信息
            if (!location.equals("")) {
                mLocationClient.stop();
            }
//            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            textView.setText(addr);
        }
    }
}
