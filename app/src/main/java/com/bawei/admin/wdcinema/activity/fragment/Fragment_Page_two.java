package com.bawei.admin.wdcinema.activity.fragment;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bawei.admin.wdcinema.adapter.TuiMovieRecycleAdapter;
import com.bawei.admin.wdcinema.bean.RecommBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.NearMoviePresenter;
import com.bawei.admin.wdcinema.presenter.RecomMoviePresenter;
import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

import static android.content.Context.MODE_PRIVATE;


public class Fragment_Page_two extends Fragment implements ResultInfe, XRecyclerView.LoadingListener, CustomAdapt {
    @BindView(R.id.recommend)
    Button recommend;
    @BindView(R.id.nearby)
    Button nearby;
    @BindView(R.id.cimema_text)
    TextView cimema_text;
    @BindView(R.id.cinemarecycleview)
    XRecyclerView cinemarecycleview;
    private boolean recommcheck = true;
    private boolean nearbycheck = false;
    private RecomMoviePresenter recomMoviePresenter;
    private SharedPreferences sp;
    private int page = 1;
    private final static int count = 5;
    private TuiMovieRecycleAdapter tuiMovieRecycleAdapter;
    private int userId;
    private String sessionId;
    private NearMoviePresenter nearMoviePresenter;
    public LocationClient mLocationClient = null;
    MyLocationListener myListener = new MyLocationListener();
    private double latitude;
    private double longitude;
    @BindView(R.id.seacrch_linear2)
    LinearLayout seacrch_linear2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_page_two, null);
        ButterKnife.bind(this, view);
        recommend.setBackgroundResource(R.drawable.btn_gradient);
        recomMoviePresenter = new RecomMoviePresenter(this);
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        sessionId = sp.getString("sessionId", "1");
        userId = sp.getInt("userId", 1);
        nearMoviePresenter = new NearMoviePresenter(this);
        recomMoviePresenter.request(userId, sessionId, page, count);
        cinemarecycleview.setLoadingListener(this);
        cinemarecycleview.setLoadingMoreEnabled(true);
        cinemarecycleview.setPullRefreshEnabled(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        tuiMovieRecycleAdapter = new TuiMovieRecycleAdapter();
        cinemarecycleview.setLayoutManager(manager);
        cinemarecycleview.setAdapter(tuiMovieRecycleAdapter);
        location();
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 510f);
        animator.setDuration(0);
        animator.start();
        return view;
    }

    @OnClick(R.id.imageView)
    public void seacrch_linear2() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 510f, 30f);
        animator.setDuration(1500);
        animator.start();
    }

    @OnClick(R.id.seacrch_text)
    public void seacrch_text() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 510f);
        animator.setDuration(1500);
        animator.start();
    }

    //定位
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            //获取纬度信息
            latitude = location.getLatitude();
            //获取经度信息
            longitude = location.getLongitude();
            if(!location.getCity().equals("")){
                cimema_text.setText(location.getCity());
                mLocationClient.stop();
            }
        }
    }

    @OnClick(R.id.recommend)
    public void recommend() {
        recommcheck = true;
        page = 1;
        if (recommcheck) {
            tuiMovieRecycleAdapter.removeAll();
            recomMoviePresenter.request(userId, sessionId, page, count);
            recommend.setBackgroundResource(R.drawable.btn_gradient);
            nearbycheck = false;
            nearby.setBackgroundResource(R.drawable.btn_false);
        }
    }

    @OnClick(R.id.nearby)
    public void nearby() {
        nearbycheck = true;
        page = 1;
        if (nearbycheck) {
            tuiMovieRecycleAdapter.removeAll();
            nearMoviePresenter.request(userId, sessionId, String.valueOf(longitude), String.valueOf(latitude), page, count);
            nearby.setBackgroundResource(R.drawable.btn_gradient);
            recommcheck = false;
            recommend.setBackgroundResource(R.drawable.btn_false);
        }
    }

    /**
     * 推荐影院
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        List<RecommBean> recommList = (List<RecommBean>) result.getResult();
        tuiMovieRecycleAdapter.addAll(recommList);
        cinemarecycleview.loadMoreComplete();
        cinemarecycleview.refreshComplete();
        Log.e("GT", "cinemarecycleview" + recommList.get(0).getAddress());
    }

    @Override
    public void errors(Throwable throwable) {

    }

    /**
     * 上拉下拉加载
     */
    @Override
    public void onRefresh() {

        page = 1;
        tuiMovieRecycleAdapter.removeAll();
        if (recommcheck) {
            recomMoviePresenter.request(userId, sessionId, page, count);
        } else {
            nearMoviePresenter.request(userId, sessionId, String.valueOf(longitude), String.valueOf(latitude), page, count);

        }
    }

    @Override
    public void onLoadMore() {
        page++;
        if (recommcheck) {
            recomMoviePresenter.request(userId, sessionId, page, count);
        } else {
            nearMoviePresenter.request(userId, sessionId, String.valueOf(longitude), String.valueOf(latitude), page, count);
        }
    }

    /**
     * 定位
     */
    private void location() {
        //百度定位
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
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
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        nearMoviePresenter.unBind();
        recomMoviePresenter.unBind();
    }
}
