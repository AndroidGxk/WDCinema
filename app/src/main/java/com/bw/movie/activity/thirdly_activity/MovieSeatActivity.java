package com.bw.movie.activity.thirdly_activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.BuyMovieTicketPresenter;
import com.bw.movie.presenter.PayPresenter;
import com.qfdqc.views.seattable.SeatTable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MovieSeatActivity extends AppCompatActivity implements CustomAdapt {
    public SeatTable seatTableView;
    @BindView(R.id.monye_linear)
    LinearLayout monye_linear;
    private int mCount = 0;
    @BindView(R.id.line0)
    TextView line0;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.address)
    TextView addressview;
    @BindView(R.id.name)
    TextView nameview;
    @BindView(R.id.date)
    TextView dateview;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.house)
    TextView houseview;
    @BindView(R.id.moey_text)
    TextView moey_text;
    private double money;
    @BindView(R.id.moviesbyid_finish)
    ImageView moviesbyid_finish;
    @BindView(R.id.zhifu)
    ImageView zhifu;
    private BuyMovieTicketPresenter buyMovieTicketPresenter;
    private int userId;
    private String sessionId;
    private int id;
    //    private SharedPreferences sp;
//    private String sessionId1;
    private PayPresenter payPresenter;
    private PopupWindow popupWindow;
    private View root;
    private TranslateAnimation animation;
    private RadioButton weixin_radio;
    private RadioButton zhifubao_radio;
    private int mType = 1;
    private Button btn;
    private List<LoginSubBean> loginSubBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_seat);
        ButterKnife.bind(this);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        DaoSession daoSession = DaoMaster.newDevSession(this, LoginSubBeanDao.TABLENAME);
        LoginSubBeanDao loginSubBeanDao = daoSession.getLoginSubBeanDao();
        loginSubBeans = loginSubBeanDao.loadAll();
        for (int i = 0; i < loginSubBeans.size(); i++) {
            userId = loginSubBeans.get(i).getId();
            sessionId = loginSubBeans.get(i).getSessionId();
        }
        //popupwindow
        root = View.inflate(this, R.layout.zhifu_item, null);
        weixin_radio = root.findViewById(R.id.weixin_radio);
        zhifubao_radio = root.findViewById(R.id.zhifubao_radio);
        btn = root.findViewById(R.id.btn);

        buyMovieTicketPresenter = new BuyMovieTicketPresenter(new BuyMovie());
        payPresenter = new PayPresenter(new Pay());

        line1.setBackgroundColor(0X77ffffff);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        String address = intent.getStringExtra("address");
        String name = intent.getStringExtra("names");
        String date = intent.getStringExtra("date");
        String house = intent.getStringExtra("house");
        String namemovie = intent.getStringExtra("namemovie");
        money = intent.getDoubleExtra("money", 0.0);
        nameview.setText(namemovie);
        long releasetime = intent.getLongExtra("releasetime", 0);
        Date dates = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd hh:mm:ss");
        dates.setTime(releasetime);
        String Datetime = format.format(dates);
        dateview.setText(Datetime);
        time.setText(date);
        houseview.setText(house);
        line0.setText(name);
        addressview.setText(address);
        seatTableView = findViewById(R.id.seatView);
        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            // TODO: 2019/1/26 选座 
            @Override
            public void checked(int row, int column) {
                monye_linear.setVisibility(View.VISIBLE);
                moviesbyid_finish.setVisibility(View.GONE);
                mCount++;
                double sum = money * mCount;
                DecimalFormat df = new DecimalFormat("######0.00");
                String sums = df.format(sum);
                moey_text.setText(sums);
            }

            @Override
            public void unCheck(int row, int column) {
                monye_linear.setVisibility(View.VISIBLE);
                moviesbyid_finish.setVisibility(View.GONE);
                mCount--;
                double sum = money * mCount;
                DecimalFormat df = new DecimalFormat("######0.00");
                String sums = df.format(sum);
                moey_text.setText(sums);
                if (mCount == 0) {
                    moviesbyid_finish.setVisibility(View.VISIBLE);
                    monye_linear.setVisibility(View.GONE);
                }
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);
    }

    @OnClick(R.id.zhifu)
    public void zhifu() {
        String s = MD5(userId + "" + id + "" + mCount + "movie");
        Log.e("qqqqqqqqqqqqqqqq", "---------------userId" + mCount);
        buyMovieTicketPresenter.request(userId, sessionId, id, mCount, s);
    }

    @OnClick(R.id.moviesbyid_finish)
    public void moviesbyid_finish() {
        finish();
    }

    /**
     * MD5加密
     *
     * @param sourceStr
     * @return
     */
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }


    @OnClick(R.id.quxiao)
    public void quxiao() {
        finish();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class BuyMovie implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            String orderId = data.getOrderId();
            if (loginSubBeans.size() > 0) {
                changeIcon(orderId);
            } else {
                Toast.makeText(MovieSeatActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class Pay implements ResultInfe<Result> {
        @Override
        public void success(Result data) {

            popupWindow.dismiss();

            final IWXAPI msgApi = WXAPIFactory.createWXAPI(MovieSeatActivity.this, null);

            // 将该app注册到微信

            msgApi.registerApp("wxd930ea5d5a258f4f");
            PayReq request = new PayReq();
            request.appId = data.getAppId();
            request.partnerId = data.getPartnerId();
            request.prepayId = data.getPrepayId();
            request.packageValue = data.getPackageValue();
            request.nonceStr = data.getNonceStr();
            request.timeStamp = data.getTimeStamp();
            request.sign = data.getSign();
            msgApi.sendReq(request);
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    /**
     * popupWindow显示
     *
     * @param orderId
     */
    private void changeIcon(final String orderId) {
        if (popupWindow == null) {
            // 参数2,3：指明popupwindow的宽度和高度
            popupWindow = new PopupWindow(root, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            // 设置背景图片， 必须设置，不然动画没作用
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            // 设置点击popupwindow外屏幕其它地方消失
            popupWindow.setOutsideTouchable(true);

            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(200);
            // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        }
        popupWindow.showAtLocation(root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


        weixin_radio.setChecked(true);
        zhifubao_radio.setChecked(false);
        weixin_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weixin_radio.setChecked(true);
                    zhifubao_radio.setChecked(false);
                    mType = 1;
                }
            }
        });
        zhifubao_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weixin_radio.setChecked(false);
                    zhifubao_radio.setChecked(true);
                    mType = 2;
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                payPresenter.request(userId, sessionId, mType, orderId);
            }
        });
        root.startAnimation(animation);
    }
}
