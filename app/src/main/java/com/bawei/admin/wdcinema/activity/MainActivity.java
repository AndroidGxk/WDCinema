package com.bawei.admin.wdcinema.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bawei.admin.wdcinema.R;

import me.jessyan.autosize.internal.CustomAdapt;

public class MainActivity extends AppCompatActivity implements CustomAdapt {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}
