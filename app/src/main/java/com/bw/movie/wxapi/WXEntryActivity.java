package com.bw.movie.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.activity.MainActivity;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.core.utils.Constant;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.WXLoginPresenter;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * 作者：古祥坤 on 2019/1/27 11:39
 * 邮箱：1724959985@qq.com
 */
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler, ResultInfe {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        api.handleIntent(this.getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    //应用请求微信的响应结果将通过onResp回调
    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {//用户同意
            final String code = ((SendAuth.Resp) resp).code;
            WXLoginPresenter wxLoginPresenter = new WXLoginPresenter(this);
            wxLoginPresenter.request(code);
        } else {
            Log.e("LKing", "授权登录失败\n\n自动返回");
        }
    }

    @Override
    public void success(Object data) {
        Result result = (Result) data;
        LoginBean loginBean = (LoginBean) result.getResult();
        LoginSubBean userInfo = loginBean.getUserInfo();
        SharedPreferences wx = getSharedPreferences("WX", MODE_PRIVATE);
        SharedPreferences.Editor edit = wx.edit();
        edit.putString("name", userInfo.getNickName());
        edit.commit();
        DaoSession daoSession = DaoMaster.newDevSession(WXEntryActivity.this, LoginSubBeanDao.TABLENAME);
        LoginSubBeanDao loginSubBeanDao = daoSession.getLoginSubBeanDao();
        loginSubBeanDao.loadAll();
        LoginSubBean loginSubBean = new LoginSubBean();
        loginSubBean.setUserId(userInfo.getUserId());
        loginSubBean.setHeadPic(userInfo.getHeadPic());
        loginSubBean.setNickName(userInfo.getNickName());
        loginSubBean.setStatu(1);
        long l = loginSubBeanDao.insertOrReplace(loginSubBean);
        Toast.makeText(this, "" + l, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
    }

    @Override
    public void errors(Throwable throwable) {

    }
}
