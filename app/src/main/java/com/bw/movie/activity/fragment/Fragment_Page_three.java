package com.bw.movie.activity.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.second_activity.BuyRecordActivity;
import com.bw.movie.activity.second_activity.ConcerActivity;
import com.bw.movie.activity.second_activity.MessageActivity;
import com.bw.movie.activity.second_activity.MyMessage_Activity;
import com.bw.movie.activity.second_activity.OpinActivity;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.UserVipInfoBean;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.UserSignInPresenter;
import com.bw.movie.presenter.UserVipInfoPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Page_three extends Fragment implements CustomAdapt, ResultInfe {
    @BindView(R.id.massge_linea)
    LinearLayout massge_linea;
    @BindView(R.id.usersigin)
    Button usersigin;
    @BindView(R.id.myheader)
    SimpleDraweeView myheader;
    @BindView(R.id.myname)
    TextView myname;
    private UserSignInPresenter userSignInPresenter;
    private List<LoginSubBean> list;
    private LoginSubBeanDao loginSubBeanDao;
    private UserVipInfoPresenter userVipInfoPresenter;
    private String sessionId;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_page_three, null);
        ButterKnife.bind(this, view);
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), LoginSubBeanDao.TABLENAME);
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            LoginSubBean loginSubBean = list.get(0);
            userId = loginSubBean.getId();
            sessionId = loginSubBean.getSessionId();
        }
        AutoSizeConfig.getInstance().setCustomFragment(true);
        userSignInPresenter = new UserSignInPresenter(this);
        userVipInfoPresenter = new UserVipInfoPresenter(new UserInfo());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    public void init() {
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), LoginSubBeanDao.TABLENAME);
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            LoginSubBean loginSubBean = list.get(0);
            userId = loginSubBean.getId();
            sessionId = loginSubBean.getSessionId();
            String nickName = loginSubBean.getNickName();
            String headPic = loginSubBean.getHeadPic();
            myheader.setImageURI(headPic);
            myname.setText(nickName);
            userVipInfoPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId());
        } else {
            myname.setText("未登录");
            myheader.setImageURI("");
            usersigin.setText("签到");
            myname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.size() == 0) {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        return;
                    }
                }
            });
        }
    }

    /**
     * 个人信息
     */
    @OnClick(R.id.massge_linea)
    public void massge_linea() {
        if (list.size() == 0) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getContext(), MyMessage_Activity.class));

    }

    /**
     * 意见反馈
     */
    @OnClick(R.id.mylinear_three)
    public void myyijian() {
        if (list.size() == 0) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getContext(), OpinActivity.class));
    }

    /**
     * 退出登录
     */
    @OnClick(R.id.back_btn)
    public void back_btn() {
        if (list.size() == 0) {
            Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
            return;
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("提示");
            alert.setMessage("确定退出登录吗?");
            alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DaoSession daoSession = DaoMaster.newDevSession(getActivity(), LoginSubBeanDao.TABLENAME);
                    loginSubBeanDao = daoSession.getLoginSubBeanDao();
                    list = loginSubBeanDao.queryBuilder()
                            .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                            .build().list();
                    if (list.size() > 0) {
                        LoginSubBean loginSubBean = list.get(0);
                        loginSubBean.setStatu(0);
                        loginSubBeanDao.insertOrReplace(loginSubBean);
                    }
                    SharedPreferences userinfo = getContext().getSharedPreferences("userinfo", MODE_PRIVATE);
                    SharedPreferences.Editor edit = userinfo.edit();
                    edit.putBoolean("login", false);
                    edit.commit();
                    init();
                }
            });
            alert.setNegativeButton("取消", null);
            alert.show();
        }
    }

    /**
     * 购票记录
     */
    @OnClick(R.id.goupiao)
    public void goupiao() {
        if (list.size() == 0) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getContext(), BuyRecordActivity.class));
    }

    /**
     * 我的关注
     *
     * @return
     */
    @OnClick(R.id.mylinear_two)
    public void mylinear_two() {
        if (list.size() == 0) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getContext(), ConcerActivity.class));
    }

    /**
     * 消息列表
     *
     * @return
     */
    @OnClick(R.id.mes_image)
    public void mes_image() {
        if (list.size() == 0) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getContext(), MessageActivity.class));
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    @Override
    public void onHiddenChanged(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume，为true时，Fragment已经可见
        } else {
            //相当于Fragment的onPause，为false时，Fragment不可见
            init();
        }
    }
    @OnClick(R.id.usersigin)
    public void usersigin() {
        if (list.size() == 0) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        if (usersigin.getText().toString().equals("已签到")) {
            return;
        }
        userSignInPresenter.request(userId, sessionId);
    }

    /**
     * 签到
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        Toast.makeText(getContext(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        if (result.getStatus().equals("0000")) {
            usersigin.setText("已签到");
        }
    }

    @Override
    public void errors(Throwable throwable) {

    }

    class UserInfo implements ResultInfe {

        @Override
        public void success(Object data) {
            Result result = (Result) data;
            UserVipInfoBean userVipInfoBean = (UserVipInfoBean) result.getResult();
            if (userVipInfoBean.getUserSignStatus() == 1) {
                usersigin.setText("签到");
            } else {
                usersigin.setText("已签到");
            }
        }

        @Override
        public void errors(Throwable throwable) {

        }
    }
}
