package com.bawei.admin.wdcinema.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bw.movie.R;

import me.jessyan.autosize.internal.CustomAdapt;

public class FilmShowActivity extends AppCompatActivity implements CustomAdapt {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_show);
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
