package com.bawei.admin.wdcinema.activity.second_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.admin.wdcinema.activity.thirdly_activity.OpinTwoActivity;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.presenter.FeedBackPresenter;
import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class OpinActivity extends AppCompatActivity implements CustomAdapt, ResultInfe {
    private SharedPreferences sp;
    @BindView(R.id.opin_editext)
    EditText opin_editext;
    private FeedBackPresenter feedBackPresenter;
    private String seesionId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opin);
        ButterKnife.bind(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        seesionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        feedBackPresenter = new FeedBackPresenter(this);

    }

    @OnClick(R.id.sub_btn)
    public void sub_btn() {
        String opin = opin_editext.getText().toString();
        feedBackPresenter.request(userId, seesionId, opin);
    }

    @OnClick(R.id.back_btn)
    public void back_btn() {
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

    /**
     * 反馈接口
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        if (result.getStatus().equals("0000")) {
            startActivity(new Intent(OpinActivity.this, OpinTwoActivity.class));
            finish();
        } else {
            Toast.makeText(this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void errors(Throwable throwable) {

    }
}
