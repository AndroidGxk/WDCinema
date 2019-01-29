package com.bw.movie.activity;

import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.activity.fragment.Fragment_Page_one;
import com.bw.movie.activity.fragment.Fragment_Page_three;
import com.bw.movie.activity.fragment.Fragment_Page_two;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

public class MainActivity extends WDActivity {
    @BindView(R.id.radio)
    RadioGroup radio;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
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
    protected void destoryData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MobclickAgent.onResume(this);
    }
}
