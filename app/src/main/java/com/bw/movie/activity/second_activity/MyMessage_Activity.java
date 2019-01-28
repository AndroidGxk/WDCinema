package com.bw.movie.activity.second_activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.thirdly_activity.UpdateNameActivity;
import com.bw.movie.bean.LoginSubBean;
import com.bw.movie.bean.Result;
import com.bw.movie.core.ResultInfe;
import com.bw.movie.core.utils.Constant;
import com.bw.movie.core.utils.FileUtils;
import com.bw.movie.core.utils.GetRealPath;
import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.LoginSubBeanDao;
import com.bw.movie.presenter.UpdateHeadPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyMessage_Activity extends AppCompatActivity implements CustomAdapt, View.OnClickListener, ResultInfe {
    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.myheader)
    SimpleDraweeView myheader;
    @BindView(R.id.update_name)
    TextView update_name;
    @BindView(R.id.update_sex)
    TextView update_sex;
    @BindView(R.id.update_date)
    TextView update_date;
    @BindView(R.id.myphone)
    TextView myphone;
    @BindView(R.id.update_mail)
    TextView update_mail;
    private int SELECT_PICTURE = 1; // 从图库中选择图片
    private static final int CHOOSE_PICTURE = 1000;
    private static final int TAKE_PICTURE = 1500;
    private SharedPreferences sp;
    private Uri tempUri;
    private UpdateHeadPresenter updateHeadPresenter;
    private DaoSession daoSession;
    private LoginSubBeanDao loginSubBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_);
        ButterKnife.bind(this);

        findViewById(R.id.update_name).setOnClickListener(this);
        findViewById(R.id.update_mail).setOnClickListener(this);
        findViewById(R.id.update_sex).setOnClickListener(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        updateHeadPresenter = new UpdateHeadPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        daoSession = DaoMaster.newDevSession(MyMessage_Activity.this, LoginSubBeanDao.TABLENAME);
        loginSubBeanDao = daoSession.getLoginSubBeanDao();
        List<LoginSubBean> list = loginSubBeanDao.queryBuilder()
                .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            LoginSubBean loginSubBean = list.get(0);
            myheader.setImageURI(loginSubBean.getHeadPic());
            update_name.setText(loginSubBean.getNickName());
            if (loginSubBean.getSex() == 1) {
                update_sex.setText("男");
            } else {
                update_sex.setText("女");
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date.setTime(loginSubBean.getBirthday());
            String Datetime = format.format(date);
            update_date.setText(Datetime);
            myphone.setText(loginSubBean.getPhone());
        }
    }

    @OnClick(R.id.myheader)
    public void myheader() {
        showChoosePhotoDialog();
    }

    @OnClick(R.id.go_updapwd)
    public void go_updapwd() {
        startActivity(new Intent(MyMessage_Activity.this, UpdatePwdActivity.class));

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MyMessage_Activity.RESULT_OK) {
            String sessionId = sp.getString("sessionId", "");
            int userId = sp.getInt("userId", 0);
            switch (requestCode) {
                case TAKE_PICTURE:
                    File imageFile = FileUtils.getImageFile();
                    String path = imageFile.getPath();

                    Log.e("zmz", "=====" + path);

//                    headPresenter.reqeust(userid, sessionId, path);

                    updateHeadPresenter.request(userId, sessionId, path);
                    break;
                case CHOOSE_PICTURE:
                    Uri uri = data.getData();

                    String[] proj = {MediaStore.Images.Media.DATA};

                    Cursor actualimagecursor = MyMessage_Activity.this.managedQuery(uri, proj, null, null, null);

                    int actual_image_column_index = actualimagecursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    actualimagecursor.moveToFirst();
                    String img_path = actualimagecursor
                            .getString(actual_image_column_index);
                    // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                        actualimagecursor.close();
                    updateHeadPresenter.request(userId, sessionId, img_path);
                    break;
            }
        }
    }

    /**
     * 相机相册选择照片弹框
     */
    private void showChoosePhotoDialog() {
        CharSequence[] items = {"相册", "相机"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (which == SELECT_PICTURE) {
                            Intent openCameraIntent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            tempUri = Uri.parse(FileUtils.getDir("/image/bimap") + "1.jpg");
                            Log.e("zmz", "=====" + tempUri);
                            //启动相机程序
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                            startActivityForResult(intent, TAKE_PICTURE);

                        } else {
                            if (ContextCompat.checkSelfPermission(MyMessage_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                //权限还没有授予，需要在这里写申请权限的代码
                                ActivityCompat.requestPermissions(MyMessage_Activity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PICTURE);
                            } else {
                                Intent openAlbumIntent = new Intent(
                                        Intent.ACTION_PICK);
                                openAlbumIntent.setType("image/*");
                                //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 修改信息跳转
     *
     * @param v
     */
    // TODO: 2019/1/24 修改信息 
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_name:
                startActivity(new Intent(MyMessage_Activity.this, UpdateNameActivity.class));
                break;
        }
    }

    @Override
    public void success(Object data) {
        Result result = (Result) data;
        Toast.makeText(this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        if (result.getStatus().equals("0000")) {
            List<LoginSubBean> list = loginSubBeanDao.queryBuilder()
                    .where(LoginSubBeanDao.Properties.Statu.eq("1"))
                    .build().list();
            LoginSubBean loginSubBean = list.get(0);
            loginSubBean.setHeadPic(result.getHeadPath());
            loginSubBeanDao.insertOrReplace(loginSubBean);
            myheader.setImageURI(result.getHeadPath());
        }

    }

    @Override
    public void errors(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateHeadPresenter.unBind();
    }
}
