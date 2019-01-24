package com.bawei.admin.wdcinema.activity.second_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyMessage_Activity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.back_image)
    ImageView back_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.go_updapwd)
    public void go_updapwd() {
        startActivity(new Intent(MyMessage_Activity.this, UpdatePwdActivity.class));
    }

    @OnClick(R.id.back_image)
    public void back_image() {
        finish();
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
