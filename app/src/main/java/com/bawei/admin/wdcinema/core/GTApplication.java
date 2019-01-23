package com.bawei.admin.wdcinema.core;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class GTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
