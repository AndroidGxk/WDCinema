package com.bawei.admin.wdcinema.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bawei.admin.wdcinema.adapter.Adapter;
import com.bw.movie.R;
import com.example.coverflow.RecyclerCoverFlow;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

public class Fragment_Page_one extends Fragment implements Adapter.onItemClick,CustomAdapt {

    private RecyclerCoverFlow mList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getContext(), R.layout.fragment_page_one, null);
        mList = view.findViewById(R.id.list);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        mList.setAdapter(new Adapter(getContext(), this));

        AutoSizeConfig.getInstance().setCustomFragment(true);

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
}
