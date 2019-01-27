package com.bw.movie.activity.thirdly_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieScheBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.presenter.MovieSchePresenter;
import com.qfdqc.views.seattable.SeatTable;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_seat);
        ButterKnife.bind(this);
        line1.setBackgroundColor(0X77ffffff);
        MovieSchePresenter movieSchePresenter = new MovieSchePresenter(new movieScheList());
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
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
        seatTableView = (SeatTable) findViewById(R.id.seatView);
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
}
