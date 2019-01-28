package com.bw.movie.activity.second_activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.WDActivity;
import com.bw.movie.activity.thirdly_activity.MovieSeatActivity;
import com.bw.movie.adapter.UserTicketRecycleAdapter;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.UserTicketBean;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.PayPresenter;
import com.bw.movie.presenter.UserBuyTicketPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BuyRecordActivity extends WDActivity implements ResultInfe, XRecyclerView.LoadingListener {
    private int mType = 1;
    private boolean recommcheck = true;
    private boolean nearbycheck = false;
    private PopupWindow popupWindow;
    private View root;
    @BindView(R.id.obli_btn)
    Button obli_btn;
    @BindView(R.id.nearby_btn)
    Button nearby_btn;
    @BindView(R.id.ticket_recycle)
    XRecyclerView ticket_recycle;
    private UserBuyTicketPresenter userBuyTicketPresenter;
    private LoginSubBeanDao loginSubBeanDao;
    private List<LoginSubBean> list;
    private int mPage = 1;
    private static final int mCount = 5;
    private UserTicketRecycleAdapter userTicketRecycleAdapter;
    private LoginSubBean loginSubBean;
    // 声明平移动画
    private TranslateAnimation animation;
    private RadioButton weixin_radio;
    private RadioButton zhifubao_radio;
    private Button btn;
    private String oderIds;
    private PayPresenter payPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_record;
    }

    @Override
    protected void initView() {
        obli_btn.setBackgroundResource(R.drawable.btn_gradient);
        userBuyTicketPresenter = new UserBuyTicketPresenter(this);
        root = View.inflate(this, R.layout.zhifu_item, null);
        weixin_radio = root.findViewById(R.id.weixin_radio);
        zhifubao_radio = root.findViewById(R.id.zhifubao_radio);
        payPresenter = new PayPresenter(new PayRequest());
        btn = root.findViewById(R.id.btn);
        DaoSession daoSession = DaoMaster.newDevSession(BuyRecordActivity.this, LoginSubBeanDao.TABLENAME);
        userTicketRecycleAdapter = new UserTicketRecycleAdapter();
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            loginSubBean = list.get(0);
            userBuyTicketPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount, 1);
        }
        LinearLayoutManager manager = new LinearLayoutManager(BuyRecordActivity.this);
        ticket_recycle.setLoadingListener(this);
        ticket_recycle.setPullRefreshEnabled(true);
        ticket_recycle.setLayoutManager(manager);
        ticket_recycle.setAdapter(userTicketRecycleAdapter);
        userTicketRecycleAdapter.setOnClickListener(new UserTicketRecycleAdapter.OnClickListener() {
            @Override
            public void onclick(int id, String oderId) {
                oderIds = oderId;
                changeIcon();
            }
        });
    }

    /**
     * popupWindow显示
     */
    private void changeIcon() {
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
                payPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mType, oderIds);
            }
        });
        root.startAnimation(animation);
    }

    @OnClick(R.id.moviesbyid_finish)
    public void moviesbyid_finish() {
        finish();
    }

    @OnClick(R.id.obli_btn)
    public void obli_btn() {
        if (recommcheck) {
            return;
        }
        recommcheck = true;
        if (recommcheck) {
            userTicketRecycleAdapter.removeAll();
            if (list.size() > 0) {
                userBuyTicketPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount, 1);
            }
            obli_btn.setBackgroundResource(R.drawable.btn_gradient);
            nearbycheck = false;
            nearby_btn.setBackgroundResource(R.drawable.btn_false);
        }
    }

    @OnClick(R.id.nearby_btn)
    public void nearby_btn() {
        if (nearbycheck) {
            return;
        }
        nearbycheck = true;
        if (nearbycheck) {
            userTicketRecycleAdapter.removeAll();
            if (list.size() > 0) {
                userBuyTicketPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount, 2);
            }
            nearby_btn.setBackgroundResource(R.drawable.btn_gradient);
            recommcheck = false;
            obli_btn.setBackgroundResource(R.drawable.btn_false);
        }
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void success(Object data) {
        Result result = (Result) data;
        List<UserTicketBean> userTicketList = (List<UserTicketBean>) result.getResult();
        userTicketRecycleAdapter.addAll(userTicketList);
        ticket_recycle.loadMoreComplete();
        ticket_recycle.refreshComplete();
    }

    @Override
    public void errors(Throwable throwable) {

    }

    /**
     * 上拉下拉
     */
    @Override
    public void onRefresh() {
        mPage = 1;
        if (recommcheck) {
            userBuyTicketPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount, 1);
        } else {
            userBuyTicketPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount, 2);
        }
        userTicketRecycleAdapter.removeAll();
    }

    @Override
    public void onLoadMore() {
        mPage++;
        if (recommcheck) {
            userBuyTicketPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount, 1);
        } else {
            userBuyTicketPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount, 2);
        }
    }

    class PayRequest implements ResultInfe<Result> {

        @Override
        public void success(Result data) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(BuyRecordActivity.this, null);
            popupWindow.dismiss();
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
}
