package com.bawei.admin.wdcinema.activity.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.admin.wdcinema.adapter.Adapter;
import com.bawei.admin.wdcinema.bean.HotMovieBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.HotMoviePresenter;
import com.bw.movie.R;
import com.example.coverflow.RecyclerCoverFlow;

import java.util.List;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Page_one extends Fragment implements Adapter.onItemClick, CustomAdapt {

    private RecyclerCoverFlow mList;
    private Adapter adapter;
    private SharedPreferences sp;
    private HotMoviePresenter hotMoviePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_page_one, null);
        //调用sp，获取userID和sessionid
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);

        String sessionId = sp.getString("sessionId", "1");
        int userId = sp.getInt("userId", 1);

        AutoSizeConfig.getInstance().setCustomFragment(true);
        //热门影片
        hotMoviePresenter = new HotMoviePresenter(new HotMovie());
        mList = view.findViewById(R.id.list);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        adapter = new Adapter(getContext(), this);
        mList.setAdapter(adapter);

        hotMoviePresenter.request(userId, sessionId, 1, 10);
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
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }
}
