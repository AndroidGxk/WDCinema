package com.bw.movie.activity.thirdly_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.second_activity.MyMessage_Activity;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.UpdateUserInfoBean;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.UpdateUserInfoPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class UpdateNameActivity extends AppCompatActivity implements CustomAdapt, ResultInfe {
    @BindView(R.id.back_activity)
    ImageView back_activity;
    @BindView(R.id.threeupdate_name)
    EditText threeupdate_name;
    private DaoSession daoSession;
    private LoginSubBeanDao loginSubBeanDao;
    private List<LoginSubBean> list;
    private UpdateUserInfoPresenter updateUserInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);
        ButterKnife.bind(this);
        daoSession = DaoMaster.newDevSession(UpdateNameActivity.this, LoginSubBeanDao.TABLENAME);
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        updateUserInfoPresenter = new UpdateUserInfoPresenter(this);
        threeupdate_name.setText(list.get(0).getNickName());
    }

    @OnClick(R.id.back_activity)
    public void back_activity() {
        finish();
    }

    @OnClick(R.id.ok_btn)
    public void ok_btn() {
        String name = threeupdate_name.getText().toString();
        LoginSubBean loginSubBean = list.get(0);
        int sex = loginSubBean.getSex();
        String mail = "1724959985@qq.com";
        updateUserInfoPresenter.request(loginSubBean.getId(), loginSubBean.getSessionId(), name, sex, mail);
    }

    @OnClick(R.id.back_image)
    public void back_image() {
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

    @Override
    public void success(Object data) {
        Result result = (Result) data;
        UpdateUserInfoBean updateUserInfoBean = (UpdateUserInfoBean) result.getResult();
        Toast.makeText(this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        if (result.getStatus().equals("0000")) {
            List<LoginSubBean> list = loginSubBeanDao.queryBuilder()
                    .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                    .build().list();
            LoginSubBean loginSubBean = list.get(0);
            loginSubBean.setNickName(updateUserInfoBean.getNickName());
            loginSubBeanDao.insertOrReplace(loginSubBean);
        }
    }

    @Override
    public void errors(Throwable throwable) {

    }
}
