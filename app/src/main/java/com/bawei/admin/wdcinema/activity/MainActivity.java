package com.bawei.admin.wdcinema.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.bawei.admin.wdcinema.activity.fragment.Fragment_Page_one;
import com.bawei.admin.wdcinema.activity.fragment.Fragment_Page_three;
import com.bawei.admin.wdcinema.activity.fragment.Fragment_Page_two;
import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

public class MainActivity extends AppCompatActivity implements CustomAdapt {
    @BindView(R.id.radio)
    RadioGroup radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        radio.check(radio.getChildAt(0).getId());
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        final Fragment_Page_one fragment_page_one = new Fragment_Page_one();
        final Fragment_Page_two fragment_page_two = new Fragment_Page_two();
        final Fragment_Page_three fragment_page_three = new Fragment_Page_three();
        transaction.add(R.id.fragment, fragment_page_one);
        transaction.add(R.id.fragment, fragment_page_two);
        transaction.add(R.id.fragment, fragment_page_three);
        transaction.show(fragment_page_one);
        transaction.hide(fragment_page_two);
        transaction.hide(fragment_page_three);
        transaction.commit();
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.one:
                        trans.show(fragment_page_one);
                        trans.hide(fragment_page_two);
                        trans.hide(fragment_page_three);
                        break;
                    case R.id.two:
                        trans.show(fragment_page_two);
                        trans.hide(fragment_page_one);
                        trans.hide(fragment_page_three);
                        break;
                    case R.id.three:
                        trans.show(fragment_page_three);
                        trans.hide(fragment_page_one);
                        trans.hide(fragment_page_two);
                        break;
                }
                trans.commit();
            }
        });
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
