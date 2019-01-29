package com.bw.movie.core;

import android.app.Application;
import android.os.Environment;

import com.bw.movie.core.utils.Constant;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;

public class GTApplication extends Application {

    public static IWXAPI mWxApi;

    @Override
    public void onCreate() {
        super.onCreate();
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "frescocache");
        Fresco.initialize(this, ImagePipelineConfig.newBuilder(this).
                setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(this)
                                .setBaseDirectoryPath(file)
                                .build()
                ).build());
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        registToWX();
        //腾讯bugly
        CrashReport.initCrashReport(getApplicationContext(), "ec43974c44", false);
    }


    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Constant.APP_ID);
    }
}
