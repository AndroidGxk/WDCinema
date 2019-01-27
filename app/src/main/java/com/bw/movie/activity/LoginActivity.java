package com.bw.movie.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.GTApplication;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.core.utils.Constant;
import com.bw.movie.core.utils.EncryptUtil;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.LoginPresenter;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Map;

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
    @BindView(R.id.rem_check)
    CheckBox rem_check;
    private LoginPresenter loginPresenter;
    private SharedPreferences sp;
    private LoginSubBeanDao loginSubBeanDao;
    private IWXAPI mWechatApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        DaoSession daoSession = DaoMaster.newDevSession(LoginActivity.this, LoginSubBeanDao.TABLENAME);
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        List<LoginSubBean> loginSubBeans = loginSubBeanDao.loadAll();
        if (loginSubBeans.size() >= 5) {
            loginSubBeanDao.deleteAll();
        }
    }

    /**
     * 点击微信登录
     */
    @OnClick(R.id.mIv_WeChat)
    public void mIv_WeChat() {

//初始化微信
        mWechatApi = WXAPIFactory.createWXAPI(this,"wxb3852e6a6b7d9516",false);
        mWechatApi.registerApp("wxb3852e6a6b7d9516");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        mWechatApi.sendReq(req);

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
        LoginSubBean userInfo = loginBean.getUserInfo();
        edit.putString("sessionId", loginBean.getSessionId());
        edit.putInt("userId", loginBean.getUserId());
        edit.commit();
        LoginSubBean loginSubBean = new LoginSubBean();
        loginSubBean.setStatu(1);
        loginSubBean.setPwd(my_login_pwd.getText().toString());
        loginSubBean.setGid(0);
        loginSubBean.setHeadPic(userInfo.getHeadPic());
        loginSubBean.setBirthday(userInfo.getBirthday());
        loginSubBean.setId(userInfo.getId());
        loginSubBean.setLastLoginTime(userInfo.getLastLoginTime());
        loginSubBean.setPhone(userInfo.getPhone());
        loginSubBean.setSessionId(loginBean.getSessionId());
        loginSubBean.setNickName(userInfo.getNickName());
        loginSubBean.setSex(userInfo.getSex());
        loginSubBean.setUserId(userInfo.getUserId());
        loginSubBean.setMail(userInfo.getMail());
        loginSubBeanDao.insertOrReplace(loginSubBean);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void errors(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.unBind();
    }
}
