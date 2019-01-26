package com.bawei.admin.wdcinema.activity.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.admin.wdcinema.activity.LoginActivity;
import com.bawei.admin.wdcinema.activity.second_activity.ConcerActivity;
import com.bawei.admin.wdcinema.activity.second_activity.MyMessage_Activity;
import com.bawei.admin.wdcinema.activity.second_activity.OpinActivity;
import com.bawei.admin.wdcinema.bean.LoginSubBean;
import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.greendao.DaoMaster;
import com.bawei.admin.wdcinema.greendao.DaoSession;
import com.bawei.admin.wdcinema.greendao.LoginSubBeanDao;
import com.bawei.admin.wdcinema.presenter.UserSignInPresenter;
import com.bw.movie.R;
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
    private int userId;
    private String sessionId;
    private SharedPreferences sp;
    private List<LoginSubBean> list;
    private LoginSubBeanDao loginSubBeanDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_page_three, null);
        ButterKnife.bind(this, view);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        userSignInPresenter = new UserSignInPresenter(this);
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        sessionId = sp.getString("sessionId", "1");
        userId = sp.getInt("userId", 1);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), LoginSubBeanDao.TABLENAME);
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            LoginSubBean loginSubBean = list.get(0);
            String nickName = loginSubBean.getNickName();
            String headPic = loginSubBean.getHeadPic();
            myheader.setImageURI(headPic);
            myname.setText(nickName);
        } else {
            myname.setText("未登录");
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

    @OnClick(R.id.massge_linea)
    public void massge_linea() {
        if (list.size() == 0) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getContext(), MyMessage_Activity.class));

    }

    @OnClick(R.id.mylinear_three)
    public void myyijian() {
        if (list.size() == 0) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getContext(), OpinActivity.class));
    }

    @OnClick(R.id.back_btn)
    public void back_btn() {
        if (list.size() == 0) {
            Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
            return;
        }
        loginSubBeanDao.deleteAll();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
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
        usersigin.setText("已签到");
    }

    @Override
    public void errors(Throwable throwable) {

    }
}
