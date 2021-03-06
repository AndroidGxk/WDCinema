package com.bw.movie.activity.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.bw.movie.activity.FilmShowActivity;
import com.bw.movie.activity.MoviesByIdActivity;
import com.bw.movie.adapter.Adapter;
import com.bw.movie.adapter.ComingSoonMovieAdapter;
import com.bw.movie.adapter.HotMovieAdapter;
import com.bw.movie.adapter.ReleaseMovieAdapter;
import com.bw.movie.bean.HotMovieBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.core.utils.FileUtils;
import com.bw.movie.presenter.ComingSoonMoviePresenter;
import com.bw.movie.presenter.HotMoviePresenter;
import com.bw.movie.presenter.ReleaseMoviePresenter;
import com.example.coverflow.CoverFlowLayoutManger;
import com.example.coverflow.RecyclerCoverFlow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.seacrch_editext)
    EditText seacrch_editext;
    private HotMovieAdapter hotMovieAdapter;
    @BindView(R.id.release_recycler)
    RecyclerView release_recycler;
    @BindView(R.id.comingSoon_recycler)
    RecyclerView comingSoon_recycler;
    @BindView(R.id.location_tv)
    TextView textView;
    @BindView(R.id.home_radio_group)
    RadioGroup home_radio_group;
    @BindView(R.id.home_radio_1)
    RadioButton home_radio_1;
    @BindView(R.id.home_radio_2)
    RadioButton home_radio_2;
    @BindView(R.id.home_radio_3)
    RadioButton home_radio_3;
    @BindView(R.id.home_radio_4)
    RadioButton home_radio_4;
    @BindView(R.id.home_radio_5)
    RadioButton home_radio_5;
    @BindView(R.id.home_radio_6)
    RadioButton home_radio_6;
    private boolean animatort = false;
    private boolean animatorf = false;
    private ReleaseMovieAdapter releaseMovieAdapter;
    private ComingSoonMovieAdapter comingSoonMovieAdapter;
    private ReleaseMoviePresenter releaseMoviePresenter;
    private ComingSoonMoviePresenter comingSoonMoviePresenter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    @BindView(R.id.seacrch_linear2)
    LinearLayout seacrch_linear2;
    private int width;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_page_one, null);
        ButterKnife.bind(this, view);

        //调用sp，获取userID和sessionid
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();

        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 530f);
        animator.setDuration(0);
        animator.start();
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

        hotMoviePresenter.request(userId, sessionId, 1, 10);
        releaseMoviePresenter.request(userId, sessionId, 1, 10);
        comingSoonMoviePresenter.request(userId, sessionId, 1, 10);
        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                home_radio_group.check(home_radio_group.getChildAt(position).getId());
            }
        });
        //点击软键盘外部，收起软键盘
        seacrch_editext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        return view;
    }

    @OnClick(R.id.imageView)
    public void seacrch_linear2() {
        if (animatort) {
            return;
        }
        animatort = true;
        animatorf = false;
        //这是显示出现的动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 530f, 30f);
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrch_linear2, "translationX", 30f, 530f);
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("电影首页"); //统计页面("MainScreen"为页面名称，可自定义)
        MobclickAgent.onResume(getContext()); //统计时长
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
        option.setLocationNotify(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    //点击事件
    @OnClick(R.id.hot)
    public void hot() {
        Intent intent = new Intent(getActivity(), FilmShowActivity.class);
        intent.putExtra("select", "1");
        startActivity(intent);
    }

    @OnClick(R.id.release)
    public void release() {
        Intent intent = new Intent(getActivity(), FilmShowActivity.class);
        intent.putExtra("select", "2");
        startActivity(intent);
    }

    @OnClick(R.id.comingSoon)
    public void comingSoon() {
        Intent intent = new Intent(getActivity(), FilmShowActivity.class);
        intent.putExtra("select", "3");
        startActivity(intent);
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

    @Override
    public void clickItem(int id, int postion) {
        Intent intent = new Intent(getActivity(), MoviesByIdActivity.class);
        intent.putExtra("id", String.valueOf(id));
        startActivity(intent);
        mList.smoothScrollToPosition(postion);
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
            String s = new Gson().toJson(result);
            Log.e("QAZQAZ", "-------------------------" + s);
            FileUtils.saveDataToFile(getContext(), s, "movesoon");
            adapter.addItem(result);
            adapter.notifyDataSetChanged();
            hotMovieAdapter.addItem(result);
            hotMovieAdapter.notifyDataSetChanged();
        }

        @Override
        public void errors(Throwable throwable) {
            String s = FileUtils.loadDataFromFile(getContext(), "movesoon");
            Type type = new TypeToken<List<HotMovieBean>>() {
            }.getType();
            List<HotMovieBean> moiveBeans = new Gson().fromJson(s, type);
            hotMovieAdapter.addItem(moiveBeans);
            hotMovieAdapter.notifyDataSetChanged();
            adapter.addItem(moiveBeans);
            adapter.notifyDataSetChanged();
        }
    }

    private class ReleaseMovie implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<HotMovieBean> result = (List<HotMovieBean>) data.getResult();
            String s = new Gson().toJson(result);
            FileUtils.saveDataToFile(getContext(), s, "move");
            releaseMovieAdapter.addItem(result);
            releaseMovieAdapter.notifyDataSetChanged();
        }

        @Override
        public void errors(Throwable throwable) {
            Toast.makeText(getContext(), "请连接网络！！！", Toast.LENGTH_SHORT).show();
            String s = FileUtils.loadDataFromFile(getContext(), "move");
            Type type = new TypeToken<List<HotMovieBean>>() {
            }.getType();
            List<HotMovieBean> moiveBeans = new Gson().fromJson(s, type);
            releaseMovieAdapter.addItem(moiveBeans);
            releaseMovieAdapter.notifyDataSetChanged();
        }
    }

    private class ComingSoonMovie implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            List<HotMovieBean> result = (List<HotMovieBean>) data.getResult();
            comingSoonMovieAdapter.addItem(result);
            comingSoonMovieAdapter.notifyDataSetChanged();
            String s = new Gson().toJson(result);
            FileUtils.saveDataToFile(getContext(), s, "m");
        }

        @Override
        public void errors(Throwable throwable) {
            String s = FileUtils.loadDataFromFile(getContext(), "m");
            Type type = new TypeToken<List<HotMovieBean>>() {
            }.getType();
            List<HotMovieBean> moiveBeans = new Gson().fromJson(s, type);
            comingSoonMovieAdapter.addItem(moiveBeans);
            comingSoonMovieAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hotMoviePresenter.unBind();
        releaseMoviePresenter.unBind();
        comingSoonMoviePresenter.unBind();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("电影页面");
        MobclickAgent.onPause(getContext()); //统计时长
    }
}
