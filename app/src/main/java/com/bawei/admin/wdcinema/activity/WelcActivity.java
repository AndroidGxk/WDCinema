package com.bawei.admin.wdcinema.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RadioGroup;

import com.bawei.admin.wdcinema.R;
import com.bawei.admin.wdcinema.activity.fragment.Fragment_Bootpage_four;
import com.bawei.admin.wdcinema.activity.fragment.Fragment_Bootpage_one;
import com.bawei.admin.wdcinema.activity.fragment.Fragment_Bootpage_three;
import com.bawei.admin.wdcinema.activity.fragment.Fragment_Bootpage_two;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

public class WelcActivity extends AppCompatActivity implements CustomAdapt {
    int timecount = 2;
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (timecount == 0) {
                    startActivity(new Intent(WelcActivity.this, MainActivity.class));
                    finish();
                }
                timecount--;
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };
    private SharedPreferences sp;
    private ViewPager viewpager;
    private RadioGroup radioGroup;
    private List<Fragment> fragments;
    private int currentItem = 0;
    private int flaggingWidth;
    public GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        boolean judge = sp.getBoolean("judge", false);
        if (judge) {
            setContentView(R.layout.activity_welc);
        } else {
            setContentView(R.layout.activity_guidance);
            slipToMain();
            // 获取分辨率
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            flaggingWidth = dm.widthPixels / 3;
            viewpager = (ViewPager) findViewById(R.id.viewpage);
            radioGroup = findViewById(R.id.radio);
            radioGroup.check(radioGroup.getChildAt(0).getId());
            fragments = new ArrayList<>();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    viewpager.setCurrentItem(checkedId - 1);
                }
            });
            Fragment_Bootpage_one fragment_bootpage_one = new Fragment_Bootpage_one();
            Fragment_Bootpage_two fragment_bootpage_two = new Fragment_Bootpage_two();
            Fragment_Bootpage_three fragment_bootpage_three = new Fragment_Bootpage_three();
            Fragment_Bootpage_four fragment_bootpage_four = new Fragment_Bootpage_four();
            fragments.add(fragment_bootpage_one);
            fragments.add(fragment_bootpage_two);
            fragments.add(fragment_bootpage_three);
            fragments.add(fragment_bootpage_four);
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    //页面切换后调用这个方法

                    currentItem = i;
                    radioGroup.check(radioGroup.getChildAt(i).getId());
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int i) {
                    return fragments.get(i);
                }

                @Override
                public int getCount() {
                    return fragments.size();
                }
            });
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("judge", true);
        edit.commit();
        if (judge) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
            }).start();
        }

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private void slipToMain() {
        mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        if (currentItem == 3) {
                            if ((e1.getRawX() - e2.getRawX()) >= flaggingWidth) {

                                Intent intent = new Intent(
                                        WelcActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                                return true;
                            }
                        }
                        return false;
                    }

                });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

}
