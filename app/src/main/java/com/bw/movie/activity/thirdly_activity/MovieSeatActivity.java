package com.bw.movie.activity.thirdly_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.MovieScheBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.presenter.BuyMovieTicketPresenter;
import com.bw.movie.presenter.MovieSchePresenter;
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

public class MovieSeatActivity extends AppCompatActivity {
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
    private SharedPreferences sp;
    private String sessionId1;
    private PayPresenter payPresenter;

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
//        DaoSession daoSession = DaoMaster.newDevSession(this, LoginSubBeanDao.TABLENAME);
//        LoginSubBeanDao loginSubBeanDao = daoSession.getLoginSubBeanDao();
//        List<LoginSubBean> loginSubBeans = loginSubBeanDao.loadAll();
//        for (int i = 0; i < loginSubBeans.size(); i++) {
//            userId = loginSubBeans.get(i).getUserId();
//            sessionId = loginSubBeans.get(i).getSessionId();
//        }
        sp = getSharedPreferences("login", MODE_PRIVATE);
        sessionId1 = sp.getString("sessionId", "1");
        userId = sp.getInt("userId", 1);
        Log.e("qqqqqqqqqqqqqqqq", "---------------userId" + userId + "--------------sessionId" + sessionId1);
        buyMovieTicketPresenter = new BuyMovieTicketPresenter(new BuyMovie());
        payPresenter = new PayPresenter(new Pay());

        line1.setBackgroundColor(0X77ffffff);
        MovieSchePresenter movieSchePresenter = new MovieSchePresenter(new movieScheList());
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        int ids = intent.getIntExtra("pid", 0);
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
        movieSchePresenter.request(ids, id);
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
//        String s = String.valueOf(userId) + String.valueOf(id) + String.valueOf(mCount)+"movie";
        String s = MD5(userId + "" + id + "" + mCount + "movie");
        Log.e("qqqqqqqqqqqqqqqq", "---------------userId" + mCount);
        buyMovieTicketPresenter.request(userId, sessionId1, id, mCount, s);
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

    class movieScheList implements ResultInfe {
        @Override
        public void success(Object data) {
            Result result = (Result) data;
            List<MovieScheBean> movieScheList = (List<MovieScheBean>) result.getResult();
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class BuyMovie implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(MovieSeatActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            String orderId = data.getOrderId();
            payPresenter.request(userId, sessionId1, 1, orderId);
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }

    private class Pay implements ResultInfe<Result> {
        @Override
        public void success(Result data) {
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
}
