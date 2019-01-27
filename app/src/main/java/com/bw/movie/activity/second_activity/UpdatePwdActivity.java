package com.bw.movie.activity.second_activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.core.utils.EncryptUtil;
import com.bw.movie.presenter.UpdatePwdPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class UpdatePwdActivity extends AppCompatActivity implements CustomAdapt, ResultInfe {

    private SharedPreferences sp;
    private UpdatePwdPresenter updatePwdPresenter;
    @BindView(R.id.resetpwd_editext)
    EditText resetpwd_editext;
    @BindView(R.id.reconfirmpwd_editext)
    EditText reconfirmpwd_editext;
    @BindView(R.id.currentpwd_text)
    EditText currentpwd_text;
    private String sessionId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        ButterKnife.bind(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        updatePwdPresenter = new UpdatePwdPresenter(this);
    }

    @OnClick(R.id.update_back_image)
    public void update_back_image() {
        String oderpwd = currentpwd_text.getText().toString();
        String pwd1 = resetpwd_editext.getText().toString();
        String pwd2 = reconfirmpwd_editext.getText().toString();
        String oderpwds = EncryptUtil.encrypt(oderpwd);
        String pwds = EncryptUtil.encrypt(pwd1);
        String pwdss = EncryptUtil.encrypt(pwd2);
        updatePwdPresenter.request(userId, sessionId, oderpwds, pwds, pwdss);
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
     * 修改密码回调接口
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        if (result.getStatus().equals("0000")) {
            Toast.makeText(this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void errors(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updatePwdPresenter.unBind();
    }
}
