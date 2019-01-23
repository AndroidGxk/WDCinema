package com.bawei.admin.wdcinema.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.admin.wdcinema.R;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.core.utils.EncryptUtil;
import com.bawei.admin.wdcinema.presenter.RegisPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 作者：admin on 2019/1/23 15:24
 * 邮箱：1724959985@qq.com
 */
public class RegisActivity extends AppCompatActivity implements CustomAdapt, ResultInfe {
    @BindView(R.id.my_regis_name)
    EditText my_regis_name;
    @BindView(R.id.my_regis_sex)
    EditText my_regis_sex;
    @BindView(R.id.my_regis_date)
    EditText my_regis_date;
    @BindView(R.id.my_regis_phone)
    EditText my_regis_phone;
    @BindView(R.id.my_regis_mail)
    EditText my_regis_mail;
    @BindView(R.id.my_regis_pwd)
    EditText my_regis_pwd;
    private RegisPresenter regisPresenter;
    int sexint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        regisPresenter = new RegisPresenter(this);
    }

    @OnClick(R.id.my_regis_btn)
    public void my_regis_btn() {
        String name = my_regis_name.getText().toString();
        String sex = my_regis_sex.getText().toString();
        String date = my_regis_date.getText().toString();
        String phone = my_regis_phone.getText().toString();
        String mail = my_regis_mail.getText().toString();
        String pwd = my_regis_pwd.getText().toString();
        String pwds = EncryptUtil.encrypt(pwd);
        if (sex.equals("男")) {
            sexint = 1;
        } else if (sex.equals("女")) {
            sexint = 2;
        }
        regisPresenter.request(name, phone, pwds, pwds, sexint, date, "123456"
                , "小米", "5.0", "android", "1724959985@qq.com");
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
     * 注册请求接口
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        Toast.makeText(this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        if (result.getStatus().equals("0000")) {
            finish();
        }
    }

    @Override
    public void errors(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        regisPresenter.unBind();
    }
}
