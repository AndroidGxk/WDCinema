package com.bw.movie.wxapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.core.utils.Constant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者：古祥坤 on 2019/1/27 11:39
 * 邮箱：1724959985@qq.com
 */
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.e("登录", "");
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        api.handleIntent(this.getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 获取openid accessToken值用于后期操作
     * @param code 请求码
     */
    private void getAccessToken(final String code) {
        String path = "https://mApi.weixin.qq.com/sns/oauth2/access_token?appid="
                + "wxb3852e6a6b7d9516"
                + "&secret="
                + "showmsg_message"
                + "&code="
                + code
                + "&grant_type=authorization_code";
        String result = "";
        try {
            BufferedReader reader = null;
            StringBuffer sbf = new StringBuffer() ;
            URL url  = new URL(path) ;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection() ;
            //设置超时时间 10s
            connection.setConnectTimeout(10000);
            //设置请求方式
            connection.setRequestMethod( "GET" ) ;
            connection.connect();
            InputStream is = connection.getInputStream() ;
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8" )) ;
            String strRead = null ;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();

            JSONObject jsonObject = null;
            jsonObject = new JSONObject(result);
            final String openid = jsonObject.getString("openid").toString().trim();
            final String access_token = jsonObject.getString("access_token").toString().trim();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getUserMesg(access_token, openid);
                }
            }).start();
            Log.e("LKing","基础信息 = "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取微信的个人信息
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        String path =  "https://mApi.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;//微信登录地址
        String result = "";
        try {
            BufferedReader reader = null;
            StringBuffer sbf = new StringBuffer() ;

            URL url  = new URL(path) ;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection() ;
            //设置超时时间 10s
            connection.setConnectTimeout(10000);
            //设置请求方式
            connection.setRequestMethod( "GET" ) ;
            connection.connect();
            InputStream is = connection.getInputStream() ;
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8" )) ;
            String strRead = null ;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
            Log.e("LKing","用户信息 = "+result);
            Toast.makeText(this, "用户信息"+result.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //应用请求微信的响应结果将通过onResp回调
    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {//用户同意
            Log.e("LKing", "授权登录成功");

            Toast.makeText(this, "授权登录成功", Toast.LENGTH_SHORT).show();
            final String code = ((SendAuth.Resp) resp).code;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        getAccessToken(code);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Log.e("LKing", "授权登录失败\n\n自动返回");
        }
    }
}
