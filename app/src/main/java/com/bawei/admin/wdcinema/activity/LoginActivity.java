package com.bawei.admin.wdcinema.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.TextView;

import com.bawei.admin.wdcinema.bean.LoginBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.bean.ormlite.DBManager;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.core.utils.Constant;
import com.bawei.admin.wdcinema.core.utils.EncryptUtil;
import com.bawei.admin.wdcinema.presenter.LoginPresenter;
import com.bw.movie.R;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends AppCompatActivity implements CustomAdapt, ResultInfe {
    boolean canSee = false;
    @BindView(R.id.regis_btn)
    TextView regis_btn;
    @BindView(R.id.my_login_phone)
    EditText my_login_phone;
    @BindView(R.id.my_login_pwd)
    EditText my_login_pwd;
    private LoginPresenter loginPresenter;
    private SharedPreferences sp;
    private DBManager dbManager;
    private List<LoginBean> loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //初始化DBManager
        try {
            dbManager = new DBManager(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loginPresenter = new LoginPresenter(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE}, Constant.REQ_PERM_CAMERA);
        }
        queryStudent();
    }

    @OnClick(R.id.my_login_btn)
    public void my_login_btn() {
        String name = my_login_phone.getText().toString();
        String pwd = my_login_pwd.getText().toString();
        String pwds = EncryptUtil.encrypt(pwd);
        loginPresenter.request(name, pwds);
    }

    /**
     * 查看密码
     */
    @OnClick(R.id.mIv_eye)
    public void mIv_eye() {
        if (canSee == false) {
            //如果是不能看到密码的情况下，
            my_login_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            canSee = true;
        } else {
            //如果是能看到密码的状态下
            my_login_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            canSee = false;
        }
    }

    /**
     * 去注册
     */
    @OnClick(R.id.regis_btn)
    public void regis_btn() {
        startActivity(new Intent(LoginActivity.this, RegisActivity.class));
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
     * 登录返回接口
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        LoginBean loginBean = (LoginBean) result.getResult();
        SharedPreferences.Editor edit = sp.edit();
        //添加Sp添加
        edit.putString("sessionId", loginBean.getSessionId());
        edit.putInt("userId", loginBean.getUserId());
        edit.commit();
        if (result.getStatus().equals("0000")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginBean login = new LoginBean();
            login.setId(123);
            login.setSessionId(loginBean.getSessionId());
            login.setUserId(loginBean.getUserId());
            String pwd = my_login_pwd.getText().toString();
            login.setPwd(pwd);
            try {
                int i = dbManager.deleteStudentAll((List<LoginBean>) loginBean);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                dbManager.insertStudent(login);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finish();
        }
    }

    @Override
    public void errors(Throwable throwable) {

    }

    /**
     * 查询所有的student
     */
    public void queryStudent() {
        try {
            loginBean = dbManager.getStudent();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
