package com.bawei.admin.wdcinema.activity.thirdly_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpinTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opin_two);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_btn)
    public void back_btn() {
        finish();
    }
}
