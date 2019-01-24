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
    private ReleaseMovieAdapter releaseMovieAdapter;
    private ComingSoonMovieAdapter comingSoonMovieAdapter;
    private ReleaseMoviePresenter releaseMoviePresenter;
    private ComingSoonMoviePresenter comingSoonMoviePresenter;

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

        hotMoviePresenter.request(userId, sessionId, 1, 10);
        releaseMoviePresenter.request(userId, sessionId, 1, 10);
        comingSoonMoviePresenter.request(userId, sessionId, 1, 10);
        return view;
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
