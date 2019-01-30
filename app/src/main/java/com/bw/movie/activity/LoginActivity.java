package com.bw.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.core.utils.EncryptUtil;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.LoginPresenter;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends WDActivity implements CustomAdapt, ResultInfe {
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
    private SharedPreferences checked;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        loginPresenter = new LoginPresenter(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        checked = getSharedPreferences("Checked", MODE_PRIVATE);
        DaoSession daoSession = DaoMaster.newDevSession(LoginActivity.this, LoginSubBeanDao.TABLENAME);
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        boolean checkeds = checked.getBoolean("checked", false);
        if (checkeds) {
            List<LoginSubBean> list = loginSubBeanDao.loadAll();
            LoginSubBean loginSubBean = list.get(0);
            my_login_phone.setText(loginSubBean.getPhone());
            my_login_pwd.setText(loginSubBean.getPwd());
            rem_check.setChecked(true);
        }
    }

    @Override
    protected void destoryData() {
        loginPresenter.unBind();
    }

    /**
     * 点击微信登录
     */
    @OnClick(R.id.mIv_WeChat)
    public void mIv_WeChat() {

//初始化微信
        mWechatApi = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", false);
        mWechatApi.registerApp("wxb3852e6a6b7d9516");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
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
        SharedPreferences.Editor edit2 = checked.edit();
        edit2.putBoolean("checked", rem_check.isChecked());
        edit2.commit();
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
        SharedPreferences userinfo = getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor edits = userinfo.edit();
        edits.putBoolean("login", true);
        edits.commit();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void errors(Throwable throwable) {

    }
}
