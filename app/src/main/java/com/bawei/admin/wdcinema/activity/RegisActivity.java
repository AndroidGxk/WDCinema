package com.bawei.admin.wdcinema.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.admin.wdcinema.bean.Result;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.core.utils.EncryptUtil;
import com.bawei.admin.wdcinema.presenter.RegisPresenter;
import com.bigkoo.pickerview.TimePickerView;
import com.bw.movie.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 作者：admin on 2019/1/23 15:24
 * 邮箱：1724959985@qq.com
 */
public class RegisActivity extends AppCompatActivity implements CustomAdapt, ResultInfe {
    @BindView(R.id.my_regis_name)
    EditText my_regis_name;
    @BindView(R.id.my_regis_sex)
    TextView my_regis_sex;
    @BindView(R.id.my_regis_date)
    TextView my_regis_date;
    @BindView(R.id.my_regis_phone)
    EditText my_regis_phone;
    @BindView(R.id.my_regis_mail)
    EditText my_regis_mail;
    @BindView(R.id.my_regis_pwd)
    EditText my_regis_pwd;
    private RegisPresenter regisPresenter;
    int sexint;
    private String[] sexArry = new String[]{"男", "女"};// 性别选择

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        regisPresenter = new RegisPresenter(this);
    }

    /**
     * 选择性别
     */

    @OnClick(R.id.my_regis_sex)
    public void my_regis_sex() {
        showSexChooseDialog();
    }

    /**
     * 选择日期
     */
    @OnClick(R.id.my_regis_date)
    public void my_regis_date() {
        TimePickerView pvTime = new TimePickerView.Builder(RegisActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                my_regis_date.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    @OnClick(R.id.my_regis_btn)
    public void my_regis_btn() {
        String name = my_regis_name.getText().toString();
        String sex = my_regis_sex.getText().toString();
        String phone = my_regis_phone.getText().toString();
        String mail = my_regis_mail.getText().toString();
        String pwd = my_regis_pwd.getText().toString();
        String pwds = EncryptUtil.encrypt(pwd);
        if (sex.equals("男")) {
            sexint = 1;
        } else if (sex.equals("女")) {
            sexint = 2;
        }
        String data = my_regis_date.getText().toString();
        regisPresenter.request(name, phone, pwds, pwds, sexint, data, "123456"
                , "小米", "5.0", "android", mail);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    /**
     * 注册请求接口
     *
     * @param data
     */
    @Override
    public void success(Object data) {
        Result result = (Result) data;
        Toast.makeText(this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        if (result.getStatus().equals("0000")) {
            finish();
        }
    }

    @Override
    public void errors(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        regisPresenter.unBind();
    }

    /**
     * 日期转换
     *
     * @param date
     * @return
     */
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 性别选择
     */
    private void showSexChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                my_regis_sex.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }
}
