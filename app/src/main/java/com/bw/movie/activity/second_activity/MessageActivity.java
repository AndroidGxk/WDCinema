package com.bw.movie.activity.second_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.FilmShowActivity;
import com.bw.movie.adapter.MessageRecycleAdapter;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.MeassageListBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.MessageListPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends AppCompatActivity implements ResultInfe, XRecyclerView.LoadingListener {
    @BindView(R.id.meagees_info)
    TextView meagees_info;
    @BindView(R.id.message_recy)
    XRecyclerView message_recy;
    private int mPage = 1;
    private int mCount = 5;
    private MessageListPresenter messageListPresenter;
    private LoginSubBean loginSubBean;
    private MessageRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        messageListPresenter = new MessageListPresenter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new MessageRecycleAdapter();
        message_recy.setLoadingListener(this);
        message_recy.setPullRefreshEnabled(true);
        message_recy.setLoadingMoreEnabled(true);
        message_recy.setLayoutManager(manager);
        message_recy.setAdapter(adapter);
        int count = adapter.getCount();
        meagees_info.setText("系统消息（" + count + "条未读）");
    }

    @Override
    protected void onResume() {
        super.onResume();
        DaoSession daoSession = DaoMaster.newDevSession(MessageActivity.this, LoginSubBeanDao.TABLENAME);
        LoginSubBeanDao loginSubBeanDao = daoSession.getLoginSubBeanDao();
        List<LoginSubBean> list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        loginSubBean = list.get(0);
        messageListPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount);
    }

    @OnClick(R.id.back_btn)
    public void back_btn() {
        finish();
    }

    @Override
    public void success(Object data) {
        Result result = (Result) data;
        List<MeassageListBean> meassageListBeans = (List<MeassageListBean>) result.getResult();
        adapter.addItem(meassageListBeans);
        message_recy.refreshComplete();
        message_recy.loadMoreComplete();
    }

    @Override
    public void errors(Throwable throwable) {

    }

    @Override
    public void onRefresh() {
        mPage = 1;
        adapter.RemoveItem();
        messageListPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        messageListPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), mPage, mCount);
    }
}
