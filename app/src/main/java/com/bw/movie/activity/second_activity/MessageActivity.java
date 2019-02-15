package com.bw.movie.activity.second_activity;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.WDActivity;
import com.bw.movie.adapter.MessageRecycleAdapter;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.MeassageListBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.ChangeMsgStatusPresenter;
import com.bw.movie.presenter.MessageListPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MessageActivity extends WDActivity implements ResultInfe, XRecyclerView.LoadingListener, CustomAdapt {
    @BindView(R.id.meagees_info)
    TextView meagees_info;
    @BindView(R.id.message_recy)
    XRecyclerView message_recy;
    private int mPage = 1;
    private int mCount = 5;
    private MessageListPresenter messageListPresenter;
    private LoginSubBean loginSubBean;
    private MessageRecycleAdapter adapter;
    private ChangeMsgStatusPresenter changeMsgStatusPresenter;
    private LoginSubBeanDao loginSubBeanDao;
    private List<LoginSubBean> list;
    private String sessionId;
    private int userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        messageListPresenter = new MessageListPresenter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, LoginSubBeanDao.TABLENAME);
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            LoginSubBean loginSubBean = list.get(0);
            userId = loginSubBean.getId();
            sessionId = loginSubBean.getSessionId();
        }
        adapter = new MessageRecycleAdapter();
        message_recy.setLoadingListener(this);
        message_recy.setPullRefreshEnabled(true);
        message_recy.setLoadingMoreEnabled(true);
        message_recy.setLayoutManager(manager);
        message_recy.setAdapter(adapter);
        changeMsgStatusPresenter = new ChangeMsgStatusPresenter(new ChangeMsgStatus());
        //未读消息总数
        adapter.setTotalPriceListener(new MessageRecycleAdapter.TotalPriceListener() {
            @Override
            public void totalPrice(int totalPrice) {
                meagees_info.setText("系统消息（" + totalPrice + "条未读）");
            }
        });
        //状态改变接口
        adapter.setMessageStatus(new MessageRecycleAdapter.messageStatus() {
            @Override
            public void messageStatus(int id) {
                changeMsgStatusPresenter.request(userId, sessionId, id);
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onResume() {
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

    /**
     * 消息状态修改
     */
    private class ChangeMsgStatus implements ResultInfe {

        @Override
        public void success(Object data) {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void errors(Throwable throwable) {

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
}