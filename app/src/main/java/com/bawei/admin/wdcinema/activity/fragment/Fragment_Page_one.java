package com.bawei.admin.wdcinema.activity.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bawei.admin.wdcinema.adapter.Adapter;
import com.bawei.admin.wdcinema.adapter.ComingSoonMovieAdapter;
import com.bawei.admin.wdcinema.adapter.HotMovieAdapter;
import com.bawei.admin.wdcinema.adapter.ReleaseMovieAdapter;
import com.bawei.admin.wdcinema.bean.HotMovieBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.ComingSoonMoviePresenter;
import com.bawei.admin.wdcinema.presenter.HotMoviePresenter;
import com.bawei.admin.wdcinema.presenter.ReleaseMoviePresenter;
import com.bw.movie.R;
import com.example.coverflow.RecyclerCoverFlow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Page_one extends Fragment implements Adapter.onItemClick, CustomAdapt {

    private Adapter adapter;
    private SharedPreferences sp;
    private HotMoviePresenter hotMoviePresenter;
    @BindView(R.id.list)
    RecyclerCoverFlow mList;
    @BindView(R.id.hot_recycler)
    RecyclerView hot_recycler;
    private HotMovieAdapter hotMovieAdapter;
    @BindView(R.id.release_recycler)
    RecyclerView release_recycler;
    @BindView(R.id.comingSoon_recycler)
    RecyclerView comingSoon_recycler;
    @BindView(R.id.location_tv)
    TextView textView;
    private ReleaseMovieAdapter releaseMovieAdapter;
    private ComingSoonMovieAdapter comingSoonMovieAdapter;
    private ReleaseMoviePresenter releaseMoviePresenter;
    private ComingSoonMoviePresenter comingSoonMoviePresenter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_page_one, null);
        ButterKnife.bind(this, view);
        //调用sp，获取userID和sessionid
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);

        String sessionId = sp.getString("sessionId", "1");
        int userId = sp.getInt("userId", 1);
        //屏幕适配
        AutoSizeConfig.getInstance().setCustomFragment(true);
        //热门影片banner
        hotMoviePresenter = new HotMoviePresenter(new HotMovie());
        releaseMoviePresenter = new ReleaseMoviePresenter(new ReleaseMovie());
        comingSoonMoviePresenter = new ComingSoonMoviePresenter(new ComingSoonMovie());
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        adapter = new Adapter(getContext(), this);
        mList.setAdapter(adapter);

        //热门影片
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_recycler.setLayoutManager(linearLayoutManager1);
        hotMovieAdapter = new HotMovieAdapter(getContext());
        hot_recycler.setAdapter(hotMovieAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        release_recycler.setLayoutManager(linearLayoutManager2);
        releaseMovieAdapter = new ReleaseMovieAdapter(getContext());
        release_recycler.setAdapter(releaseMovieAdapter);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        comingSoon_recycler.setLayoutManager(linearLayoutManager3);
        comingSoonMovieAdapter = new ComingSoonMovieAdapter(getContext());
        comingSoon_recycler.setAdapter(comingSoonMovieAdapter);

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

        hotMoviePresenter.request(userId, sessionId, 1, 10);
        releaseMoviePresenter.request(userId, sessionId, 1, 10);
        comingSoonMoviePresenter.request(userId, sessionId, 1, 10);
        return view;
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getAddrStr();    //获取详细地址信息
            textView.setText(locationDescribe + addr);

        }
    }

    @Override
    public void clickItem(int pos) {
        mList.smoothScrollToPosition(pos);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class HotMovie implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<HotMovieBean> result = (List<HotMovieBean>) data.getResult();
            adapter.addItem(result);
            adapter.notifyDataSetChanged();
            hotMovieAdapter.addItem(result);
            hotMovieAdapter.notifyDataSetChanged();

            releaseMovieAdapter.addItem(result);
            releaseMovieAdapter.notifyDataSetChanged();

            comingSoonMovieAdapter.addItem(result);
            comingSoonMovieAdapter.notifyDataSetChanged();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class ReleaseMovie implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<HotMovieBean> result = (List<HotMovieBean>) data.getResult();
            releaseMovieAdapter.addItem(result);
            releaseMovieAdapter.notifyDataSetChanged();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class ComingSoonMovie implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<HotMovieBean> result = (List<HotMovieBean>) data.getResult();
            comingSoonMovieAdapter.addItem(result);
            comingSoonMovieAdapter.notifyDataSetChanged();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }
}
