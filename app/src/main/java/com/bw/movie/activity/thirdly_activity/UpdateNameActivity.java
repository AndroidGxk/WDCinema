package com.bw.movie.activity.thirdly_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class UpdateNameActivity extends AppCompatActivity implements CustomAdapt {
    @BindView(R.id.back_activity)
    ImageView back_activity;
    @BindView(R.id.threeupdate_name)
    EditText threeupdate_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_activity)
    public void back_activity() {
        finish();
    }

    @OnClick(R.id.ok_btn)
    public void ok_btn() {
        String name = threeupdate_name.getText().toString();

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
