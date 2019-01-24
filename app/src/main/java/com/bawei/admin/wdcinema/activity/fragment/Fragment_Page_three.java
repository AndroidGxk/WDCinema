package com.bawei.admin.wdcinema.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bawei.admin.wdcinema.activity.LoginActivity;
import com.bawei.admin.wdcinema.activity.second_activity.MyMessage_Activity;
import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

public class Fragment_Page_three extends Fragment implements CustomAdapt {
    @BindView(R.id.massge_linea)
    LinearLayout massge_linea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_page_three, null);
        ButterKnife.bind(this, view);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        return view;
    }

    @OnClick(R.id.massge_linea)
    public void massge_linea() {
        startActivity(new Intent(getContext(), MyMessage_Activity.class));
    }

    @OnClick(R.id.back_btn)
    public void back_btn() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
